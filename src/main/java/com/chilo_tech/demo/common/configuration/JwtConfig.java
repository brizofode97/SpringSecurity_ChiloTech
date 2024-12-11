package com.chilo_tech.demo.common.configuration;

import com.chilo_tech.demo.entity.Utilisateur;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtConfig {

    private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);
    private final CustomUserDetails customUserDetails;

    @Value("${chilo.valeur.secret.key}")
    private String encryptionkey;

    //IMPORTANT: Cette fonction permet de générer une clé cryptographique à partir d'une chaine encodée en Base64
    private Key getKey(){
        final byte[] decode = Decoders.BASE64.decode(encryptionkey);
//        log.info("🔔A quoi ressemble la cle : {}", Keys.hmacShaKeyFor(decode));
        return Keys.hmacShaKeyFor(decode);
    }

    //IMPORTANT: Fonction qui permet de générer le token
    public Map<String, String> generateJwt(Authentication authentication){

        final Instant now = Instant.now();
        final Instant expiration = now.plus(10, ChronoUnit.MINUTES);
        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
//        log.info("👌👌les infos de l'utilisateur après connexion : {}", utilisateur);
        final Map<String, Object> claims = Map.of(
                "name", utilisateur.getNomComplet(),
                "email", utilisateur.getEmail(),
                Claims.SUBJECT, utilisateur.getEmail(),
                Claims.EXPIRATION, Date.from(expiration));

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

    //IMPORTANT: Récupération des claims
    private Claims getAllClaims(String token) {

        JwtParserBuilder jwtParserBuilder = Jwts.parser();
        Claims claims = jwtParserBuilder
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        log.info("❤️❤️Les claims sont: {}", claims);
        return function.apply(claims);
    }

    //IMPORTANT: Vérifier si le token est expiré à partir des claims
    public boolean isTokenExpired(String token) {
        Instant dateExpiration = Objects.requireNonNull(getClaim(token, Claims::getExpiration)).toInstant();
        log.info("✔️ la date est expiré : {}", dateExpiration.isBefore(Instant.now()));
        return dateExpiration.isBefore(Instant.now());
    }

    //IMPORTANT: Extraire le username à partir du claim
    public String extractUsername(String token) {
        log.info("😍😍le username de l'utilisateur: {}", getClaim(token, Claims::getSubject));
        return getClaim(token, Claims::getSubject);
    }


}
