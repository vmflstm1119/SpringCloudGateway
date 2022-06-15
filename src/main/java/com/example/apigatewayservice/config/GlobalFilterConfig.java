package com.example.apigatewayservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class GlobalFilterConfig implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.info("Global Pre Filter : request ID -> {}", request.getId());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Global Post Filter  : response Code -> {}", response.getStatusCode());
        }));
    }

    //Global Filter의 적용 순서를 위한 것
    @Override
    public int getOrder() {
        return -1;
    }
}
