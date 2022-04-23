package org.smartfiles.handler;

import java.io.Serializable;

import org.smartfiles.model.User;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static org.smartfiles.utils.Constant.*;

@Component
public class TokenProvider implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 914895223455529931L;

	public String generateToken(User user) {
        final List<String> authorities = user.getRoles().stream()
                .map(r -> r.getName())
                .collect(Collectors.toList());
        String token =  Jwts.builder()
                .setSubject(user.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
        System.out.println("token " + token);
        return token;
    }
	
}
