package social.network.backend.socialnetwork.facade.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.UserFacade;
import social.network.backend.socialnetwork.facade.mapper.Mapper;
import social.network.backend.socialnetwork.service.UserService;

import java.util.NoSuchElementException;

@Component
public final class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final Mapper<User, GetUserDTO> userMapper;
    private final Validator validator;

    @Autowired
    public UserFacadeImpl(final UserService userService,
                          final Mapper<User, GetUserDTO> userMapper,
                           final Validator validator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.validator = validator;
    }

    @Override
    public GetUserDTO getUserById(final Integer userId) {
        if (userId == null || userId <= 0) {

            throw new IllegalArgumentException("Message ID must be a positive integer.");
        } else {
            final User user = this.userService.getUserById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with userId: " + userId));

            return this.userMapper.toDto(user);
        }
    }

    @Override
    public GetUserDTO createUser(final @NotNull CreateUserDTO createUserDTO, final @NotNull BindingResult result) {
        this.validator.validate(createUserDTO, result);
        if (result.hasErrors()) {
            throw new IllegalArgumentException(
                    "Validation errors occurred: " + result.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                            .orElse("Unknown error")
            );
        } else {
            final User createdUser = this.userService.createUser(
                    createUserDTO.email(),
                    createUserDTO.name(),
                    createUserDTO.surname(),
                    createUserDTO.password()
            );

            return this.userMapper.toDto(createdUser);
        }
    }

    @Override
    public GetUserDTO updateUser(final UpdateUserDTO user, final @NotNull BindingResult result) {
        this.validator.validate(user, result);
        if (result.hasErrors()) {
            throw new IllegalArgumentException(
                    "Validation errors occurred: " + result.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                            .orElse("Unknown error")
            );
        }

        final User updatedUser = this.userService.updateUser(
                user.id(),
                user.email(),
                user.name(),
                user.surname(),
                user.password()
        );

        return this.userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(final Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }

        this.userService.deleteUser(id);
    }
}
