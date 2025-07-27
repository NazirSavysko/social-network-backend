package social.network.backend.socialnetwork.service;

import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.User;

import java.util.Optional;

public interface UserService {

    @Transactional(readOnly = true)
    Optional<User> getUserById(Integer id);

    @Transactional(rollbackFor = Exception.class)
    void deleteUser(Integer id);

    @Transactional(rollbackFor = Exception.class)
    User createUser(String email, String name, String surname, String password);

    @Transactional(rollbackFor = Exception.class)
    User updateUser(Integer id, String email, String name, String surname, String password);
}
