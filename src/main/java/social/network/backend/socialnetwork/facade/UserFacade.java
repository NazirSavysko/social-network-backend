package social.network.backend.socialnetwork.facade;

import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;

public interface UserFacade {

    GetUserDTO getUserById(final Integer userId);

    GetUserDTO createUser(final CreateUserDTO createUserDTO, BindingResult result);

    GetUserDTO updateUser(final UpdateUserDTO user,BindingResult result);

    void deleteUser(final Integer id);
}