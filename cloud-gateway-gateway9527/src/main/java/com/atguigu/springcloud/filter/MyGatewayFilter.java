package com.atguigu.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class MyGatewayFilter implements GlobalFilter,Ordered {
    @Override
    //Mono可以把它当成ModelAndView
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断第一个参数是否有sb
        //测试地址http://localhost:9527/payment/port?sb=1
        String first = exchange.getRequest().getQueryParams().getFirst("sb");
        if(first != null){
            System.out.println("sb-------------------");
            exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);
            //如果有sb这个参数就响应HttpStatus.BAD_GATEWAY
            return exchange.getResponse().setComplete();
        }
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;     //0表示全局的过滤器
    }
}
