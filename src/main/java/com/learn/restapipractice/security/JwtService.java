package com.learn.restapipractice.security;

import com.learn.restapipractice.entity.User;
import com.learn.restapipractice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private int expiration ;

    private SecretKey Key(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email,String role){
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+expiration))
                .signWith(Key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return parseClaims(token).getSubject();
    }

    public boolean isValidToken(String token,String email){
        try{
            Claims claims = parseClaims(token);
            return claims.getSubject().equals(email) && claims.getExpiration().after(new Date());
        }
        catch (Exception e){ return false;}
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(Key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractRole(String token){
        return parseClaims(token).get("role").toString();
    }

}
