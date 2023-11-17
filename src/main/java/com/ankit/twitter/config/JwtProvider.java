package com.ankit.twitter.config;

import com.ankit.twitter.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
public class JwtProvider {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        return Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000)).claim("email", authentication.getName()).signWith(key).compact();
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }

}