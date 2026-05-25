package com.em.Api_Gateway.filter;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtValidationGatewayFilterFactoryTest {

    @Test
    void allowsRequestWhenTokenIsValid() {
        ExchangeFunction exchangeFunction = request -> Mono.just(ClientResponse.create(HttpStatus.OK).build());
        JwtValidationGatewayFilterFactory factory = new JwtValidationGatewayFilterFactory(
                WebClient.builder().exchangeFunction(exchangeFunction),
                "http://auth-service",
                Duration.ofMillis(100)
        );

        AtomicBoolean chainCalled = new AtomicBoolean(false);
        GatewayFilter filter = factory.apply(new Object());
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/employees")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .build()
        );
        GatewayFilterChain chain = serverWebExchange -> {
            chainCalled.set(true);
            return Mono.empty();
        };

        StepVerifier.create(filter.filter(exchange, chain)).verifyComplete();

        assertTrue(chainCalled.get());
        assertNull(exchange.getResponse().getStatusCode());
    }

    @Test
    void rejectsRequestWhenAuthServiceReturnsUnauthorized() {
        ExchangeFunction exchangeFunction = request -> Mono.just(ClientResponse.create(HttpStatus.UNAUTHORIZED).build());
        JwtValidationGatewayFilterFactory factory = new JwtValidationGatewayFilterFactory(
                WebClient.builder().exchangeFunction(exchangeFunction),
                "http://auth-service",
                Duration.ofMillis(100)
        );

        AtomicBoolean chainCalled = new AtomicBoolean(false);
        GatewayFilter filter = factory.apply(new Object());
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/employees")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .build()
        );
        GatewayFilterChain chain = serverWebExchange -> {
            chainCalled.set(true);
            return Mono.empty();
        };

        StepVerifier.create(filter.filter(exchange, chain)).verifyComplete();

        assertFalse(chainCalled.get());
        assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
    }
}
