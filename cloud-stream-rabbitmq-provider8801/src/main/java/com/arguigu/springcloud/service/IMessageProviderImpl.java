package com.arguigu.springcloud.service;

import com.atguigu.springcloud.myinterface.MyOutOrIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(MyOutOrIn.class)
public class IMessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel chenoutput;


    @Override
    public String send() {
        String  uuid = UUID.randomUUID().toString();
        chenoutput.send(MessageBuilder.withPayload(uuid).build());
        System.out.println("uuid \t " + uuid);
        return null;
    }
}
