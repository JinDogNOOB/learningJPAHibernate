package com.jpabook.jpashop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jpabook.jpashop.domain.Book;
import com.jpabook.jpashop.domain.Item;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.service.ItemService;

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
public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템_저장(){
        // given
        Item book = new Book();
        book.setName("test");
        book.setPrice(2000);
        book.setStockQuantity(2);
        //when
        itemService.saveItem(book);
        //then
        assertEquals(book, itemService.findOne(book.getId()));
    }

    public void 아이템_중복_수정(){
                // given
                Item book = new Book();
                book.setName("test");
                book.setPrice(2000);
                book.setStockQuantity(2);
                itemService.saveItem(book);
                //when
                Item book1 = new Book();
                book1.setId(book.getId());
                book1.setPrice(2001);
                itemService.saveItem(book1);
                //then
                assertEquals(book, book1);
    }


}
