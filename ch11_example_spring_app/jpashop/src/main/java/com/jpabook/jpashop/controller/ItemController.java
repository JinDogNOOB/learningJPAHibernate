package com.jpabook.jpashop.controller;

import java.util.List;

import com.jpabook.jpashop.domain.Book;
import com.jpabook.jpashop.domain.Item;
import com.jpabook.jpashop.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ItemController {
    
    @Autowired ItemService itemService;

    @RequestMapping(value = "/items/new", method = RequestMethod.GET)
    public String createForm(){
        return "items/createItemForm";
    }

    @RequestMapping(value = "/items/new", method = RequestMethod.POST)
    public String create(Book item){
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @RequestMapping(value="/items", method=RequestMethod.GET)
    public String requestMethodName(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
    
}
