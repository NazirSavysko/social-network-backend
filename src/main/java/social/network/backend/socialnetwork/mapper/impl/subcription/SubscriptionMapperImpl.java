package social.network.backend.socialnetwork.mapper.impl.subcription;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.Subscription;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
public final class SubscriptionMapperImpl implements Mapper<Subscription, GetSubscriptionDTO> {

    private final Mapper<User, UserShortDTO> userMapper;

    @Autowired
    public SubscriptionMapperImpl(final Mapper<User, UserShortDTO> userMapper) {
        this.userMapper = userMapper;
    }

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
