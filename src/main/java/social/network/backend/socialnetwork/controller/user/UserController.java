package social.network.backend.socialnetwork.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdateUserPayload;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.facade.UserFacade;

@RestController
@RequestMapping("/api/v1/users/{userId:\\d+}")
public final class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ModelAttribute("user")
    public GetUserDTO getUserId(@PathVariable("userId") Integer userId) {

        return this.userFacade.getUserById(userId);
    }


    @GetMapping
    public ResponseEntity<GetUserDTO> getUser(@ModelAttribute("user") GetUserDTO user) {

        return ResponseEntity
                .ok(user);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(final @PathVariable("userId") Integer userId,
                                        final @RequestBody UpdateUserPayload updateUserPayload,
                                        final BindingResult result) {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                userId,
                updateUserPayload.name(),
                updateUserPayload.surname(),
                updateUserPayload.email(),
                updateUserPayload.password()
        );
        final GetUserDTO updatedUser = this.userFacade.updateUser(updateUserDTO, result);

        return ResponseEntity
                .ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        this.userFacade.deleteUser(userId);

        return ResponseEntity
                .ok()
                .build();
    }

}
