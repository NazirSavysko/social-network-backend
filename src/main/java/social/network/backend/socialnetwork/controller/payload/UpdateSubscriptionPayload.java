package social.network.backend.socialnetwork.controller.payload;

public record UpdateSubscriptionPayload(
Integer subscriberId,
Integer targetId
) {
}
