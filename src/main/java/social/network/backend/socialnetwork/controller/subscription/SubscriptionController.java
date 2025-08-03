package social.network.backend.socialnetwork.controller.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdateSubscriptionPayload;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.UpdateSubscriptionDTO;
import social.network.backend.socialnetwork.facade.SubscriptionFacade;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/subscriptions/{subscriptionId:\\d+}")
public final class SubscriptionController {


    private final SubscriptionFacade subscriptionFacade;

    @Autowired
    public SubscriptionController(final SubscriptionFacade subscriptionFacade) {
        this.subscriptionFacade = subscriptionFacade;
    }


    @ModelAttribute("subscription")
    public GetSubscriptionDTO getUserId(final @PathVariable("subscriptionId") Integer subscriptionId) {
        return this.subscriptionFacade.getSubscriptionById(subscriptionId);
    }


    @GetMapping
    public ResponseEntity<?> getSubscription(final @ModelAttribute("subscription") GetSubscriptionDTO subscription) {
        return ok(subscription);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSubscription(
            final @ModelAttribute(value = "subscription", binding = false) GetSubscriptionDTO subscription,
            final @RequestBody UpdateSubscriptionPayload updateSubscriptionPayload, final BindingResult result) {
        final UpdateSubscriptionDTO updateSubscriptionDTO = new UpdateSubscriptionDTO(
                subscription.id(),
                updateSubscriptionPayload.subscriberId(),
                updateSubscriptionPayload.targetId()
        );
        final GetSubscriptionDTO updatedSubscription = this.subscriptionFacade.updateSubscription(updateSubscriptionDTO, result);

        return ok(updatedSubscription);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSubscription(
            final @ModelAttribute(value = "subscription", binding = false) GetSubscriptionDTO subscription) {
        this.subscriptionFacade.deleteSubscription(subscription.id());

        return noContent().build();
    }
}
