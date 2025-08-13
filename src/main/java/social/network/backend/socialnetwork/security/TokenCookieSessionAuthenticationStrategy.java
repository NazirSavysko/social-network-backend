package social.network.backend.socialnetwork.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.Objects;
import java.util.function.Function;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.SECONDS;

@Setter
public final class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private Function<Authentication, Token> tokenCookieFactory = new DefaultTokenCookieFactory();
    private Function<Token, String> tokenStringSerializer = Objects::toString;

    @Override
    public void onAuthentication(final Authentication authentication,final HttpServletRequest request,
                                 final HttpServletResponse response) throws SessionAuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            final Token token = this.tokenCookieFactory.apply(authentication);
            final String tokenString = this.tokenStringSerializer.apply(token);

            final Cookie cookie = new Cookie("__Host-auth-token", tokenString);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) SECONDS.between(now(), token.expiresAt()));

            response.addCookie(cookie);
        }
    }
}
