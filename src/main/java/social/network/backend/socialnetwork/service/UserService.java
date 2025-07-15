package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.User;

public interface UserService {

    User getUserById(Integer id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Integer id);
}
