package org.cibertec.edu.pe.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class AuthProvider {
    @Value("${config.security.keySecret}")
    private String keySecret;

    public String createToken(Usuario authUser) {
        Map<String, Object> claims = Jwts.claims().setSubject(authUser.getEmail());
        claims.put("id", authUser.getId());
        Date now = new Date();
        Date exp = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authUser.getId().toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, keySecret.getBytes())
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(keySecret.getBytes()).parseClaimsJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(keySecret.getBytes()).parseClaimsJwt(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return String.format("Bad token: %s", e.getMessage());
        }
    }
}
