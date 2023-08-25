package com.justreadit.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private String secretKey = "newJwtTokenKey";

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token); 
		System.out.println(System.currentTimeMillis()); 
		System.out.println(expirationDate.getTime()); 
		System.out.println(new Date().getTime());  
		System.out.println("The Difference is :"+(expirationDate.getTime()-System.currentTimeMillis()));
		return (expirationDate.getTime()-System.currentTimeMillis())>0;
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token); 
		System.out.println("VALIDATION TOKEN:"+userName+" "+userDetails.getUsername()+" "+isTokenExpired(token));
		return (userName.equals(userDetails.getUsername()) && isTokenExpired(token));
	}

}
