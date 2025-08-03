package social.network.backend.socialnetwork.dto.subscription;

public record CreateSubscriptionDTO(
        Integer subscriberId,
        Integer targetId
) {
}
