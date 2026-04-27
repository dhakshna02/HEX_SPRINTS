package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.enums.CollatralTypes;
import com.bank.MavericksBank.enums.Designation;
import com.bank.MavericksBank.model.Employees;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.repository.CollatralRepository;
import com.bank.MavericksBank.repository.EmployeeRepository;
import com.bank.MavericksBank.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CollatralServiceTest {

    @InjectMocks
    private CollatralService collatralService;

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CollatralRepository collatralRepository;

    @Mock
    private LoanService loanService;


    @Test
    public void getCollatralsTest(){

        Loans loan = new Loans();
        loan.setId(1L);
        loan.setAssestVerifierId(10L);

        Employees emp = new Employees();
        emp.setId(10L);
        emp.setDesignation(Designation.ASSET_VERIFIER);


        CollatralResponseDto dto = new CollatralResponseDto(
                1L,
                2L,
                "House",
                CollatralTypes.PROPERTY,
                "Chennai",
                "doc.pdf"
        );

        Mockito.when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        Mockito.when(employeeRepository.findById(10L))
                .thenReturn(Optional.of(emp));

        Mockito.when(collatralRepository.getAllCollatralsById(1L))
                .thenReturn(dto);

        Assertions.assertEquals(dto,
                collatralService.getCollatrals(1L,"Dhakshna"));

        Mockito.verify(loanRepository,Mockito.times(1)).findById(1L);
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(10L);
        Mockito.verify(collatralRepository,Mockito.times(1)).getAllCollatralsById(1L);

    }
}
