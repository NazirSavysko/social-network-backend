package social.network.backend.socialnetwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.Subscription;

public interface SubscriptionService {
    @Transactional(readOnly = true)
    Subscription getSubscriptionById(Integer subscriptionId);

    @Transactional(rollbackFor = Exception.class)
    Subscription createSubscription(final Integer subscriber, final Integer targetId);

    @Transactional(rollbackFor = Exception.class)
    Subscription updateSubscription(final Integer id, final Integer subscriberId, final Integer targetId);

    @Transactional(rollbackFor = Exception.class)
    void deleteSubscription(Integer subscriptionId);

    @Transactional(readOnly = true)
    int countSubscriptionsByUserId(Integer id);

    @Transactional(readOnly = true)
    int countSubscribersByUserId(Integer id);

    @Transactional(readOnly = true)
    Page<Subscription> getSubscriptionsByUserId(Integer id, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Subscription> getSubscribersByUserId(final Integer id, final Pageable pageable);
}
