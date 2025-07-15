package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Integer id);


    void deleteUser(Integer id);

    User createUser(String email, String name, String surname, String password);

    User updateUser(Integer id, String email, String name, String surname, String password);
}
