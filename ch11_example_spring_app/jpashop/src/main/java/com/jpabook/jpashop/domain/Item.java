package com.jpabook.jpashop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    // ### 비즈니스 로직 ##
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) throws Exception {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new Exception("not enough stock");
        }
        this.stockQuantity = restStock;
    }
     
}


