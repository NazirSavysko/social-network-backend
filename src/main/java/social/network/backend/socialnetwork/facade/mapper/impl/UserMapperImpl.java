package social.network.backend.socialnetwork.facade.mapper.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.user.CreateUserDTO;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.dto.user.UpdateUserDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.mapper.Mapper;

import static social.network.backend.socialnetwork.entity.enums.Role.ROLE_USER;

@Component("userMapper")
public final class UserMapperImpl implements Mapper<User, UpdateUserDTO, CreateUserDTO, GetUserDTO> {

    @Override
    public User toEntityFromCreate(final @NotNull CreateUserDTO dtoForCreate) {

        return User.builder()
                .email(dtoForCreate.email())
                .name(dtoForCreate.name())
                .surname(dtoForCreate.surname())
                .password(dtoForCreate.password())
                .role(ROLE_USER)
                .build();
    }

    @Override
    public User toEntityFromUpdate(final @NotNull UpdateUserDTO dtoForUpdate) {

        return User.builder()
                .id(dtoForUpdate.id())
                .email(dtoForUpdate.email())
                .name(dtoForUpdate.name())
                .surname(dtoForUpdate.surname())
                .password(dtoForUpdate.password())
                .build();
    }


    @Contract("_ -> new")
    @Override
    public @NotNull GetUserDTO toDto(final @NotNull User entity) {

        return new GetUserDTO(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail()
        );
    }
}
