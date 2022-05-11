package com.mk.muradbank.dto.account;

import com.mk.muradbank.model.City;
import com.mk.muradbank.model.ParaBirimi;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseAccountRequest {

    @NotBlank(message = "Müşteri kimliği boş olmamalıdır")
    private String customerId;
    @NotNull
    @Min(0)
    private Double balance;
    @NotNull(message = "Para birimi boş olmamalıdır")
    private ParaBirimi paraBirimi;
    @NotNull(message = "Şehir Boş olmamalıdır")
    private City city;
}
