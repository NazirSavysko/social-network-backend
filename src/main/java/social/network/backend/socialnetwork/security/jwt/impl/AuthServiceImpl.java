package social.network.backend.socialnetwork.security.jwt.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.security.jwt.AuthService;
import social.network.backend.socialnetwork.security.jwt.JwtTokenFactory;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenFactory jwtTokenFactory;
    private final AuthenticationManager authenticationManager;

    @Override
    public String authenticateAndIssueJwt(final String email, final String rawPassword) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword)
        );
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return this.jwtTokenFactory.createToken(userDetails);
    }


}
