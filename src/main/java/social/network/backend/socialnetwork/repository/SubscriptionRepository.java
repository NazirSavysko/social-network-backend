package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Page<Subscription> findAllByTarget_id(Integer targetId, Pageable pageable);

    Page<Subscription> findAllBySubscriber_id(Integer subscriberId, Pageable pageable);

    long countByTarget_Id(Integer targetId);

    long countBySubscriber_Id(Integer subscriberId);

    boolean existsBySubscriber_IdAndTarget_Id(Integer subscriberId, Integer targetId);
}
