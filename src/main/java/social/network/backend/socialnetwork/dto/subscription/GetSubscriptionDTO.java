package social.network.backend.socialnetwork.dto.subscription;

import social.network.backend.socialnetwork.dto.user.UserShortDTO;

public record GetSubscriptionDTO(
        Integer id,
        UserShortDTO subscriber,
        UserShortDTO subscribedTo

) {
}
