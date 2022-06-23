package com.backend.vaccinebookingsystem.domain.dto;

import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${vaccinebookingsystem.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsDao userDetailsDao = (UserDetailsDao) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetailsDao.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired Jwt Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid Jwt Token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid Jwt Signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt Claim is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
