package org.smartfiles.handler;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import static org.smartfiles.utils.Constants.*;

@Component
public class TokenProvider implements Serializable {

	@Autowired
	@Value("classpath:/keys/private.key")
	private Resource privateKey;

	private static final long serialVersionUID = 5754947999230780853L;

	public String getUsernameFromToken(String token) throws SignatureException, ExpiredJwtException,
			UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) throws SignatureException, ExpiredJwtException,
			UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws SignatureException,
			ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) throws SignatureException, ExpiredJwtException,
			UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
		return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, IllegalArgumentException, IOException {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
}