package com.jpabook.jpashop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Book;
import com.jpabook.jpashop.domain.Item;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class OrderServiceTest {
    
    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Test 
    public void 상품주문() throws Exception{
        // given
        Member member = new Member();
        member.setName("test1");
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        address.setZipcode("zipcode");
        member.setAddress(address);
        memberService.join(member);

        Item item = new Book();
        item.setName("name");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);

        int orderCount = 2;

        //When 
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야함");
        assertEquals(10000*2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다");
    }

    // 따로 사용자 Exception을 만들어둬야할듯..
    @Test(expected = Exception.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11; // 재고보다 많은 수량

        //When
        orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        fail("재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    public void 주문취소() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("aa", 1000, 10);
        int orderCount = 1;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL이어야함");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야한다");
    }






    // ### tool for testing
    private Member createMember(){
        Member member = new Member();
        member.setName("test1");
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        address.setZipcode("zipcode");
        member.setAddress(address);
        memberService.join(member);

        return member;
    }
    private Book createBook(String name, int price, int quantity){
        Book item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(quantity);
        itemService.saveItem(item);
        return item;
    }

}
