package social.network.backend.socialnetwork.facade;

import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;

public interface UserFacade {

    GetUserDTO getUserById(final Integer id);

    GetUserDTO createUser(final CreateUserDTO createUserDTO);

    GetUserDTO updateUser(final UpdateUserDTO user);

    void deleteUser(final Integer id);
}