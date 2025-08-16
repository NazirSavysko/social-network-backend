package social.network.backend.socialnetwork.security.jwt.impl;

import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.security.jwt.JwtTokenFactory;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.Jwts.SIG.HS256;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.claims;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public final class JwtTokenFactoryImpl implements JwtTokenFactory {

    private final SecretKey secret;
    private final Duration duration;

    public JwtTokenFactoryImpl(@Value("${jwt.secret}") String secret, @Value("${jwt.lifetime}") Duration duration) {
        this.secret = hmacShaKeyFor(BASE64.decode(secret));
        this.duration = duration;
    }


    @Override
    public String createToken(final UserDetails userDetails) {
        return this.generateToken(userDetails);
    }

    private String generateToken(final UserDetails userDetails) {
        final Claims claims = this.buildClaims(userDetails);

        final Date issuedDate = new Date();
        final Date expiredDate = new Date(issuedDate.getTime() + this.duration.toMillis());

        return builder()
                .claims(claims)
                .signWith(this.secret, HS256)
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .subject(userDetails.getUsername())
                .compact();
    }

    private @NotNull Claims buildClaims(final @NotNull UserDetails userDetails) {
        final List<String> authorities = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return claims()
                .add("authorities", authorities)
                .build();
    }
}
