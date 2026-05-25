package com.em.Api_Gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

//filter class to validate JWT token
@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;
    private final Duration authServiceTimeout;

    public JwtValidationGatewayFilterFactory(
            WebClient.Builder webClientBuilder,
            @Value("${auth.service.url:http://localhost:4005}") String authServiceUrl,
            @Value("${auth.service.timeout:2s}") Duration authServiceTimeout
    ) {
        super(Object.class);
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
        this.authServiceTimeout = authServiceTimeout;
    }

    @Override
    public GatewayFilter apply(Object config){
        return (exchange,chain)->{
            String token=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token==null || !token.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION,token)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return chain.filter(exchange);
                        }

                        if (response.statusCode().is4xxClientError()) {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }

                        exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                        return exchange.getResponse().setComplete();
                    })
                    .timeout(authServiceTimeout)
                    .onErrorResume(ex -> completeWithStatus(exchange, HttpStatus.SERVICE_UNAVAILABLE));
        };
    }

    private Mono<Void> completeWithStatus(org.springframework.web.server.ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }
}
