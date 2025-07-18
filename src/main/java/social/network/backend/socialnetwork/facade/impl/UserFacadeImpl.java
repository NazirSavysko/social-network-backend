package social.network.backend.socialnetwork.facade.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.UserFacade;
import social.network.backend.socialnetwork.facade.mapper.Mapper;
import social.network.backend.socialnetwork.service.UserService;
import social.network.backend.socialnetwork.validation.DtoValidator;

import java.util.NoSuchElementException;

import static java.lang.String.format;
import static social.network.backend.socialnetwork.validation.ValidationMessage.ERROR_USER_ID_MUST_BE_POSITIVE;

@Component
public final class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final Mapper<User, GetUserDTO> userMapper;
    private final DtoValidator validator;

    @Autowired
    public UserFacadeImpl(final UserService userService,
                          final Mapper<User, GetUserDTO> userMapper,
                          final DtoValidator validator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.validator = validator;
    }

    @Override
    public GetUserDTO getUserById(final Integer userId) {
        this.validateUserId(userId);

        final User user = this.userService.getUserById(userId)
                .orElseThrow(() ->
                        new NoSuchElementException(format("User not found with userId: %d", userId))
                );

        return this.userMapper.toDto(user);

    }

    @Override
    public GetUserDTO createUser(final @NotNull CreateUserDTO createUserDTO,
                                 final @NotNull BindingResult result) {
        this.validator.validate(createUserDTO, result);

        final User createdUser = this.userService.createUser(
                createUserDTO.email(),
                createUserDTO.name(),
                createUserDTO.surname(),
                createUserDTO.password()
        );

        return this.userMapper.toDto(createdUser);
    }

    @Override
    public GetUserDTO updateUser(final @NotNull UpdateUserDTO user,
                                 final @NotNull BindingResult result) {
        this.validator.validate(user, result);

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
        this.validateUserId(id);

        this.userService.deleteUser(id);
    }

    private void validateUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException(ERROR_USER_ID_MUST_BE_POSITIVE);
        }
    }
}
