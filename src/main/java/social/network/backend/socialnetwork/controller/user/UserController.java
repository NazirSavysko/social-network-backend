package social.network.backend.socialnetwork.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;

@RestController
@RequestMapping("/api/v1/users/{userId:\\d+}")
public class UserController {


    @ModelAttribute("user")
    public GetUserDTO getUserId(@PathVariable("userId") Integer userId) {
        return null;
    }


    @GetMapping
    public ResponseEntity<GetUserDTO> getUser(@ModelAttribute("user") GetUserDTO user) {

        return ResponseEntity.ok()
                .body(user);
    }


    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

        return ResponseEntity.status(200).build();
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId) {

        return ResponseEntity.status(200).build();
    }

}
