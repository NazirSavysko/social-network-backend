package social.network.backend.socialnetwork.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import social.network.backend.socialnetwork.entity.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.time.LocalDateTime.now;
import static java.util.stream.IntStream.range;
import static social.network.backend.socialnetwork.entity.enums.Role.ROLE_USER;

public final class EntityDtoFactory {

    private static final int NUMBER_OF_ENTITIES = 5;

    private EntityDtoFactory() {
    }

    public static User createUserEntity() {
        return User.builder()
                .id(1)
                .name("Test")
                .surname("User")
                .email("sddsdfsfdsadf")
                .role(ROLE_USER)
                .password("dsfsfsdfsfdsdfsdf")
                .posts(createListOfEntity(EntityDtoFactory::createPostEntity))
                .messages(range(1, 5).mapToObj(i -> createMessagesEntity()).toList())
                .build();
    }

    public static Message createMessagesEntity() {
        return Message.builder()
                .id(1)
                .sender(User.builder().id(1).build())
                .recipient(User.builder().id(2).build())
                .messageText("Test message")
                .messageDate(now())
                .build();
    }

    public static Post createPostEntity() {
        return Post.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .postComments(createListOfEntity(EntityDtoFactory::createPostCommentEntity))
                .postLikes(createListOfEntity(EntityDtoFactory::createPostLikeEntity))
                .postDate(now())
                .image(new Image(1,"sdsadada"))
                .postText("dfghjkhgdfsa")
                .build();
    }

    public static PostComment createPostCommentEntity() {
        return PostComment.builder()
                .id(1)
                .commentText("Test comment")
                .dateComment(now())
                .user(User.builder().id(1).build())
                .post(Post.builder().id(1).build())
                .build();
    }

    public static PostLike createPostLikeEntity() {
        return PostLike.builder()
                .id(1)
                .user(User.builder().id(1).build())
                .post(Post.builder().id(1).build())
                .build();
    }

    public static <T> @Unmodifiable  @NotNull List<T> createListOfEntity(@NotNull Supplier<T> create) {
        return IntStream.range(0, NUMBER_OF_ENTITIES)
                .mapToObj(i -> create.get())
                .toList();
    }
}
