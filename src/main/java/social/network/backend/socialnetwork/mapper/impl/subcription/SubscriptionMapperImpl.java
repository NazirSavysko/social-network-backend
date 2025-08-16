package social.network.backend.socialnetwork.mapper.impl.subcription;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.Subscription;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
@AllArgsConstructor
public final class SubscriptionMapperImpl implements Mapper<Subscription, GetSubscriptionDTO> {

    private final Mapper<User, UserShortDTO> userMapper;

    @Contract("_ -> new")
    @Override
    public @NotNull GetSubscriptionDTO toDto(final @NotNull Subscription entity) {
        return new GetSubscriptionDTO(
                entity.getId(),
                mapDto(entity.getSubscriber(), this.userMapper::toDto),
                mapDto(entity.getTarget(), this.userMapper::toDto)
        );
    }
}
