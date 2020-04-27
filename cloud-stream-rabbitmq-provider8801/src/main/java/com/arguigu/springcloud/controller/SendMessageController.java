package com.arguigu.springcloud.controller;

import com.arguigu.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {
    @Autowired
    IMessageProvider iMessageProvider;

    @GetMapping(value = "/send")
    public String send(){
        return iMessageProvider.send();
    }
}
