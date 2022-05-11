package com.mk.muradbank.dto.customer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateCustomerRequest  extends BaseCustomerRequest{
    private String id;

}
