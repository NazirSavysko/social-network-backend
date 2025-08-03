package social.network.backend.socialnetwork.controller.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.subscription.CreateSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.facade.SubscriptionFacade;

import java.util.Map;

import static java.util.Map.of;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/subscriptions")
public final class SubscriptionsController {

    private final SubscriptionFacade subscriptionFacade;

    @Autowired
    public SubscriptionsController(final SubscriptionFacade subscriptionFacade) {
        this.subscriptionFacade = subscriptionFacade;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createSubscription(final @RequestBody CreateSubscriptionDTO createSubscriptionDTO,
                                                final UriComponentsBuilder uriComponentsBuilder,
                                                final BindingResult result) {
        final GetSubscriptionDTO createdSubscription = this.subscriptionFacade.createSubscription(createSubscriptionDTO, result);

        return created(
                uriComponentsBuilder
                        .replacePath("/api/v1/subscriptions{subscriptionId}")
                        .build(Map.of("subscriptionId", createdSubscription.id()))
        ).body(createdSubscription);
    }

    @GetMapping("/user/{userId:\\d+}/subscriptions/list")
    public ResponseEntity<?> getSubscriptionsListByUserId(
            @PathVariable("userId") Integer userId,
            final @PageableDefault(sort = {"subscribedAt"}) Pageable pageable) {
        final Page<UserShortDTO> subscriptionsList = this.subscriptionFacade
                .getSubscriptionsByUserId(userId, pageable);

        return ok(subscriptionsList);
    }

    @GetMapping("/user/{userId:\\d+}/subscribers/list")
    public ResponseEntity<?> getSubscribersListByUserId(
            @PathVariable("userId") Integer userId,
            final @PageableDefault(sort = {"subscribedAt"}) Pageable pageable) {
        final Page<UserShortDTO> subscribersList = this.subscriptionFacade
                .getSubscribersByUserId(userId, pageable);

        return ok(subscribersList);
    }

    @GetMapping("/user/{userId:\\d+}/subscriptions/count")
    public ResponseEntity<?> getSubscriptionsCountByUserId(@PathVariable("userId") Integer userId) {
        final int count = this.subscriptionFacade.countSubscriptionsByUserId(userId);

        return ok(
                of("count", count)
        );
    }

    @GetMapping("/user/{userId:\\d+}/subscribers/count")
    public ResponseEntity<?> getSubscribersCountByUserId(@PathVariable("userId") Integer userId) {
        final int count = this.subscriptionFacade.countSubscribersByUserId(userId);

        return ok(
                of("count", count)
        );
    }
}
