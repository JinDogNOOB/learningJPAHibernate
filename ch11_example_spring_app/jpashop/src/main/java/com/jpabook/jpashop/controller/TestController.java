package com.jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TestController {
    
    @RequestMapping(value="/test1", method=RequestMethod.GET)
    public String test1() {
        System.out.println("heheh");
        return "test";
    }

    @RequestMapping(value="/test2", method=RequestMethod.GET)
    public String test2() {
        return "/test/test";
    }
    
}
