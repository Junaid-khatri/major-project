package com.springboot.major_project.service;

import com.springboot.major_project.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRETKEY = "f3ef4f303d10f3dff46420489545aad6e5c3dc2435731b92be9ca975baa6b393";
    private static final int EXPIRATION_TIME = 600000;

    public String  generateToken(User user){
        return generateToken(new HashMap<String,Object>(), user);
    }
    public String generateToken(Map<String,Object> authorities, User user){
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setClaims(authorities)
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }
    public Key getKey(){
        byte [] key = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllCliams(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllCliams(String token){
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValidate(String token, UserDetails user){
        return (!(isExpired(token)) && extractName(token).equals(user.getUsername()));
    }

    public boolean isExpired(String token){
        return (extractExpiration(token).before(new Date()));
    }

}
