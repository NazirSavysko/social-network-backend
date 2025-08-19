package social.network.backend.socialnetwork.controller.subscription;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdateSubscriptionPayload;
import social.network.backend.socialnetwork.dto.subscription.GetSubscriptionDTO;
import social.network.backend.socialnetwork.dto.subscription.UpdateSubscriptionDTO;
import social.network.backend.socialnetwork.facade.SubscriptionFacade;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/subscriptions/{subscriptionId:\\d+}")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionFacade subscriptionFacade;

    @ModelAttribute("subscription")
    public GetSubscriptionDTO getUserId(final @PathVariable("subscriptionId") Integer subscriptionId) {
        return this.subscriptionFacade.getSubscriptionById(subscriptionId);
    }


    @GetMapping
    public ResponseEntity<?> getSubscription(final @ModelAttribute("subscription") GetSubscriptionDTO subscription) {
        return ok(subscription);
    }

    @PutMapping("/update")
    @PreAuthorize(value = "hasRole('ADMIN') or (#subscription.subscriber().email() == principal.username " +
            "and @userServiceImpl.getUserById(subscription.subscriber()).email == principal.username)")
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
    @PreAuthorize(value = "hasRole('ADMIN') or #subscription.subscriber().email() == principal.username or #subscription.subscribedTo().email() == principal.username")
    public ResponseEntity<?> deleteSubscription(
            final @ModelAttribute(value = "subscription", binding = false) GetSubscriptionDTO subscription) {
        this.subscriptionFacade.deleteSubscription(subscription.id());

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Map.of("message", "Subscription with id >" + subscription.id() + "< has been deleted"));
    }
}
