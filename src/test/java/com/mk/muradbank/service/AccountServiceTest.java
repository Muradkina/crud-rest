package com.mk.muradbank.service;

import com.mk.muradbank.dto.account.AccountDto;
import com.mk.muradbank.dto.account.AccountDtoConverter;
import com.mk.muradbank.dto.account.CreateAccountRequest;
import com.mk.muradbank.dto.account.UpdateAccountRequest;
import com.mk.muradbank.model.Account;
import com.mk.muradbank.model.City;
import com.mk.muradbank.model.Customer;
import com.mk.muradbank.model.ParaBirimi;
import com.mk.muradbank.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

//1->İlk datalar yazılır
//2->daha sonra when kısımlarını yaz
//3->asert yapılır

public class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;

    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);
        accountService = new AccountService(accountRepository, customerService, accountDtoConverter);


    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setParaBirimi(ParaBirimi.TRY);
        createAccountRequest.setCity(City.İSTANBUL);

        Customer customer = Customer.builder()
                .id("12345")
                .ad("Murad")
                .dogumYili(1989)
                .adres("Dumlupınar mh")
                .city(City.İSTANBUL)
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .customerId(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .paraBirimi(createAccountRequest.getParaBirimi())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .paraBirimi(ParaBirimi.TRY)
                .balance(100.0)
                .build();


        //Mockito.when()-->> yalancı servislerimizi çağırır
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);
        //servisimin dışına çıktıgım herhangi bir nokta kalmadığından  artık sevisimin metdounu çağırabilirim

        //createAccount metodunu test ettiğmizden dolayı accountService.createAccount

        AccountDto sonuc = accountService.createAccount(createAccountRequest);
        //karşılaştırmalar yapılır. Ne ile yapılır?
        //Assert ile yapılır.verdiğimiz değerleri karşılaştırır. eşitse değerler eşit sonuç döner

        Assert.assertEquals(sonuc, accountDto);

        //Mockito.verify()-->>/Mockito.when() metodu ile çağırılan yalancı metotları Mockito.verify().metodu ile test et
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);

    }

    //2->2.seneryomuz CustomerServisten bana null bir customer dönmesidir
    //createAccount çağırdığım zaman neileolmayan bir customerla ne yapmalı?boş bi accountdto dönmeli)
    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmtyAccountDto() {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setParaBirimi(ParaBirimi.TRY);
        createAccountRequest.setCity(City.İSTANBUL);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto sonuc = accountService.createAccount(createAccountRequest);
        Assert.assertEquals(expectedAccountDto, sonuc);

        //eğer custumer nesnem boşsa diğer kodları çalıştırmayacağndan test seneryoma Mockito.verifyNoInteractions()
        //Mockito.verifyNoInteractions()-->>içerisinde verdiğim mock yanı metoduma hiç gidilmediğini belirtir
        //benım seneryomda gidilmeyen yerler accountRepositry v accountDtoConverter
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);

    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithOutId_itShouldReturnEmtyAccountDto() {
        //yanlışlıkla veritabanında id lerin silinmesi ya da boşluk bırakılarak girilmesi durumunda

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setParaBirimi(ParaBirimi.TRY);
        createAccountRequest.setCity(City.İSTANBUL);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                .id(" ")
                .build());


        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto sonuc = accountService.createAccount(createAccountRequest);
        Assert.assertEquals(expectedAccountDto, sonuc);

        //eğer custumer nesnem boşsa diğer kodları çalıştırmayacağndan test seneryoma Mockito.verifyNoInteractions()
        //Mockito.verifyNoInteractions()-->>içerisinde verdiğim mock yanı metoduma hiç gidilmediğini belirtir
        //benım seneryomda gidilmeyen yerler accountRepositry v accountDtoConverter
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);

    }

    //servislerden herhangi birinin exception fırlattığı durumda ki senaryo junit testi
    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setParaBirimi(ParaBirimi.TRY);
        createAccountRequest.setCity(City.İSTANBUL);

        Customer customer = Customer.builder()
                .id("12345")
                .ad("Murad")
                .dogumYili(1989)
                .adres("Dumlupınar mh")
                .city(City.İSTANBUL)
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .customerId(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .paraBirimi(createAccountRequest.getParaBirimi())
                .city(createAccountRequest.getCity())
                .build();




        //Mockito.when()-->> yalancı servislerimizi çağırır
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("mk mk"));

        /*Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);*/ //-->bu metot çalışmıcak
       //Mockito.verifyNoInteractions(accountDtoConverter) yap


        //servisimin dışına çıktıgım herhangi bir nokta kalmadığından  artık sevisimin metdounu çağırabilirim

        //createAccount metodunu test ettiğmizden dolayı accountService.createAccount

         accountService.createAccount(createAccountRequest);
        //karşılaştırmalar yapılır. Ne ile yapılır?
        //Assert ile yapılır.verdiğimiz değerleri karşılaştırır. eşitse değerler eşit sonuç döner



        //Mockito.verify()-->>/Mockito.when() metodu ile çağırılan yalancı metotları Mockito.verify().metodu ile test et
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);

    }




}