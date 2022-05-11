package com.mk.muradbank;

import com.mk.muradbank.model.Account;
import com.mk.muradbank.model.City;
import com.mk.muradbank.model.Customer;
import com.mk.muradbank.model.ParaBirimi;
import com.mk.muradbank.repository.AccountRepository;
import com.mk.muradbank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;

@SpringBootApplication
public class MuradbankApplication implements CommandLineRunner {
    public final AccountRepository accountRepository;
    public final CustomerRepository customerRepository;

    public MuradbankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MuradbankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer c1 = Customer.builder()
                .id("124578")
                .ad("Murad")
                .dogumYili(1989)
                .adres("Dumlupınar mh")
                .city(City.İSTANBUL)
                .build();

        Customer c2 = Customer.builder()
                .id("125578")
                .ad("Ebru")
                .dogumYili(1990)
                .adres("Dumlupınar mh")
                .city(City.İZMİR)
                .build();

        customerRepository.saveAll(Arrays.asList(c1,c2));
        Account a1=Account.builder()
                .id("101")
                .customerId("124578")
                .balance(4500.0)
                .paraBirimi(ParaBirimi.TRY)
                .city(City.İSTANBUL)
                .build();
        Account a2=Account.builder()
                .id("102")
                .customerId("125578")
                .balance(5000.0)
                .paraBirimi(ParaBirimi.TRY)
                .city(City.İSTANBUL)
                .build();
        accountRepository.saveAll(Arrays.asList(a1,a2));

    }

}


