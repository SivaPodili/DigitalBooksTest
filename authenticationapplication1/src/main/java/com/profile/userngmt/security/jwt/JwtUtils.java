package com.profile.userngmt.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.profile.userngmt.service.AuthenticationDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@RefreshScope
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${invalid.jwt.signature}")
	String invalidSignature;

	@Value("${invalid.jwt.token}")
	String invalidToken;

	@Value("${jwt.token.expired}")
	String tokenExpired;

	@Value("${jwt.token.unsupported}")
	String tokenUnsupported;

	@Value("${jwt.claim.empty}")
	String claimEmpty;

	public String generateJwtToken(Authentication authentication) {

		AuthenticationDetailsImpl userPrincipal = (AuthenticationDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error(invalidSignature, e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error(invalidToken, e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error(tokenExpired, e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error(tokenUnsupported, e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error(claimEmpty, e.getMessage());
		}

		return false;
	}
}
