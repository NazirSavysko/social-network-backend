package social.network.backend.socialnetwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    @Autowired
    public SubscriptionServiceImpl(final SubscriptionRepository subscriptionRepository, final UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    @Override
    public Subscription getSubscriptionById(final Integer subscriptionId) {
        return this.getSubscriptionByIdOrThrow(subscriptionId);
    }

    @Override
    public Subscription createSubscription(final Integer subscriberId, final Integer targetId) {
        final User subscriber = this.userService.getUserByIdOrTrow(subscriberId, "Subscriber user not found");
        final User target = this.userService.getUserByIdOrTrow(targetId, "Target user not found");

        this.existsBySubscriberAndTargetIdOrTrow(subscriberId, targetId);
        Subscription subscription = Subscription.builder()
                .subscriber(subscriber)
                .target(target)
                .subscribedAt(now())
                .build();

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(final Integer id, final Integer subscriberId, final Integer targetId) {
        final Subscription subscription = this.getSubscriptionByIdOrThrow(id);

        final User subscriber = this.userService.getUserByIdOrTrow(subscriberId, "Subscriber user not found");
        final User target = this.userService.getUserByIdOrTrow(targetId, "Target user not found");

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
                .orElseThrow(() -> new NoSuchElementException("Subscription not found"));
    }

    private void existsBySubscriberAndTargetIdOrTrow(final Integer subscriberId, final Integer targetId) {
        if (this.subscriptionRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetId)) {
            throw new IllegalArgumentException("Subscription already exists between these users");
        }
    }
}
