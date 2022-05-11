package com.mk.muradbank.dto.customer;

import com.mk.muradbank.dto.CityDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseCustomerRequest {
    private String ad;
    private Integer dogumYili;
    private CityDto city;
    private String adres;
}
