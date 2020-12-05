package com.jpabook.jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("A")
public class Album extends Item{
    private String artist;
    private String etc;
    
    
}
