package social.network.backend.socialnetwork.facade.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.UserFacade;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.service.UserService;
import social.network.backend.socialnetwork.validation.DtoValidator;

@Component
@AllArgsConstructor
public final class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final Mapper<User, GetUserDTO> userMapper;
    private final DtoValidator validator;

    @Override
    public GetUserDTO getUserById(final Integer userId) {
        final User user = this.userService.getUserById(userId);

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
        this.userService.deleteUser(id);
    }

}
