package social.network.backend.socialnetwork.security;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import static java.time.Duration.ofDays;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Setter
public final class DefaultTokenCookieFactory implements Function<Authentication, Token> {

    private Duration tokenTtl = ofDays(1);

    @Override
    public @NotNull Token apply(final @NotNull Authentication authentication) {
        final Instant now = now();

        return new Token(randomUUID(), authentication.getName(),
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList(),
                now, now.plus(this.tokenTtl));
    }
}
