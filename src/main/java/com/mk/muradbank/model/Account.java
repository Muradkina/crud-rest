package com.mk.muradbank.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;


    private String customerId;
    private Double balance;
    private City city;
    private ParaBirimi paraBirimi;


}
