package social.network.backend.socialnetwork.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.auth.LoginDTO;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.facade.UserFacade;
import social.network.backend.socialnetwork.security.jwt.AuthService;
import social.network.backend.socialnetwork.validation.DtoValidator;

import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public final class AuthController {

    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer %s";

    private final AuthService authService;
    private final DtoValidator validator;
    private UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(final @RequestBody LoginDTO login, final BindingResult result) {
        validator.validate(login, result);

        final String token = this.authService.authenticateAndIssueJwt(login.email(), login.password());

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Map.of("token", token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(final @RequestBody CreateUserDTO createUserDTO, final BindingResult result, final UriComponentsBuilder uriComponentsBuilder) {
        final GetUserDTO createdUser = this.userFacade.createUser(createUserDTO, result);
        final String token = this.authService.authenticateAndIssueJwt(createUserDTO.email(), createUserDTO.password());

        return created(uriComponentsBuilder
                .replacePath("/api/v1/users/{userId}")
                .build(Map.of("userId", createdUser.id()))
        ).header(AUTHORIZATION, format(AUTHORIZATION_HEADER_VALUE, token))
                .body(createdUser);
    }
}
