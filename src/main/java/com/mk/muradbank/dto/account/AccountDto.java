package com.mk.muradbank.dto.account;

import com.mk.muradbank.model.ParaBirimi;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDto {
    private String id;

    private String customerId;
    private Double balance;
    private ParaBirimi paraBirimi;
}
