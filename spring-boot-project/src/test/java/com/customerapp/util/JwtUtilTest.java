package com.customerapp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtUtil jwtUtil;

    private SecretKey secretKey;
    private long expirationTime;
    private String token;

    @BeforeEach
    public void setUp() {
    	secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        expirationTime = 1000 * 60 * 60; // 1 hour
        when(userDetails.getUsername()).thenReturn("testuser");

        token = jwtUtil.generateToken("testuser");
    }

    @Test
    public void testGenerateToken() {
        String generatedToken = jwtUtil.generateToken("testuser");
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(generatedToken).getBody();

        assertEquals("testuser", claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    public void testValidateToken() {
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    public void testExtractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testExtractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    public void testIsTokenExpired() {
        assertFalse(jwtUtil.isTokenExpired(token));
    }
}
