package social.network.backend.socialnetwork.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;

@RestController
@RequestMapping("/api/v1/users")
public final class UsersController {

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(final @RequestBody CreateUserDTO createUserDTO) {

        return ResponseEntity.status(201).build();
    }

}
