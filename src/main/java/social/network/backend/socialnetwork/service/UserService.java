package social.network.backend.socialnetwork.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.User;

public interface UserService {

    @Transactional(readOnly = true)
    @NotNull
    User getUserById(Integer id);

    @Transactional(rollbackFor = Exception.class)
    void deleteUser(Integer id);

    @Transactional(rollbackFor = Exception.class)
    User createUser(String email, String name, String surname, String password);

    @Transactional(rollbackFor = Exception.class)
    User updateUser(Integer id, String email, String name, String surname, String password);

    void isUserExistByIdOrThrow(Integer id);

    User getUserByIdOrTrow(final Integer userId, final String errorMessage);
}
