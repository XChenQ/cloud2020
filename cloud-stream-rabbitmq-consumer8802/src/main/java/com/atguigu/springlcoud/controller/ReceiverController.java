package com.atguigu.springlcoud.controller;

import com.atguigu.springcloud.myinterface.MyOutOrIn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@EnableBinding(MyOutOrIn.class)
public class ReceiverController {
    @Value("${server.port}")
    private String port;

    @StreamListener(MyOutOrIn.INPUT)
    public void input(Message<String> message){
        System.out.println("接收到的消息：" + message.getPayload() + "\t" + port);
    }
}
