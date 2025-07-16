package social.network.backend.socialnetwork.service.impl;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.service.UserService;

import java.util.Optional;

import static java.util.Optional.of;
import static social.network.backend.socialnetwork.utils.EntityDtoFactory.createUserEntity;

@Service
public final class UserServiceImpl implements UserService {

    @Contract("_ -> new")
    @Override
    public @NotNull Optional<User> getUserById(final Integer id) {

        return of(createUserEntity());
    }

    @Override
    public void deleteUser(final Integer id) {

    }

    @Override
    public User createUser(final String email, final String name, final String surname, final String password) {

        return createUserEntity();
    }

    @Override
    public User updateUser(final Integer id, final String email, final String name, final String surname, final String password) {

        return createUserEntity();
    }
}
