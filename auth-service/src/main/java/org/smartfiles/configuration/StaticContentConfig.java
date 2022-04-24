package org.smartfiles.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.WebFilter;


@Configuration
public class StaticContentConfig {

    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public WebFilter contextPathWebFilter() {
        String contextPath = "/auth";
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getURI().getPath().startsWith(contextPath)) {
                return chain.filter(
                    exchange.mutate()
                    .request(request.mutate().contextPath(contextPath).build())
                    .build());
            }
            return chain.filter(exchange);
        };
    }
}