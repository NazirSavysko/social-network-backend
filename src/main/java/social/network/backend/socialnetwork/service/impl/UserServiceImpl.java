package social.network.backend.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> getUserById(final Integer id) {
        return Optional.of(
                User.builder()
                        .id(id)
                        .build()
        );
    }



    @Override
    public void deleteUser(final Integer id) {

    }

    @Override
    public User createUser(final String email, final String name, final String surname, final String password) {
        return User.builder()
                .id(1)
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .build();
    }

    @Override
    public User updateUser(final Integer id, final String email, final String name, final String surname, final String password) {
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .build();
    }
}
