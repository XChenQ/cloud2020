package com.atguigu.springcloud.myLoadBalanced;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class myLoadBalancedImpl implements MyLoadBalanced {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    

    @Override
    public int increment() {
        int next = 0;
        boolean flag = true;
        do{
            int current = atomicInteger.get();
            next = current + 1;
            flag = atomicInteger.compareAndSet(current, next);
            log.info("*****第"+next+"次");
        } while(!flag);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = increment() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
