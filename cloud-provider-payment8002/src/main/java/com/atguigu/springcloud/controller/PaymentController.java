package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    DiscoveryClient discoveryClient;
    
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

    @GetMapping("/payment/discovery")
    public Map getServices(){
        Map map = new HashMap();
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        map.put("services", services);
        map.put("instances", instances);
        return map;
    }

    @GetMapping(value = "/payment/port")
    public CommonResult getPaymentPort(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMesage("success");
        commonResult.setData(serverPort);
        return  commonResult;
    }
}
