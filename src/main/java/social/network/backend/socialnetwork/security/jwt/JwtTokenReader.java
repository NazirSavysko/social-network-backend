package social.network.backend.socialnetwork.security.jwt;


import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface JwtTokenReader {
    String getUsername(String token);

    List<GrantedAuthority> getAuthorities(String token);
}
