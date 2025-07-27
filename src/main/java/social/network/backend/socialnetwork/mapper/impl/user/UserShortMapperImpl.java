package social.network.backend.socialnetwork.mapper.impl.user;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

@Component
public final class UserShortMapperImpl implements Mapper<User, UserShortDTO> {

    @Contract("_ -> new")
    @Override
    public @NotNull UserShortDTO toDto(@NotNull User entity) {
        return new UserShortDTO(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail()
        );
    }
}
