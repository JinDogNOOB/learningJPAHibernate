package com.jpabook.jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("B")
public class Book extends Item{

    private String author;
    private String isbn;
    
}
