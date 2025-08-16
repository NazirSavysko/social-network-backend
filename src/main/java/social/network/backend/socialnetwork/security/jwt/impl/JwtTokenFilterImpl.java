package social.network.backend.socialnetwork.security.jwt.impl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import social.network.backend.socialnetwork.security.jwt.JwtTokenReader;

import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@AllArgsConstructor
public final class JwtTokenFilterImpl extends OncePerRequestFilter {

    private static final int START_INDEX = 7;

    private final JwtTokenReader jwtTokenReader;

    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response, final @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwtToken = authHeader.substring(START_INDEX);
            final String username = this.jwtTokenReader.getUsername(jwtToken);

            if (username != null && getContext().getAuthentication() == null) {
                final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        this.jwtTokenReader.getAuthorities(jwtToken)
                );

                getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request, response);
    }
}
