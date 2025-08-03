package social.network.backend.socialnetwork.validation;

public final class ErrorMessages {
    // User
   public static final String ERROR_USER_NOT_FOUND = "User not found";

   // Post
   public static final String ERROR_POST_NOT_FOUND = "Post not found";
   public static final String ERROR_POST_IMAGE_INVALID_FORMAT = "Post image is invalid format";

   //Message
   public static final String ERROR_MESSAGE_NOT_FOUND = "Message not found";
   public static final String ERROR_MESSAGE_SENDER_NOT_FOUND = "Sender user not found";
   public static final String ERROR_MESSAGE_RECIPIENT_NOT_FOUND = "Recipient user not found";

   //Subscription
    public static final String ERROR_SUBSCRIPTION_NOT_FOUND = "Subscription not found";
    public static final String ERROR_SUBSCRIBER_NOT_FOUND = "Subscriber user not found";
    public static final String ERROR_TARGET_NOT_FOUND = "Target user not found";
    public static final String ERROR_SUBSCRIPTION_ALREADY_EXISTS  = "Subscription already exists between these users";


    private ErrorMessages() {
    }
}
