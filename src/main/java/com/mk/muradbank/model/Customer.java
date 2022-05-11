package com.mk.muradbank.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Customer {

    @Id
    private String id;

    private String ad;
    private Integer dogumYili;
    private City city;


    private String adres;


}
