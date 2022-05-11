package com.mk.muradbank.service;

import com.mk.muradbank.dto.account.AccountDto;
import com.mk.muradbank.dto.account.AccountDtoConverter;
import com.mk.muradbank.dto.account.CreateAccountRequest;
import com.mk.muradbank.dto.account.UpdateAccountRequest;
import com.mk.muradbank.exception.CustomerNotFoundException;
import com.mk.muradbank.model.Account;
import com.mk.muradbank.model.Customer;
import com.mk.muradbank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;


    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {

        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if (customer.getId()==null ||customer.getId().trim().equals("")){ //customer.getId yi getir içindeki boşlukları sil
            /*return AccountDto.builder().build();*///boş birtane accountdto dönsün
            throw new CustomerNotFoundException("Müşteri kaydı yok!");

        }
        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .customerId(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .paraBirimi(createAccountRequest.getParaBirimi())
                .city(createAccountRequest.getCity())
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));

    }

    public AccountDto uptadeAcountDto(String id, UpdateAccountRequest accountRequest) {
        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());
        if (customer.getId().equals("") || customer.getId() == null) {
            return AccountDto.builder().build();//boş birtane accountdto dönsün

        }
        Optional<Account> optionalAccount = accountRepository.findById(id);
        optionalAccount.ifPresent(account -> {
            account.setBalance(accountRequest.getBalance());
            account.setCustomerId(accountRequest.getCustomerId());
            account.setCity(accountRequest.getCity());
            account.setParaBirimi(accountRequest.getParaBirimi());
            accountRepository.save(account);
        });

        return optionalAccount.map(accountDtoConverter::convert).orElse(new AccountDto());


    }

    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();

        return accountList.stream()
                .map(accountDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountById(String id) {
        return accountRepository.findById(id)
                .map(accountDtoConverter::convert)
                .orElse(new AccountDto());

      /*  Optional<Account>accountOptional=accountRepository.findById(id);
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());*/
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);

    }
    public  AccountDto withdrawMoney(String id,Double amount){
       //1-> accountu id ile sorgula
        Optional<Account>accountOptional=accountRepository.findById(id);
        //2->eğer accountum varsa
        accountOptional.ifPresent(account -> {
            if (account.getBalance()>amount){
                account.setBalance(account.getBalance()-amount);
                accountRepository.save(account);
            }else{
                System.out.println("yetersiz bakiye -> accountId"+id+
                        "balance:"+account.getBalance()+"amount:" +" "+amount);
            }
        });
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());

    }

    public  AccountDto addMoney(String id,Double amount){
        Optional<Account>accountOptional=accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(account.getBalance()+amount);
            accountRepository.save(account);
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }
}

