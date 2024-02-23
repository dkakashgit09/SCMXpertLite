package com.scm.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//This component is responsible for JWT operations such as token generation, validation, and cookie handling
@Component
public class JwtUtils 
{

	@Value("${secret}")
	private String jwtSecret;
	
	@Value("${expire.time}")
	private Long jwtExpirationMs;
	
	public String extractUsername(String token) 
    {
		try 
		{
	        return extractClaim(token, Claims::getSubject);
	    } 
		catch (ExpiredJwtException e) 
		{
	        return null; 
	    }
    }
	
	public Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	public Claims extractAllClaims(String token) 
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	public Boolean isTokenExpired(String token) 
    {
		try
		{
			 Claims claims = extractAllClaims(token);
			 Date expiration = claims.getExpiration();
			 return expiration.before(new Date());
		}
        catch (ExpiredJwtException e) 
		{
        	return true;
		}
		catch (Exception e)
		{
			return false;
		}
		
    }
	
	public Boolean validateToken(String token, UserDetails userDetails) 
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	public String generateToken(String username, Set<String> roles){
        Map<String,Object> claims=new HashMap<>();
       
        claims.put("roles", roles);
        return createToken(claims,username);
    }
	
	private String createToken(Map<String, Object> claims, String username) 
    {
    	
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
	
	private Key getSignKey() 
    {
        byte[] keyBytes= Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> extractClaims(String token) 
    {
		 return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
	

}
