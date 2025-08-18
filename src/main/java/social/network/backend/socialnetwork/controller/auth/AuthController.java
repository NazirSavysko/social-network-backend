package social.network.backend.socialnetwork.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.network.backend.socialnetwork.dto.auth.LoginDTO;
import social.network.backend.socialnetwork.security.jwt.JwtTokenFactory;
import social.network.backend.socialnetwork.validation.DtoValidator;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenFactory jwtTokenFactory;
    private final DtoValidator validator;


    @PostMapping("/login")
    public ResponseEntity<?> login(final @RequestBody LoginDTO login, final BindingResult result) {
        validator.validate(login, result);

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.email(), login.password())
        );

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(login.email());
        final String token = this.jwtTokenFactory.createToken(userDetails);

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(
                        Map.of(
                                "token", token
                        )
                );
    }
}
