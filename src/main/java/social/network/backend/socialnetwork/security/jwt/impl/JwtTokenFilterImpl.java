package social.network.backend.socialnetwork.security.jwt.impl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import social.network.backend.socialnetwork.security.jwt.JwtTokenReader;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@AllArgsConstructor
public final class JwtTokenFilterImpl extends OncePerRequestFilter {

    private static final int START_INDEX = 7;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final JwtTokenReader jwtTokenReader;

    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response, final @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            final String jwtToken = authHeader.substring(START_INDEX);
            final String username = this.jwtTokenReader.getUsername(jwtToken);

            if (username != null && getContext().getAuthentication() == null) {
                final List<GrantedAuthority> authorities = jwtTokenReader.getAuthorities(jwtToken);

                final UserDetails principal = User
                        .withUsername(username)
                        .password("N/A")
                        .authorities(authorities)
                        .accountExpired(false).accountLocked(false)
                        .credentialsExpired(false).disabled(false)
                        .build();

                final UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                final SecurityContext ctx = SecurityContextHolder.createEmptyContext();
                ctx.setAuthentication(auth);
                SecurityContextHolder.setContext(ctx);
            }
        }

        filterChain.doFilter(request, response);
    }
}
