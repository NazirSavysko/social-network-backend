package social.network.backend.socialnetwork.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

;

public interface JwtTokenFactory {

    String createToken(UserDetails userDetails);
}
