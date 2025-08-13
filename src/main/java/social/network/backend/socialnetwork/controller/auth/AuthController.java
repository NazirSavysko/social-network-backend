package social.network.backend.socialnetwork.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.network.backend.socialnetwork.dto.LoginDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.facade.UserFacade;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<?> login(final @RequestBody LoginDTO login,final BindingResult result) {
        final GetUserDTO user = this.userFacade.login(login,result);

        return ok(user);
    }
}
