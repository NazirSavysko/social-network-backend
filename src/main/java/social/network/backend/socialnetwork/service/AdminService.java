package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;

import java.time.Instant;
import java.util.List;

public interface AdminService {

    int postCount(final Instant start, final Instant end);

    List<User> getTenTheMostActiveUsers(final Instant start, final Instant end);

    List<Post> getTenthMostCommentedPosts(final Instant start, final Instant end);

    List<Post> getTenTheMostLikedPosts(final Instant start, final Instant end);

    double getAveragePostCountByDay(Instant start, Instant end);
}
