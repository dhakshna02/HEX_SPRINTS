package com.bank.MavericksBank.mapper;

import com.bank.MavericksBank.dto.ViewRemarksDto;
import com.bank.MavericksBank.model.Remarks;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class RemarksMapper {
    public static ViewRemarksDto entToDto(Remarks remarks) {

        return  new ViewRemarksDto(

                remarks.getId(),
                remarks.getRemarks()

        );
    }
}
