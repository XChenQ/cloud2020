package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.myLoadBalanced.MyLoadBalanced;
import com.atguigu.springcloud.service.PaymentFeginService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    PaymentFeginService paymentFeginService;

    @Autowired
    MyLoadBalanced myLoadBalanced;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
           return  restTemplate.postForObject(PAYMENT_URL + "/payment/create/",
                   payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return  restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id,
                CommonResult.class);
    }

    @GetMapping("/consumer/payment/lb")
    public CommonResult<Payment> getLbPayment(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        ServiceInstance increment = myLoadBalanced.instances(instances);
        URI uri = increment.getUri();
        return  restTemplate.getForObject(uri + "/payment/port",
                CommonResult.class);
    }

    @GetMapping("/consumer/payment/timeout")
    @HystrixCommand(fallbackMethod = "getHystrixTimeoutPayment",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "1500")
    })
    public CommonResult getTimeoutPayment(){
        CommonResult commonResult = paymentFeginService.getTimeoutPayment();
        return  commonResult;
    }

    public CommonResult getHystrixTimeoutPayment(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setMesage("success");
        commonResult.setData("consumer:Hystrix降级成功");
        System.out.println("consumer:Hystrix降级成功");
        return  commonResult;
    }
}
