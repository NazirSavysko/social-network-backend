package social.network.backend.socialnetwork.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdateUserPayload;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.facade.UserFacade;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/users/{userId:\\d+}")
public final class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ModelAttribute("user")
    public GetUserDTO getUserId(final @PathVariable("userId") Integer userId) {
        return this.userFacade.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<GetUserDTO> getUser(final @ModelAttribute("user") GetUserDTO user) {
        return ok(user);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(final @ModelAttribute(value = "user", binding = false) GetUserDTO user,
                                        final @RequestBody UpdateUserPayload updateUserPayload,
                                        final BindingResult result) {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                user.id(),
                updateUserPayload.name(),
                updateUserPayload.surname(),
                updateUserPayload.email(),
                updateUserPayload.password()
        );
        final GetUserDTO updatedUser = this.userFacade.updateUser(updateUserDTO, result);

        return ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(final @ModelAttribute(value = "user", binding = false) GetUserDTO user) {
        this.userFacade.deleteUser(user.id());

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Map.of("message", "User with id >" + user.id() + "< has been deleted"));
    }

}
