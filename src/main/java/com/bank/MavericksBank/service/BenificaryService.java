package com.bank.MavericksBank.service;

import com.bank.MavericksBank.dto.BenficaryAddDto;
import com.bank.MavericksBank.dto.BenificaryReDto;
import com.bank.MavericksBank.dto.BenificaryResponseDto;
import com.bank.MavericksBank.exceptions.ResourceNotFound;
import com.bank.MavericksBank.mapper.BenificaryMapper;
import com.bank.MavericksBank.model.Accounts;
import com.bank.MavericksBank.model.Benificaries;
import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Users;
import com.bank.MavericksBank.repository.AccountsRepository;
import com.bank.MavericksBank.repository.BenificaryRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BenificaryService {

    private final UsersService usersService;
    private final BenificaryRepository benificaryRepository;
    private final CustomerService customerService;
    private final AccountService accountService;

    public void addBenificary(BenficaryAddDto dto, String name) {

        if(dto.ifsc().equals("IDBI")) {
            Accounts accounts = accountService.getById(dto.accountNumber());

            if(!accounts.getAccountStatus().equals("ACTIVE")){
                throw new ResourceNotFound("Invalid account");
            }

        }
        Benificaries benificaries = BenificaryMapper.DtoToEnt(dto);

        Customers customers = customerService.getByUsername(name);

        benificaries.setCustomers(customers);

        benificaryRepository.save(benificaries);

    }

    public BenificaryReDto getBenificary(int page, int size,String name) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Benificaries> benificariesList = benificaryRepository.getByUserName(name,pageable);
        List<BenificaryResponseDto> benifiacries=  benificariesList.toList().stream().map(BenificaryMapper::EntToDto).toList();

        return new BenificaryReDto(
                benifiacries,
                benificariesList.getTotalPages(),
                benificariesList.getTotalElements()
        );


    }
}
