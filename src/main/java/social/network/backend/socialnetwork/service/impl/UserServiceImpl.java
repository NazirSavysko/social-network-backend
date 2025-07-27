package social.network.backend.socialnetwork.service.impl;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.UserRepository;
import social.network.backend.socialnetwork.service.UserService;

import java.util.Optional;

import static social.network.backend.socialnetwork.entity.enums.Role.ROLE_USER;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull Optional<User> getUserById(final Integer id) {

        return this.userRepository.findById(id);
    }

    @Override
    public void deleteUser(final Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public @NotNull User createUser(final String email, final String name, final String surname, final String password) {
        final User user = User.builder()
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .role(ROLE_USER)
                .build();

        return this.userRepository.save(user);
    }


    @Override
    public @NotNull User updateUser(final Integer id, final String email, final String name, final String surname, final String password) {
      final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found "));

        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);

        return this.userRepository.save(user);

    }
}
