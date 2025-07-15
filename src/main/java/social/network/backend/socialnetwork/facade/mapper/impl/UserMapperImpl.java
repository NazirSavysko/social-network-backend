package social.network.backend.socialnetwork.facade.mapper.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.user.GetUserDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.mapper.Mapper;


@Component("userMapper")
public final class UserMapperImpl implements Mapper<User, GetUserDTO> {

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
