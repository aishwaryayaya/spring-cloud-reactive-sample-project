package com.example.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Prefilter extends AbstractGatewayFilterFactory<Prefilter.Config> {
    private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    public Prefilter(ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory) {
        super(Config.class);
        this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;

    }

    @Override
    public GatewayFilter apply(Config config) {
        return modifyRequestBodyGatewayFilterFactory.apply(c->{
            c.setRewriteFunction(String.class,String.class, (serverWebExchange, requestBody) ->{
                requestBody = requestBody.toUpperCase();
                return Mono.just(requestBody);
            });
        });

    }

    public static class Config{}
}
