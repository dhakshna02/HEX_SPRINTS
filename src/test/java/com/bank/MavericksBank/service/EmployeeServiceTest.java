package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.enums.*;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.AccountsRepository;
import com.bank.MavericksBank.repository.EmployeeRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UsersService usersService;

    @Mock
    private AccountsRepository accountsRepository;
    @Mock
    private LoanRepository loanRepository;

    @Test
    public void getByIdTestWhenExists(){

        Users user = new Users();
        user.setId(1L);
        user.setUserName("Murugan");

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Murugan");
        employee.setEmail("murugan@mail.com");
        employee.setMobNo("9999999999");
        employee.setDesignation(Designation.ASSET_VERIFIER);
        employee.setUsers(user);

        Mockito.when(employeeRepository.findById(10L))
                .thenReturn(Optional.of(employee));

        Assertions.assertEquals(employee,
                employeeService.getById(10L));

        Mockito.verify(employeeRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getByIdWhenNotExits(){

        Mockito.when(employeeRepository.findById(10L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFound.class , ()->{
            employeeService.getById(10L);}
        );

        Assertions.assertEquals(" Employee id is invalid",e.getMessage());

        Mockito.verify(employeeRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getDetailsOfEmployeeTest(){

        Users user = new Users();
        user.setId(1L);
        user.setUserName("Murugan");

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Murugan");
        employee.setEmail("murugan@mail.com");
        employee.setMobNo("9999999999");
        employee.setDesignation(Designation.ASSET_VERIFIER);
        employee.setUsers(user);

        EmployeeDto expected = new EmployeeDto(
                employee.getName(),
                employee.getEmail(),
                employee.getMobNo(),
                employee.getDesignation()
        );

        Mockito.when(employeeRepository.findById(10L))
                .thenReturn(Optional.of(employee));

        Assertions.assertEquals(expected,
                employeeService.getDetailsOfEmployee(10L));

        Mockito.verify(employeeRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getDetailsOfEmployeeWhenNotExits(){

        Mockito.when(employeeRepository.findById(10L))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFound.class , ()->{
            employeeService.getDetailsOfEmployee(10L);}
        );

        Assertions.assertEquals(" Employee id is invalid",e.getMessage());

        Mockito.verify(employeeRepository,Mockito.times(1))
                .findById(10L);

    }

    @Test
    public void getAllManagerTest(){

        Employees emp1 = new Employees();
        emp1.setId(1L);
        emp1.setName("Manager1");
        emp1.setDesignation(Designation.MANAGER);

        Employees emp2 = new Employees();
        emp2.setId(2L);
        emp2.setName("Manager2");
        emp2.setDesignation(Designation.MANAGER);

        List<Employees> list = List.of(emp1,emp2);

        ManagerDto dto1 = new ManagerDto(
                emp1.getId(),
                emp1.getName()
        );

        ManagerDto dto2 = new ManagerDto(
                emp2.getId(),
                emp2.getName()
        );

        List<ManagerDto> expected = List.of(dto1,dto2);

        Mockito.when(employeeRepository.getAllManager(Designation.MANAGER))
                .thenReturn(list);

        Assertions.assertEquals(expected,
                employeeService.getAllManager());

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getAllManager(Designation.MANAGER);

    }

    @Test
    public void getAllEmployessTest(){

        Employees manager = new Employees();
        manager.setId(1L);
        manager.setName("Manager1");
        manager.setDesignation(Designation.MANAGER);

        Employees verifier = new Employees();
        verifier.setId(2L);
        verifier.setName("Verifier1");
        verifier.setDesignation(Designation.ASSET_VERIFIER);

        Employees analyst = new Employees();
        analyst.setId(3L);
        analyst.setName("Analyst1");
        analyst.setDesignation(Designation.FINACIAL_ANALYST);

        List<Employees> managers = List.of(manager);
        List<Employees> verifiers = List.of(verifier);
        List<Employees> analysts = List.of(analyst);

        ManagerDto mDto = new ManagerDto(manager.getId(),manager.getName());
        ManagerDto vDto = new ManagerDto(verifier.getId(),verifier.getName());
        ManagerDto aDto = new ManagerDto(analyst.getId(),analyst.getName());

        EmpIdsDto expected = new EmpIdsDto(
                List.of(mDto),
                List.of(vDto),
                List.of(aDto)
        );

        Mockito.when(employeeRepository.getAllManager(Designation.MANAGER))
                .thenReturn(managers);

        Mockito.when(employeeRepository.getAllManager(Designation.ASSET_VERIFIER))
                .thenReturn(verifiers);

        Mockito.when(employeeRepository.getAllManager(Designation.FINACIAL_ANALYST))
                .thenReturn(analysts);

        Assertions.assertEquals(expected,
                employeeService.getAllEmployess());

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getAllManager(Designation.MANAGER);

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getAllManager(Designation.ASSET_VERIFIER);

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getAllManager(Designation.FINACIAL_ANALYST);

    }

    @Test
    public void getByUsernameTest(){

        Users user = new Users();
        user.setId(1L);
        user.setUserName("Murugan");

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Murugan");
        employee.setEmail("murugan@mail.com");
        employee.setMobNo("9999999999");
        employee.setDesignation(Designation.MANAGER);
        employee.setUsers(user);

        Mockito.when(employeeRepository.getByUserName("Murugan"))
                .thenReturn(employee);

        Assertions.assertEquals(employee,
                employeeService.getByUsername("Murugan"));

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getByUserName("Murugan");

    }

    @Test
    public void getDesignationTest(){

        Users user = new Users();
        user.setId(1L);
        user.setUserName("Murugan");

        Employees employee = new Employees();
        employee.setId(10L);
        employee.setName("Murugan");
        employee.setEmail("murugan@mail.com");
        employee.setMobNo("9999999999");
        employee.setDesignation(Designation.MANAGER);
        employee.setUsers(user);

        DesignationDto expected = new DesignationDto(
                employee.getDesignation().toString()
        );

        Mockito.when(employeeRepository.getByUserName("Murugan"))
                .thenReturn(employee);

        Assertions.assertEquals(expected,
                employeeService.getDesignation("Murugan"));

        Mockito.verify(employeeRepository,Mockito.times(1))
                .getByUserName("Murugan");

    }

    @Test
    public void getManagerStatTest(){

        Users user = new Users();
        user.setUserName("Admin");
        user.setRole(Role.ADMIN);

        Mockito.when(usersService.loadUserByUsername("Admin"))
                .thenReturn(user);

        Mockito.when(accountsRepository.getNoOfActiveAccounts(AccountStatus.ACTIVE))
                .thenReturn(5);

        Mockito.when(accountsRepository.getNoOfAcctsInitated(AccountOpeningStatus.PENDING))
                .thenReturn(3);

        Mockito.when(loanRepository.getNoOfLoansOnGoing(LoanStatus.ONGOING))
                .thenReturn(4);

        Mockito.when(loanRepository.getNoOfLoansOnGoing(LoanStatus.PENDING))
                .thenReturn(2);

        ManagerStatDto expected = new ManagerStatDto(
                4,
                2,
                5,
                3
        );

        Assertions.assertEquals(expected,
                employeeService.getManagerStat("Admin"));

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getNoOfActiveAccounts(AccountStatus.ACTIVE);

        Mockito.verify(accountsRepository,Mockito.times(1))
                .getNoOfAcctsInitated(AccountOpeningStatus.PENDING);

        Mockito.verify(loanRepository,Mockito.times(1))
                .getNoOfLoansOnGoing(LoanStatus.ONGOING);

        Mockito.verify(loanRepository,Mockito.times(1))
                .getNoOfLoansOnGoing(LoanStatus.PENDING);

    }



}
