package org.smartfiles.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

@Configuration
public class StaticContentConfig {

	@Bean
	public RouterFunction<ServerResponse> imgRouter() {
		return RouterFunctions.resources("/smart-files/**", new ClassPathResource("static/"));
	}

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
				return chain
						.filter(exchange.mutate().request(request.mutate().contextPath(contextPath).build()).build());
			}
			return chain.filter(exchange);
		};
	}
}