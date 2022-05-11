package com.mk.muradbank.dto.account;

import com.mk.muradbank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {
    public  AccountDto convert(Account account){
       return AccountDto.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .paraBirimi(account.getParaBirimi())
                .build();


    }
}
