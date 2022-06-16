package com.microservices.cloudgateway.config;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secret;

    
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

	public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
    
    public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) {
		
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		
	}

   
}
