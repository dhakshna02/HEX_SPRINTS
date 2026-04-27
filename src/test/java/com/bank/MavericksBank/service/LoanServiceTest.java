package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.*;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.CollatralMapper;
import com.bank.MavericksBank.mapper.LoanMapper;
import com.bank.MavericksBank.model.*;
import com.bank.MavericksBank.repository.CollatralRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private  UsersService usersService;
    @Mock
    private CollatralRepository collatralRepository;

    @Mock
    private EmployeeService employeeService;







    @Test
    public void getAllLoanTest(){

        Loans loan1 = new Loans();
        loan1.setId(1L);
        loan1.setLoanStatus(LoanStatus.PENDING);

        Loans loan2 = new Loans();
        loan2.setId(2L);
        loan2.setLoanStatus(LoanStatus.PENDING);
        Customers customer = new Customers();
        customer.setId(10L);

        loan1.setCustomers(customer);
        loan2.setCustomers(customer);

        Page<Loans> page = new PageImpl<>(List.of(loan1,loan2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(loanRepository.findAllbyPendingLoanApproval(LoanStatus.PENDING,pageable))
                .thenReturn(page);

        GetAllLoansDtoForPagination result =
                loanService.getAllLoan(0,2);

        Assertions.assertEquals(2,result.loans().size());

        Mockito.verify(loanRepository,Mockito.times(1))
                .findAllbyPendingLoanApproval(LoanStatus.PENDING,pageable);

    }

    @Test
    public void getByIdTestWhenExists(){

        Customers customer = new Customers();
        customer.setId(1L);
        customer.setName("Dhakshna");

        Employees employee = new Employees();
        employee.setId(2L);
        employee.setName("Murugan");

        Loans loan = new Loans();
        loan.setId(10L);
        loan.setLoanType(LoanType.HOME);
        loan.setRequestedLoanAmount(BigDecimal.valueOf(100000));
        loan.setApprovedLoanAmount(BigDecimal.valueOf(90000));
        loan.setIntrestRate(BigDecimal.valueOf(7.5));
        loan.setMonths(12);
        loan.setEmi(BigDecimal.valueOf(8000));
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setRiskRating(RiskRating.VERY_LOW_RISK);
        loan.setFinancialAnalystId(3L);
        loan.setAssestVerifierId(4L);
        loan.setCustomerLoanDecision(CustomerLoanDecision.ACCEPTED);
        loan.setCustomers(customer);
        loan.setCollatralStatus(CollatralStatus.SUFFICIENT);
        loan.setLoanBalance(BigDecimal.valueOf(90000));
        loan.setEmployees(employee);
        loan.setApprovedAt(LocalDate.now());

        Mockito.when(loanRepository.findById(10L))
                .thenReturn(Optional.of(loan));

        Assertions.assertEquals(loan,
                loanService.getById(10L));

        Mockito.verify(loanRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getByIdWhenNotExits(){

        Mockito.when(loanRepository.findById(10L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFound.class , ()->{
            loanService.getById(10L);}
        );

        Assertions.assertEquals("LoanId is invalid",e.getMessage());

        Mockito.verify(loanRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getAllDetailsTest(){

        LoanCustomerDto dto1 = new LoanCustomerDto(
                "Dhakshna",
                "test@mail.com",
                "Chennai",
                Gender.MALE,
                "Engineer",
                500000,
                "Aadhar",
                "EB Bill",
                "ABCDE1234F",
                "cert.pdf",
                "photo.png"
        );

        LoanCustomerDto dto2 = new LoanCustomerDto(
                "Dhakshna",
                "test@mail.com",
                "Chennai",
                Gender.MALE,
                "Engineer",
                500000,
                "Aadhar",
                "EB Bill",
                "ABCDE1234F",
                "cert.pdf",
                "photo.png"
        );

        List<LoanCustomerDto> list = List.of(dto1,dto2);

        Mockito.when(loanRepository.getByUserDetails("Dhakshna"))
                .thenReturn(list);

        List<LoanCustomerDto> result =
                loanService.getAllDetails("Dhakshna");

        Assertions.assertEquals(1,result.size());

        Mockito.verify(loanRepository,Mockito.times(1))
                .getByUserDetails("Dhakshna");

    }

    @Test
    public void getAllLoanDetailsTest(){

        LoanResponseDto dto1 = new LoanResponseDto(
                1L,
                LoanType.HOME,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(90000),
                12,
                BigDecimal.valueOf(8000),
                LoanStatus.PENDING,
                BigDecimal.valueOf(7.5),
                BigDecimal.valueOf(90000),
                LocalDate.now(),
                "Dhakshna"
        );

        LoanResponseDto dto2 = new LoanResponseDto(
                2L,
                LoanType.VEHICLE,
                BigDecimal.valueOf(200000),
                BigDecimal.valueOf(180000),
                24,
                BigDecimal.valueOf(9000),
                LoanStatus.ONGOING,
                BigDecimal.valueOf(8.0),
                BigDecimal.valueOf(170000),
                LocalDate.now(),
                "Dhakshna"
        );

        List<LoanResponseDto> list = List.of(dto1,dto2);

        Mockito.when(loanRepository.getAllLoanDetails("Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(list,
                loanService.getAllLoanDetails("Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1))
                .getAllLoanDetails("Dhakshna");

    }

    @Test
    public void getAllLoanDetailsByIdTest(){

        LoanResponseDto dto1 = new LoanResponseDto(
                1L,
                LoanType.HOME,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(90000),
                12,
                BigDecimal.valueOf(8000),
                LoanStatus.PENDING,
                BigDecimal.valueOf(7.5),
                BigDecimal.valueOf(90000),
                LocalDate.now(),
                "Dhakshna"
        );

        List<LoanResponseDto> list = List.of(dto1);

        Mockito.when(loanRepository.getAllLoanDetailsById(1L,"Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(list,
                loanService.getAllLoanDetailsById(1L,"Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1))
                .getAllLoanDetailsById(1L,"Dhakshna");

    }

    @Test
    public void loanWidgetTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");

        Loans loan1 = new Loans();
        loan1.setLoanBalance(BigDecimal.valueOf(1000));

        Loans loan2 = new Loans();
        loan2.setLoanBalance(BigDecimal.valueOf(2000));

        List<Loans> list = List.of(loan1,loan2);

        LoanWidgetDto expected = new LoanWidgetDto(
                BigDecimal.valueOf(3000),
                list.size()
        );

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Mockito.when(loanRepository.getByUserName("Dhakshna"))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                loanService.loanWidget("Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1))
                .getByUserName("Dhakshna");

    }

    @Test
    public void getLoanDetailsWithOtherLoansTest(){

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Analyst");
        employee.setDesignation(Designation.FINACIAL_ANALYST);

        Loans loan1 = new Loans();
        loan1.setId(1L);
        loan1.setLoanType(LoanType.HOME);
        loan1.setRequestedLoanAmount(BigDecimal.valueOf(100000));
        loan1.setLoanStatus(LoanStatus.PENDING);

        Loans loan2 = new Loans();
        loan2.setId(2L);
        loan2.setLoanType(LoanType.VEHICLE);
        loan2.setRequestedLoanAmount(BigDecimal.valueOf(200000));
        loan2.setLoanStatus(LoanStatus.PENDING);

        Page<Loans> pageObj = new PageImpl<>(List.of(loan1,loan2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(employeeService.getByUsername("Dhakshna"))
                .thenReturn(employee);

        Mockito.when(loanRepository.getByFinancialAnalystId(10L,LoanStatus.PENDING,pageable))
                .thenReturn(pageObj);

        GetLoansForFinAnalystDto result =
                loanService.getLoanDetailsWithOtherLoans(0,2,"Dhakshna");

        Assertions.assertEquals(2,result.loans().size());

        Mockito.verify(loanRepository,Mockito.times(1))
                .getByFinancialAnalystId(10L,LoanStatus.PENDING,pageable);

    }

    @Test
    public void getLoanDetailsByIdAndOtherLoansTest(){

        Customers customer = new Customers();
        customer.setId(5L);
        customer.setIncomeCertificate("cert.pdf");

        Loans loan = new Loans();
        loan.setId(1L);
        loan.setLoanType(LoanType.HOME);
        loan.setRequestedLoanAmount(BigDecimal.valueOf(100000));
        loan.setCustomers(customer);

        Loans existingLoan = new Loans();
        existingLoan.setId(2L);
        existingLoan.setLoanType(LoanType.VEHICLE);
        existingLoan.setRequestedLoanAmount(BigDecimal.valueOf(50000));
        existingLoan.setLoanStatus(LoanStatus.ONGOING);

        List<Loans> existingList = List.of(existingLoan);

        LoanExistingDto dto = LoanMapper.existingLoanEntToDto(existingLoan);

        List<LoanExistingDto> dtoList = List.of(dto);

        LoanDetailsForFinancialAnalystDtoById expected =
                new LoanDetailsForFinancialAnalystDtoById(
                        loan.getId(),
                        loan.getLoanType().toString(),
                        loan.getRequestedLoanAmount(),
                        loan.getCustomers().getIncomeCertificate(),
                        dtoList
                );

        Mockito.when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        Mockito.when(loanRepository.getExistingActiveLoansOfCustomer(5L,LoanStatus.ONGOING))
                .thenReturn(existingList);

        Assertions.assertEquals(expected,
                loanService.getLoanDetailsByIdAndOtherLoans(1L,"Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1))
                .findById(1L);

        Mockito.verify(loanRepository,Mockito.times(1))
                .getExistingActiveLoansOfCustomer(5L,LoanStatus.ONGOING);

    }

    @Test
    public void getAllLoansOfAssestVeriferTest(){

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Verifier");
        employee.setDesignation(Designation.ASSET_VERIFIER);

        Loans loan1 = new Loans();
        loan1.setId(1L);
        loan1.setLoanType(LoanType.HOME);
        loan1.setRequestedLoanAmount(BigDecimal.valueOf(100000));
        loan1.setLoanStatus(LoanStatus.PENDING);

        Loans loan2 = new Loans();
        loan2.setId(2L);
        loan2.setLoanType(LoanType.VEHICLE);
        loan2.setRequestedLoanAmount(BigDecimal.valueOf(200000));
        loan2.setLoanStatus(LoanStatus.PENDING);

        Page<Loans> page = new PageImpl<>(List.of(loan1,loan2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(employeeService.getByUsername("Dhakshna"))
                .thenReturn(employee);

        Mockito.when(loanRepository.getByAssestVeriferId(10L,LoanStatus.PENDING,pageable))
                .thenReturn(page);

        GetLoansForFinAnalystDto result =
                loanService.getAllLoansOfAssestVerifer(0,2,"Dhakshna");

        Assertions.assertEquals(2,result.loans().size());

        Mockito.verify(loanRepository,Mockito.times(1))
                .getByAssestVeriferId(10L,LoanStatus.PENDING,pageable);

    }

    @Test
    public void getAllLoansForManagerTest(){

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Manager");
        employee.setDesignation(Designation.MANAGER);

        Loans loan1 = new Loans();
        loan1.setId(1L);
        loan1.setLoanType(LoanType.HOME);
        loan1.setLoanStatus(LoanStatus.PENDING);
        loan1.setRequestedLoanAmount(BigDecimal.valueOf(100000));

        Loans loan2 = new Loans();
        loan2.setId(2L);
        loan2.setLoanType(LoanType.VEHICLE);
        loan2.setLoanStatus(LoanStatus.PENDING);
        loan2.setRequestedLoanAmount(BigDecimal.valueOf(200000));

        Page<Loans> page = new PageImpl<>(List.of(loan1,loan2));

        Pageable pageable = PageRequest.of(0,2);

        Mockito.when(employeeService.getByUsername("Dhakshna"))
                .thenReturn(employee);

        Mockito.when(loanRepository.getByUserNameInEmp("Dhakshna",LoanStatus.PENDING,pageable))
                .thenReturn(page);

        LoansManagerDto result =
                loanService.getAllLoansForManager(0,2,"Dhakshna");

        Assertions.assertEquals(2,result.loans().size());

        Mockito.verify(loanRepository,Mockito.times(1))
                .getByUserNameInEmp("Dhakshna",LoanStatus.PENDING,pageable);

    }
    @Test
    public void loanDecisionForManagerTest(){

        Customers customer = new Customers();
        customer.setId(1L);
        customer.setName("Dhakshna");
        customer.setAddress("Chennai");
        customer.setGender(Gender.MALE);
        customer.setOccupation("Engineer");
        customer.setIncomeCertificate("cert.pdf");

        Loans loan = new Loans();
        loan.setId(10L);
        loan.setRequestedLoanAmount(BigDecimal.valueOf(100000));
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setLoanType(LoanType.HOME);
        loan.setRiskRating(RiskRating.VERY_LOW_RISK);
        loan.setCustomers(customer);

        Collatral col = new Collatral();
        col.setCollataralname("House");
        col.setCollatralType(CollatralTypes.VEHICLE);
        col.setCollatralValye(BigDecimal.valueOf(500000));

        List<Collatral> list = List.of(col);

        CollatralDtoForLoanManager dto =
                CollatralMapper.CollatralEntToDtoForManager(col);

        List<CollatralDtoForLoanManager> dtoList = List.of(dto);

        LoanDtoForLoanManager expected =
                new LoanDtoForLoanManager(
                        dtoList,
                        loan.getId(),
                        loan.getRequestedLoanAmount(),
                        loan.getLoanStatus().toString(),
                        loan.getLoanType().toString(),
                        loan.getRiskRating().toString(),
                        loan.getCustomers().getName(),
                        loan.getCustomers().getAddress(),
                        loan.getCustomers().getGender().toString(),
                        loan.getCustomers().getOccupation(),
                        loan.getCustomers().getIncomeCertificate()
                );

        Mockito.when(loanRepository.findById(10L))
                .thenReturn(Optional.of(loan));

        Mockito.when(collatralRepository.getbyLoanId(loan))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                loanService.loanDecisionForManager(10L,"Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1))
                .findById(10L);

        Mockito.verify(collatralRepository,Mockito.times(1))
                .getbyLoanId(loan);

    }

}
