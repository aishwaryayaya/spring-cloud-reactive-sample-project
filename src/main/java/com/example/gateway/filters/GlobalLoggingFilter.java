package com.example.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class GlobalLoggingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        logger.info("[GlobalFilter] Incoming Request: {} {}", request.getMethod(), request.getURI());
        logger.debug("[GlobalFilter] Headers: {}", request.getHeaders());
        logger.info("request:{}",exchange.getRequest().getMethod());
        //change the headers
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().headers(headers -> headers.remove(HttpHeaders.CONTENT_LENGTH)).build();
        exchange = exchange.mutate().request(modifiedRequest).build();
        return chain.filter(exchange);
    }
}
