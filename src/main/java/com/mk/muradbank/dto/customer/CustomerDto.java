package com.mk.muradbank.dto.customer;

import com.mk.muradbank.dto.CityDto;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

public class CustomerDto {

    private String id;
    private String ad;
    private Integer dogumYili;
    private CityDto city;
    private String adres;
}