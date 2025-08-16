package social.network.backend.socialnetwork.facade.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.subscription.CreateSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.UpdateSubscriptionDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.Subscription;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.SubscriptionFacade;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.service.SubscriptionService;
import social.network.backend.socialnetwork.validation.DtoValidator;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
@AllArgsConstructor
public final class SubscriptionFacadeImpl implements SubscriptionFacade {

    private final SubscriptionService subscriptionService;
    private final DtoValidator validator;
    private final Mapper<User, UserShortDTO> userMapper;
    private final Mapper<Subscription, GetSubscriptionDTO> subscriptionMapper;


    @Override
    public GetSubscriptionDTO getSubscriptionById(final Integer subscriptionId) {
        final Subscription subscription = this.subscriptionService.getSubscriptionById(subscriptionId);

        return mapDto(subscription, this.subscriptionMapper::toDto);
    }

    @Override
    public GetSubscriptionDTO createSubscription(final CreateSubscriptionDTO createSubscriptionDTO, final BindingResult result) {
        this.validator.validate(createSubscriptionDTO, result);

        final Subscription subscription = this.subscriptionService.createSubscription(
                createSubscriptionDTO.subscriberId(),
                createSubscriptionDTO.targetId()
        );

        return mapDto(subscription, this.subscriptionMapper::toDto);
    }

    @Override
    public void deleteSubscription(final Integer subscriptionId) {
        this.subscriptionService.deleteSubscription(subscriptionId);
    }

    @Override
    public GetSubscriptionDTO updateSubscription(final UpdateSubscriptionDTO updateSubscriptionDTO, final BindingResult result) {
        this.validator.validate(updateSubscriptionDTO, result);

        final Subscription updatedSubscription = this.subscriptionService.updateSubscription(
                updateSubscriptionDTO.id(),
                updateSubscriptionDTO.subscriberId(),
                updateSubscriptionDTO.targetId()
        );

        return mapDto(updatedSubscription, this.subscriptionMapper::toDto);
    }

    @Override
    public int countSubscriptionsByUserId(final Integer id) {
        return this.subscriptionService.countSubscriptionsByUserId(id);
    }

    @Override
    public int countSubscribersByUserId(final Integer id) {
        return this.subscriptionService.countSubscribersByUserId(id);
    }

    @Override
    public @NotNull Page<UserShortDTO> getSubscriptionsByUserId(final Integer id, final Pageable pageable) {
        final Page<Subscription> subscriptions = this.subscriptionService.getSubscriptionsByUserId(id, pageable);

        return subscriptions.map(subscription -> mapDto(subscription.getTarget(), this.userMapper::toDto));
    }

    @Override
    public @NotNull Page<UserShortDTO> getSubscribersByUserId(final Integer id, final Pageable pageable) {
        final Page<Subscription> subscriptions = this.subscriptionService.getSubscribersByUserId(id, pageable);

        return subscriptions.map(subscription -> mapDto(subscription.getSubscriber(), this.userMapper::toDto));
    }
}
