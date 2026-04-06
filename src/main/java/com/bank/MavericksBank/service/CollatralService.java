package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.CollatralDto;
import com.bank.MavericksBank.dto.CollatralResponseDto;
import com.bank.MavericksBank.dto.CollatralValueDto;
import com.bank.MavericksBank.exceptions.AccountRemarksException;
import com.bank.MavericksBank.mapper.CollatralMapper;
import com.bank.MavericksBank.model.Collatral;
import com.bank.MavericksBank.model.Loans;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.CollatralRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CollatralService {
    private final CollatralRepository collatralRepository;
    private  final UsersService usersService;
    private final LoanService loanService;

    public void addCollatral(CollatralDto collatralDto, String name) {


        Users users = (Users) usersService.loadUserByUsername(name);
        System.out.println(collatralDto.LoanId());
        Loans loans = loanService.getById(collatralDto.LoanId());
        System.out.println(loans);
        if(!loans.getCustomers().getUsers().getUsername().equals(users.getUsername()))
            throw new AccountRemarksException("This acoount is not hold by thsi user");

        Collatral collatral = CollatralMapper.CollatralDtoToEnt(collatralDto);
        collatralRepository.save(collatral);


    }

    public List<CollatralResponseDto> getCollatrals(long lid, String name) {

        Loans loans = loanService.getById(lid);

        if(!loans.getCustomers().getUsers().getUsername().equals(name))
            throw  new AccountRemarksException("Account is not hold by this customer");


        return collatralRepository.getAllCollatralsById(lid);

    }

    public void addCollatralValue(CollatralValueDto collatralValueDto, String name) {

   Users users = (Users) usersService.loadUserByUsername(name);

   Loans loans = loanService.getById(collatralValueDto.loanId());

   // need to chanfge the user table - if time perimists to that dhakshna dont forget

        Collatral collatral = collatralRepository.findById(collatralValueDto.collatralId()).orElseThrow(()->new AccountRemarksException("No collatral exist"));


        collatral.setCollatralValye(collatralValueDto.collatralValue());
        collatralRepository.save(collatral);
   }
}
