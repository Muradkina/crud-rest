package com.mk.muradbank.dto.customer;

import com.mk.muradbank.dto.CityDto;
import com.mk.muradbank.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {
    public final CustomerDto convert(Customer customer){
        //customer ise burada customerDto ya çevirdim
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setAd(customer.getAd());
        customerDto.setDogumYili(customer.getDogumYili());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        customerDto.setAdres(customer.getAdres());
        return customerDto;

    }
}
