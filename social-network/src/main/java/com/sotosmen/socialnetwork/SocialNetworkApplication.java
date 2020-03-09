package com.sotosmen.socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import com.sotosmen.socialnetwork.amqp.DestinationsConfig;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
public class SocialNetworkApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
	}
	
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
	
}
