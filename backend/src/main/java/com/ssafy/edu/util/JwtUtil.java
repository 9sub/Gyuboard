package com.ssafy.edu.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.edu.model.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	public String createToken(MemberDto user) {
		Date now = new Date();
		Date exiryDate = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
				.setSubject(String.valueOf(user.getUserId()))
				.claim("userId", user.getUserId())
				.claim("writer", user.getWriter())
				.claim("name", user.getName())
				.setIssuedAt(now)
				.setExpiration(exiryDate)
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.compact();
	}
	
	public Claims parseToken(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

    public int getUserId(String token) {

        Claims claims = parseToken(token);

        return claims.get("userId", Integer.class);

    }

    public String getWriter(String token) {

        Claims claims = parseToken(token);

        return claims.get("writer", String.class);

    }
	
}
