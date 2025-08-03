package social.network.backend.socialnetwork.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.facade.UserFacade;

import java.util.Map;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/v1/users")
public final class UsersController {

    private final UserFacade userFacade;

    @Autowired
    public UsersController(final UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(final @RequestBody CreateUserDTO createUserDTO,
                                        final BindingResult result,
                                        final UriComponentsBuilder uriComponentsBuilder) {
        final GetUserDTO createdUser = this.userFacade.createUser(createUserDTO, result);

        return created(uriComponentsBuilder
                        .replacePath("/api/v1/users/{userId}")
                        .build(Map.of("userId", createdUser.id()))
        ).body(createdUser);
    }
}
