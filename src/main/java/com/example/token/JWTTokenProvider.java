
package com.example.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import com.example.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWTTokenProvider {

	
	@Value("${app.jwt-secrets}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private String jwtExpirationInMs;

	// generate token
	public String generateToken(Authentication authentication) {

	    String username = authentication.getName();
	    Date currentDate = new Date();
	    Date expiredDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpirationInMs));

	    return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(currentDate)
	            .setExpiration(expiredDate)
	            .signWith(SignatureAlgorithm.HS512, jwtSecret)
	            .compact();
	}

	// get username from token
	public String getUsernameFromToken(String token) {
	    Claims claims = Jwts.parser()
	            .setSigningKey(jwtSecret)
	            .parseClaimsJws(token)
	            .getBody();
	    return claims.getSubject();
	}

	// validate token
	public boolean validateToken(String token) {
	    try {
	        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
	        return true;
	    } catch (SignatureException ex) {
	        throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
	    } catch (MalformedJwtException ex) {
	        throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
	    } catch (ExpiredJwtException ex) {
	        throw new ApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
	    } catch (UnsupportedOperationException ex) {
	        throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupported  JWT token");
	    } catch (IllegalArgumentException ex) {
	        throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
	    }
	}
	
}
