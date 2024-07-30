package com.univr.pump.insulinpump.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/charts")
    public String statistics(){
        return ("/statisticscharts");
    }

}
