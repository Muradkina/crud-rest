package com.mk.muradbank.controller;

import com.mk.muradbank.dto.account.AccountDto;
import com.mk.muradbank.dto.account.CreateAccountRequest;
import com.mk.muradbank.dto.account.UpdateAccountRequest;
import com.mk.muradbank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>>getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());

    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto>getAccountById(@PathVariable String id){
        return ResponseEntity.ok(accountService.getAccountById(id));


    }


    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest accountRequest){
        return ResponseEntity.ok(accountService.createAccount(accountRequest));

    }
    @PutMapping("/{id}")
        public ResponseEntity<AccountDto>updateAccount(@PathVariable String id,
                                                       @RequestBody UpdateAccountRequest accountRequest){

        return ResponseEntity.ok(accountService.uptadeAcountDto(id,accountRequest));


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto>withdrawMoney(@PathVariable String id,@PathVariable Double amount){
        return ResponseEntity.ok(accountService.withdrawMoney(id,amount));

    }
    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDto>addMoney(@PathVariable String id,@PathVariable Double amount){
        return ResponseEntity.ok(accountService.addMoney(id,amount));

    }


}
