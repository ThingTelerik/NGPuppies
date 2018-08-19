package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtTokenProvider {

    @Value("$app.jwtSecret")
    private String jwtSecret;

    @Value("$app.jwtExpirationsInMs")
    private String jwtExpirationInMs;


    public String generateToken(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();

        Date dateNow = new Date();

        Date expireDate = new Date(dateNow.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public Integer getUserIdFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException | MalformedJwtException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
