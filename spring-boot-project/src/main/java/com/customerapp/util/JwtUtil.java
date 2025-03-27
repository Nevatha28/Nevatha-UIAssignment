package com.customerapp.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private SecretKey SECRET_KEY;
	
	@Value("${jwt.expiration}")
	private long expirationTime;

	
	//Generate JWT Token
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	//Validate JWT Token
	public boolean validateToken(String token, UserDetails userDetails) {
		String extractedUsername = extractUsername(token);
		return extractedUsername.equals(userDetails) && !isTokenExpired(token);
		
	}
	//Extract Username from Token
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	//Extract Expiration date
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	//Generic method to extract claims
	private<T> T extractClaim(String token, Function<Claims, T> claimsresolver) {
		
		Claims claims = extractAllClaims(token);
		return claimsresolver.apply(claims);
	}
	
	//Extract all claims
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
	}
	
	//check if token is expired
	boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
