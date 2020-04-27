package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int i = paymentService.create(payment);
        log.info("创建完成 serverport" + serverPort);
        CommonResult commonResult = new CommonResult();
        if (i > 0){
            commonResult.setCode(200);
            commonResult.setMesage("success");
            commonResult.setData(i);
            return  commonResult;
        }else {
            commonResult.setCode(400);
            commonResult.setMesage("fail");
            commonResult.setData(null);
            return  commonResult;
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询完成serverport" + serverPort);
        CommonResult commonResult = new CommonResult();
        if (payment != null){
            commonResult.setCode(200);
            commonResult.setMesage("success");
            commonResult.setData(payment);
            return  commonResult;
        }else {
            commonResult.setCode(400);
            commonResult.setMesage("fail");
            commonResult.setData(null);
            return  commonResult;
        }
    }

    @GetMapping(value = "/payment/port")
    public CommonResult getPaymentPort(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMesage("success");
        commonResult.setData(serverPort);
        return  commonResult;
    }

    @GetMapping("/payment/timeout")
    @HystrixCommand(fallbackMethod = "getHystrixTimeoutPayment",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "3000")
    })
    public CommonResult getTimeoutPayment(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMesage("success");
        commonResult.setData("Feign调用成功");
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return  commonResult;
    }

    public CommonResult getHystrixTimeoutPayment(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMesage("success");
        commonResult.setData("payment:Hystrix降级成功");
        System.out.println("payment:Hystrix降级成功");
        return  commonResult;
    }
    
}
