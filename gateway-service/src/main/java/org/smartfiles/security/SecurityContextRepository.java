package org.smartfiles.security;

import java.io.IOException;

import org.smartfiles.handler.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

	private static final Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);

	private static final String TOKEN_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange swe) {
		ServerHttpRequest request = swe.getRequest();
		System.out.println("path " + request.getURI().getRawPath());
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		String authToken = null;
		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			authToken = authHeader.replace(TOKEN_PREFIX, "");
		} else {
			logger.warn("couldn't find bearer string, will ignore the header.");
		}
		if (authToken != null) {
			String userName = "";
			try {
				userName.concat(tokenProvider.getUsernameFromToken(authToken));
			} catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Authentication auth = new UsernamePasswordAuthenticationToken(userName, authToken);
			return this.authenticationManager.authenticate(auth)
					.map((authentication) -> new SecurityContextImpl(authentication));
		} else {
			return Mono.empty();
		}
	}

}
