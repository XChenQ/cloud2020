package com.atguigu.springcloud.myLoadBalanced;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyLoadBalanced {
    int increment();
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
