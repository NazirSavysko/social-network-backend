package social.network.backend.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Subscription;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.SubscriptionRepository;
import social.network.backend.socialnetwork.service.SubscriptionService;
import social.network.backend.socialnetwork.service.UserService;

import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static social.network.backend.socialnetwork.validation.ErrorMessages.*;


@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;


    @Override
    public Subscription getSubscriptionById(final Integer subscriptionId) {
        return this.getSubscriptionByIdOrThrow(subscriptionId);
    }

    @Override
    public Subscription createSubscription(final Integer subscriberId, final Integer targetId) {
        final User subscriber = this.userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        final User target = this.userService.getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);

        this.existsBySubscriberAndTargetIdOrTrow(subscriberId, targetId);
        final Subscription subscription = Subscription.builder()
                .subscriber(subscriber)
                .target(target)
                .subscribedAt(now())
                .build();

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(final Integer id, final Integer subscriberId, final Integer targetId) {
        final Subscription subscription = this.getSubscriptionByIdOrThrow(id);

        final User subscriber = this.userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        final User target = this.userService.getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);

        subscription.setSubscriber(subscriber);
        subscription.setTarget(target);

        return this.subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(final Integer subscriptionId) {
        this.subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public int countSubscriptionsByUserId(final Integer id) {
        return (int) this.subscriptionRepository.countByTarget_Id(id);
    }

    @Override
    public int countSubscribersByUserId(final Integer id) {
        return (int) this.subscriptionRepository.countBySubscriber_Id(id);
    }

    @Override
    public Page<Subscription> getSubscriptionsByUserId(final Integer id, final Pageable pageable) {
        this.userService.isUserExistByIdOrThrow(id);

        return this.subscriptionRepository.findAllBySubscriber_id(id, pageable);
    }

    @Override
    public Page<Subscription> getSubscribersByUserId(final Integer id, final Pageable pageable) {
        this.userService.isUserExistByIdOrThrow(id);

        return this.subscriptionRepository.findAllByTarget_id(id, pageable);
    }

    private Subscription getSubscriptionByIdOrThrow(final Integer subscriptionId) {
        return this.subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchElementException(ERROR_SUBSCRIPTION_NOT_FOUND));
    }

    private void existsBySubscriberAndTargetIdOrTrow(final Integer subscriberId, final Integer targetId) {
        if (this.subscriptionRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetId)) {
            throw new IllegalArgumentException(ERROR_SUBSCRIPTION_ALREADY_EXISTS);
        }
    }
}
