package social.network.backend.socialnetwork.controller.payload;

/**
 * Payload for updating a message.
 * This record is used to encapsulate the data required to update an existing message.
 *
 * @param content the new content of the message
 */
public record UpdateMessagePayload(
    String content
) {
}
