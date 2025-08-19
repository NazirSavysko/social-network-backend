package social.network.backend.socialnetwork.security.jwt.impl;

import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.security.jwt.JwtTokenReader;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static social.network.backend.socialnetwork.validation.ErrorMessages.*;

@Component
public final class JwtTokenReaderImpl implements JwtTokenReader {

    private final static String AUTHORITIES_KEY = "authorities";

    private final SecretKey secret;

    public JwtTokenReaderImpl(@Value("${jwt.secret}") final String secret) {
        this.secret = hmacShaKeyFor(BASE64.decode(secret));
    }

    @Override
    public String getUsername(final String token) {
        return this.getClaims(token).getSubject();
    }

    @Override
    public @NotNull @Unmodifiable List<GrantedAuthority> getAuthorities(final String token) {
        final Object raw = getClaims(token).get(AUTHORITIES_KEY);
        if (raw == null) {
            throw new IllegalArgumentException(ERROR_AUTHORITIES_NULL);
        }

        if (!(raw instanceof List<?> list)) {
            throw new IllegalArgumentException(ERROR_AUTHORITIES_NOT_LIST);
        }

        final List<GrantedAuthority> out = new ArrayList<>(list.size());
        list.forEach(it -> {
            if (!(it instanceof String s) || s.isBlank()) {
                throw new IllegalArgumentException(ERROR_AUTHORITIES_INVALID_ITEM);
            }

            out.add(new SimpleGrantedAuthority(s));
        });

        return out.stream()
                .distinct()
                .toList();
    }

    @Contract("_ -> new")
    private Claims getClaims(final String token) {
        return parser()
                .verifyWith(this.secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
