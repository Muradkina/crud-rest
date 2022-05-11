package com.mk.muradbank.dto.account;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest extends BaseAccountRequest {

    @NotBlank(message = "Hesap kimliği boş olmamalıdır")
    private String id;
}
