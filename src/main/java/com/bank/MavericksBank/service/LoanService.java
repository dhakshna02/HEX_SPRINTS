package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.CustomerLoanDecision;
import com.bank.MavericksBank.enums.Designation;
import com.bank.MavericksBank.enums.LoanStatus;
import com.bank.MavericksBank.exceptions.AccountOwnerInvalidException;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.CollatralMapper;
import com.bank.MavericksBank.mapper.LoanMapper;
import com.bank.MavericksBank.model.*;
import com.bank.MavericksBank.repository.CollatralRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final UsersService usersService;
    private final CollatralService collatralService;
    private final CollatralRepository collatralRepository;
    private final TranscationService transcationService;


    @Transactional
    public void createLoan(@Valid CreateLoanDto createLoanDto,String username) {


        Users users = (Users) usersService.loadUserByUsername(username);


        Customers customers = customerService.getByUsername(users.getUsername());

        Loans loans = LoanMapper.LoanDtoToEntity(createLoanDto);

        customers.setIncomeCertificate(String.valueOf(createLoanDto.incomeCertificate()));




           loans.setCustomers(customers);
//        // assigning other varibales
       loans.setLoanStatus(LoanStatus.PENDING);
//
//        loanRepository.save(loans);
//
//        // 3 save's are there
//        // 1 save income certifivate in the customer
        System.out.println(customers);
        customerService.saveIncomeCertificate(customers);
//        // 2 save the loan type and loan amount in the loan

        System.out.println(loans);
       Loans loanSaved= loanRepository.save(loans);
//        // 3 save collatrals


       // saving the collatrals

        if(createLoanDto.collatralName() != null && createLoanDto.collatralAddress()!= null)
            collatralService.addCollatralInCreateLoan(createLoanDto,loanSaved);










    }

    public void verifyLoan(@Valid VerifyTheLoanDto verifyTheLoanDto, String name) {

       Loans loans = loanRepository.findById(verifyTheLoanDto.loanId()).orElseThrow(() ->new ResourceNotFound("Loan id is invalid"));

       Users users = (Users) usersService.loadUserByUsername(name);

       if(!loans.getEmployees().getUsers().getUsername().equals(users.getUsername()))
           throw new AccountRemarksException("This account is no hold by this employee");


        Loans loans1 = LoanMapper.VerifyLoanDtoToEntity(loans,verifyTheLoanDto);

        loanRepository.save(loans1);


    }


    public GetAllLoansDtoForPagination getAllLoan(int page, int size) {
       // Pageable pageable = PageRequest.of(page, size);

        //List<GetLoanForEmployeeDto> loanDtoToEmployee = loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING);

      //  List<GetLoanForEmployeeDto> loanDtoToEmployee =loansPending.stream().map(LoanMapper:: EntityToDto).toList();
        Pageable pageable = PageRequest.of(page, size);

        Page<Loans> loans =   loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING,pageable);

        List<GetLoanForEmployeeDto> loan = loans.toList().stream().map(LoanMapper:: LoanEntToDto).toList();


        return  new GetAllLoansDtoForPagination(
              loan,
                loans.getTotalPages(),
                loans.getTotalElements()

        );



    }

    public Loans getById(long l) {
        return loanRepository.findById(l).orElseThrow(()->new ResourceNotFound("LoanId is invalid"));
    }

    public void AssigningEmpToLoan(AssignEmpsToLoanDto assignEmpsToLoanDto) {
        Loans loan = loanRepository.findById(assignEmpsToLoanDto.loanId()).orElseThrow(()->new ResourceNotFound("Account id is invalid"));

        Employees employees = employeeService.getById(assignEmpsToLoanDto.loanVerifier());
        Employees employees1 = employeeService.getById(assignEmpsToLoanDto.assestVerifier());
        Employees employees2 = employeeService.getById(assignEmpsToLoanDto.finaincialAnalyst());


        loan.setAssestVerifierId(assignEmpsToLoanDto.assestVerifier());
        loan.setFinancialAnalystId(assignEmpsToLoanDto.finaincialAnalyst());
        loan.setEmployees(employees);

        loanRepository.save(loan);
    }


    public List<LoanCustomerDto> getAllDetails(String name) {

        List<LoanCustomerDto>  l = loanRepository.getByUserDetails(name);
       List<LoanCustomerDto> fina  =  l.stream().distinct().toList();
        return fina;
    }

    public List<LoanResponseDto> getAllLoanDetails(String name) {

        return loanRepository.getAllLoanDetails(name);

    }

    public List<LoanResponseDto> getAllLoanDetailsById(long id ,String name) {


       return loanRepository.getAllLoanDetailsById(id,name);

//       return loans.stream().map(LoanMapper ::EntToDto ).toList();

    }

    public void verifyAndGiveRiskRates(RiskRateAndCollatralDto riskRateAndCollatralDto, String name) {

        Loans loans = getById(riskRateAndCollatralDto.loanId());
        Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(loans);
        System.out.println(name);

        loans.setRiskRating(riskRateAndCollatralDto.riskRating());

        loanRepository.save(loans);




    }

    public void loanConfirmation(LoanConfirmationDto loanConfirmationDto, String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        Loans loan = loanRepository.findById(loanConfirmationDto.loanId()).orElseThrow(()-> new AccountRemarksException("Account invalid"));


        loan.setCustomerLoanDecision(loanConfirmationDto.loanConfirmation());

        if(loanConfirmationDto.loanConfirmation().equals(CustomerLoanDecision.ACCEPTED)){
           transcationService.loanDisbursement(loanConfirmationDto.loanId(),name);


            loan.setLoanStatus(LoanStatus.ONGOING);
            BigDecimal interest = loan.getApprovedLoanAmount()
                    .multiply(loan.getIntrestRate())
                    .divide(BigDecimal.valueOf(100));

            BigDecimal total = loan.getApprovedLoanAmount().add(interest);
            loan.setLoanBalance(total);

        }


        loanRepository.save(loan);

    }

    public LoanWidgetDto loanWidget(String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        List<Loans> loans = loanRepository.getByUserName(name);
        int loanSize = loans.size();
        BigDecimal loanBalance = BigDecimal.ZERO;

        for(Loans l : loans){
            if (l.getLoanBalance() != null) {
                loanBalance = loanBalance.add(l.getLoanBalance());
            }
        }

        System.out.println(loanBalance);
        System.out.println(loanSize);

        return new LoanWidgetDto(
                loanBalance,
                loanSize
        );
    }

    public GetLoansForFinAnalystDto getLoanDetailsWithOtherLoans(int page, int size,String name) {

        // get the logged in employee if he is fincacial analyst
        Employees employee = employeeService.getByUsername(name);

        System.out.println(employee);

        if(!employee.getDesignation().equals(Designation.FINACIAL_ANALYST))
            throw new ResourceNotFound("Invalid employee");

        Pageable pageable = PageRequest.of(page,size);




        Page<Loans> listOfLoans = loanRepository.getByFinancialAnalystId(employee.getId(), LoanStatus.PENDING,pageable);

        List<getAllLoansForFinancialAnalyst> loans =  listOfLoans.toList().stream().map(LoanMapper::FinLoanToDto).toList();


        return  new GetLoansForFinAnalystDto(
                loans,
                listOfLoans.getTotalPages(),
                listOfLoans.getTotalElements()
        );




    }

    public LoanDetailsForFinancialAnalystDtoById getLoanDetailsByIdAndOtherLoans(long id, String name) {

        Loans loans = loanRepository.findById(id).orElseThrow(()->new AccountRemarksException("Invalid id"));

        long customerid = loans.getCustomers().getId();


        // get the loan details by the customer id

        List<Loans> existingActiveloans = loanRepository.getExistingActiveLoansOfCustomer(customerid,LoanStatus.ONGOING);

       List<LoanExistingDto> loan1 = existingActiveloans.stream().map(LoanMapper::existingLoanEntToDto).toList();


        return  new LoanDetailsForFinancialAnalystDtoById(
             loans.getId(),
             loans.getLoanType().toString(),
             loans.getRequestedLoanAmount() ,
             loans.getCustomers().getIncomeCertificate(),
             loan1
        );
    }

    public GetLoansForFinAnalystDto getAllLoansOfAssestVerifer(int page, int size,String name) {
        // get the logged in employee if he is AssestVerifer
        Employees employee = employeeService.getByUsername(name);

        System.out.println(employee);

        if(!employee.getDesignation().equals(Designation.ASSET_VERIFIER))
            throw new ResourceNotFound("Invalid employee");


        Pageable pageable = PageRequest.of(page,size);


        Page<Loans> listOfLoans = loanRepository.getByAssestVeriferId(employee.getId(), LoanStatus.PENDING , pageable);

        List<getAllLoansForFinancialAnalyst> loans =   listOfLoans.toList().stream().map(LoanMapper::FinLoanToDto).toList();

        return new GetLoansForFinAnalystDto(
                loans,
                listOfLoans.getTotalPages(),
                listOfLoans.getTotalElements()
        );

    }

    public LoansManagerDto getAllLoansForManager(int page , int size ,String name) {


        Employees employees = employeeService.getByUsername(name);

        // check he is manager
        if(!employees.getDesignation().equals(Designation.MANAGER))
            throw new AccountOwnerInvalidException("Invalid user");

        Pageable pageable = PageRequest.of(page,size);


        Page<Loans> loans = loanRepository.getByUserNameInEmp(name,LoanStatus.PENDING,pageable);


        List<LoansFotManagerDto> loan =loans.toList().stream().map(LoanMapper::manEntToDto).toList();

        return new LoansManagerDto(
                loan,
                loans.getTotalPages(),
                loans.getTotalElements()
        );


    }

    public LoanDtoForLoanManager loanDecisionForManager(long id, String name) {


        Loans loans = loanRepository.findById(id).orElseThrow(()-> new AccountRemarksException("Invalid account"));

        System.out.println(loans);

        List<Collatral> collatral = collatralRepository.getbyLoanId(loans);
        System.out.println(collatral.toString());

        List<CollatralDtoForLoanManager> collatrals = collatral.stream().map(CollatralMapper::CollatralEntToDtoForManager).toList();




        return new LoanDtoForLoanManager(
            collatrals,
                loans.getId(),
                loans.getRequestedLoanAmount(),
                loans.getLoanStatus().toString(),
                loans.getLoanType().toString(),
                loans.getRiskRating().toString(),
                loans.getCustomers().getName(),
                loans.getCustomers().getAddress(),
                loans.getCustomers().getGender().toString(),
                loans.getCustomers().getOccupation(),
                loans.getCustomers().getIncomeCertificate()
        );
    }


}
