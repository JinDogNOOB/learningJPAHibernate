package com.jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
