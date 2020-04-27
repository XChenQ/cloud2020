package com.atguigu.springcloud.myinterface;

//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;

public interface MyOutOrIn {
    String OUTPUT = "chenoutput";
    String INPUT = "cheninput";


//    @Output(MyOutOrIn.OUTPUT)
//    MessageChannel output();

//    @Input(MyOutOrIn.INPUT)
//    SubscribableChannel input();
}
