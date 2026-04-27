package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.enums.RemarkStatus;
import com.bank.MavericksBank.model.Remarks;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.RemarksReposiotry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RemarksServiceTest {
    @InjectMocks
    private RemarksService remarksService;

    @Mock
    private RemarksReposiotry remarksReposiotry;
    @Mock
    private UsersService usersService;

    @Test
    public void viewingAllRemarksTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");

        Remarks remark1 = new Remarks();
        remark1.setId(1L);
        remark1.setRemarks("Test Remark 1");

        Remarks remark2 = new Remarks();
        remark2.setId(2L);
        remark2.setRemarks("Test Remark 2");

        List<Remarks> list = List.of(remark1,remark2);

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Mockito.when(remarksReposiotry.viewingAllRemarks("Dhakshna",RemarkStatus.ACTIVE))
                .thenReturn(list);

        List<ViewRemarksDto> result =
                remarksService.viewingAllRemarks("Dhakshna");

        Assertions.assertEquals(2,result.size());

        Mockito.verify(remarksReposiotry,Mockito.times(1))
                .viewingAllRemarks("Dhakshna", RemarkStatus.ACTIVE);

    }

    @Test
    public void viewingAllLoanRemarksTest(){

        Users user = new Users();
        user.setUserName("Dhakshna");

        Remarks remark1 = new Remarks();
        remark1.setId(1L);
        remark1.setRemarks("Loan Remark 1");

        Remarks remark2 = new Remarks();
        remark2.setId(2L);
        remark2.setRemarks("Loan Remark 2");

        List<Remarks> list = List.of(remark1,remark2);

        Mockito.when(usersService.loadUserByUsername("Dhakshna"))
                .thenReturn(user);

        Mockito.when(remarksReposiotry.viewingAllLoanRemarks("Dhakshna",RemarkStatus.ACTIVE))
                .thenReturn(list);

        List<ViewRemarksDto> result =
                remarksService.viewingAllLoanRemarks("Dhakshna");

        Assertions.assertEquals(2,result.size());

        Mockito.verify(remarksReposiotry,Mockito.times(1))
                .viewingAllLoanRemarks("Dhakshna",RemarkStatus.ACTIVE);

    }
}
