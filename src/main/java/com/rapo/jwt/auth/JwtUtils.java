package com.rapo.jwt.auth;

import com.rapo.jwt.auth.model.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static com.rapo.jwt.auth.InitLoad.privateKey;
import static com.rapo.jwt.auth.InitLoad.publicKey;

@Component
public class JwtUtils {

    public static JwtResponse generateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000); // 1 hour in milliseconds
        return new JwtResponse(Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(privateKey)
                .compact(),"Bearer",3600,null);
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}