package org.smartfiles.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.smartfiles.handler.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import reactor.core.publisher.Mono;
import static org.smartfiles.utils.Constants.AUTHORITIES_KEY;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	@SuppressWarnings("unchecked")
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		String username;
		UsernamePasswordAuthenticationToken auth = null;
		try {
			username = tokenProvider.getUsernameFromToken(authToken);
		} catch (Exception e) {
			username = null;
		}
		try {
			if (username != null && !tokenProvider.isTokenExpired(authToken)) {
				Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
				List<String> roles = claims.get(AUTHORITIES_KEY, List.class);
				List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
						.collect(Collectors.toList());
				auth = new UsernamePasswordAuthenticationToken(username, authToken, authorities);
				SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(username, authorities));
				return Mono.just(auth);
			} else {
				return Mono.empty();
			}
		} catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
				| IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mono.just(auth);
	}

}
