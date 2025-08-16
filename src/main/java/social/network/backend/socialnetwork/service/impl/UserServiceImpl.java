package social.network.backend.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.UserRepository;
import social.network.backend.socialnetwork.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static social.network.backend.socialnetwork.entity.enums.Role.ROLE_USER;
import static social.network.backend.socialnetwork.validation.ErrorMessages.ERROR_USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Contract("_ -> new")
    @Override
    public @NotNull User getUserById(final Integer id) {
        return this.getUserByIdOrTrow(id, ERROR_USER_NOT_FOUND);
    }

    @Override
    public void deleteUser(final Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public @NotNull User createUser(final String email, final String name, final String surname, final String password) {
        if(this.userRepository.existsByEmail(email)){
            throw new IllegalArgumentException(format("User with %s email already exists.",email));
        }

        final User user = User.builder()
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .role(ROLE_USER)
                .messages(List.of())
                .receivedMessages(List.of())
                .posts(List.of())
                .build();

        return this.userRepository.save(user);
    }

    @Override
    public @NotNull User updateUser(final Integer id, final String email, final String name, final String surname, final String password) {
        final User user = this.getUserByIdOrTrow(id, ERROR_USER_NOT_FOUND);

        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);

        return this.userRepository.save(user);
    }

    @Override
    public void isUserExistByIdOrThrow(final Integer id) {
        if (!this.userRepository.existsById(id)) {
            throw new NoSuchElementException(ERROR_USER_NOT_FOUND);
        }
    }

    @Override
    public User getUserByIdOrTrow(final Integer userId, final String errorMessage) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(errorMessage));
    }
}
