package it.goodgamegroup.up.configurations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Configuration
public class ZoomConfiguration {

    @Value("${zoom.zoomApiSecret}")
    private static String zoomApiSecret;

    @Value("${zoom.zoomApiKey}")
    private static String zoomApiKey;

    public static String generateZoomJWTTOken() {
        String id = UUID.randomUUID().toString().replace("-", "");
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date creation = new Date(System.currentTimeMillis());
        Date tokenExpiry = new Date(System.currentTimeMillis() + (1000 * 60));

        Key key = Keys.hmacShaKeyFor(zoomApiSecret.getBytes());
        return Jwts.builder()
                .setId(id)
                .setIssuer(zoomApiKey)
                .setIssuedAt(creation)
                .setSubject("")
                .setExpiration(tokenExpiry)
                .signWith(key)
                .compact();
    }
}
