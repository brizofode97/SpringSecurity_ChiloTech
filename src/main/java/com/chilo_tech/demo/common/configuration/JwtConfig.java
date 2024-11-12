package com.chilo_tech.demo.common.configuration;

import com.chilo_tech.demo.entity.Utilisateur;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtConfig {

    private final CustomUserDetails customUserDetails;

    @Value("${chilo.valeur.secret.key}")
    private String encryptionkey;

    private Key getKey(){
        final byte[] decode = Decoders.BASE64.decode(encryptionkey);
        return Keys.hmacShaKeyFor(decode);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur){

        final Instant now = Instant.now();
        final Instant expiration = now.plus(10, ChronoUnit.MINUTES);
        final Map<String, String> claims = Map.of(
                "nom", utilisateur.getNomComplet(),
                "email", utilisateur.getEmail());

        final JwtBuilder builder = Jwts.builder();

        String bearer =  builder
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .subject(utilisateur.getEmail())
                .claims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    public Map<String, String> generate(String username) {
        Utilisateur utilisateur = (Utilisateur) customUserDetails.loadUserByUsername(username);
        return generateJwt(utilisateur);
    }

}
