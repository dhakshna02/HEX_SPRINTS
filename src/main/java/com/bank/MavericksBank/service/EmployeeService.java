package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.*;
import com.bank.MavericksBank.exceptions.AccountOwnerInvalidException;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.EmployeeMapper;
import com.bank.MavericksBank.mapper.UserMapper;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.AccountsRepository;
import com.bank.MavericksBank.repository.EmployeeRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;
    private final LoanRepository loanRepository;
    private final AccountsRepository accountsRepository;

    public Employees getById(long empid) {
        return employeeRepository.findById(empid).orElseThrow(()-> new ResourceNotFound(" Employee id is invalid"));
    }

    public void addEmployee(EmployeeSignUpDto employeeSignUpDto) {
        Employees employees = EmployeeMapper.EmployeedtoToEmployee(employeeSignUpDto);

        Users users = UserMapper.EmpSignupDtoToEntity(employeeSignUpDto);

        users.setPassword(passwordEncoder.encode(employeeSignUpDto.password()));
        users.setRole(Role.EMPLOYEE);
        Users users1 = usersService.save(users);

        employees.setUsers(users1);

        employeeRepository.save(employees);

    }

    // get employee details
    public EmployeeDto getDetailsOfEmployee(long id) {
        Employees employee = getById(id);

        return  EmployeeMapper.emptoDto(employee);
    }


    public List<ManagerDto> getAllManager() {

        List<Employees> employees = employeeRepository.getAllManager(Designation.MANAGER);

            return employees.stream().map(EmployeeMapper::EmpToDto).toList();
    }

    public EmpIdsDto getAllEmployess() {

        List<Employees> employees =employeeRepository.getAllManager(Designation.MANAGER);
        List<Employees> assesstVerifier = employeeRepository.getAllManager(Designation.ASSET_VERIFIER);
        List<Employees> finacialAnalyst = employeeRepository.getAllManager(Designation.FINACIAL_ANALYST);



                List<ManagerDto> verifer =   employees.stream().map(EmployeeMapper::EmpToDto).toList();
        List<ManagerDto> assesstverifier = assesstVerifier.stream().map(EmployeeMapper::EmpToDto).toList();
        List<ManagerDto> finacialAnalys=  finacialAnalyst.stream().map(EmployeeMapper::EmpToDto).toList();


                return new EmpIdsDto(
                        verifer,
                        assesstverifier,
                        finacialAnalys

                );

    }

    public Employees getByUsername(String name) {
      return  employeeRepository.getByUserName(name);
    }

    public DesignationDto getDesignation(String name) {
        Employees employees=  employeeRepository.getByUserName(name);

        return new DesignationDto(
                employees.getDesignation().toString()
        );

    }

    public ManagerStatDto getManagerStat(String name) {

        Users users = (Users) usersService.loadUserByUsername(name);

        if(!users.getRole().equals(Role.ADMIN))
            throw new AccountOwnerInvalidException("Invalid Ownere");

        //no of acctive accounts
        int noOfAccountsActive = accountsRepository.getNoOfActiveAccounts(AccountStatus.ACTIVE);

        // no of account initated
        int noOfAccountsInitated = accountsRepository.getNoOfAcctsInitated(AccountOpeningStatus.PENDING);


        System.out.println(noOfAccountsActive);
        System.out.println(noOfAccountsInitated);



        // no of loans ongoing
        int noOfLoanOngoing = loanRepository.getNoOfLoansOnGoing(LoanStatus.ONGOING);

        int noOfLoansInited = loanRepository.getNoOfLoansOnGoing(LoanStatus.PENDING);


        System.out.println(noOfLoanOngoing);
        System.out.println(noOfLoansInited);
        return new ManagerStatDto(
                noOfLoanOngoing,
                noOfLoansInited,
                noOfAccountsActive,
                noOfAccountsInitated
        );

    }
}
// ipo empolyee the loan approve pana poraru so athu loan la varuma ila employee la varuma
