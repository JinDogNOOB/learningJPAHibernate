package com.jpabook.jpashop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
