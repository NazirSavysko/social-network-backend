package social.network.backend.socialnetwork.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.subscription.CreateSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.UpdateSubscriptionDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;

public interface SubscriptionFacade {

    GetSubscriptionDTO getSubscriptionById(Integer subscriptionId);

    GetSubscriptionDTO createSubscription(CreateSubscriptionDTO createSubscriptionDTO, BindingResult result);

    void deleteSubscription(Integer subscriptionId);

    GetSubscriptionDTO updateSubscription(UpdateSubscriptionDTO updateSubscriptionPayload, BindingResult result);

    int countSubscriptionsByUserId(Integer id);

    int countSubscribersByUserId(Integer id);

    Page<UserShortDTO> getSubscriptionsByUserId(Integer id, Pageable pageable);

    Page<UserShortDTO> getSubscribersByUserId(Integer id, Pageable pageable);
}
