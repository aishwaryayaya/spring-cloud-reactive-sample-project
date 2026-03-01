package com.example.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PostFilter extends AbstractGatewayFilterFactory<PostFilter.Config> {
    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public PostFilter(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory) {
        super(Config.class);
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return modifyResponseBodyGatewayFilterFactory.apply(c->{
            c.setRewriteFunction(String.class,String.class, (serverWebExchange, requestBody) ->{
                requestBody = requestBody.toUpperCase() + "Modified Response";
                return Mono.just(requestBody);
            });
        });

    }

    public static class Config{}
}
