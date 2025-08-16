package social.network.backend.socialnetwork.service;

import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;

import java.util.List;

public interface AdminService {

    int postCount();

    List<User> getTenTheMostActiveUsers();

    List<Post> getTenthMostCommentedPosts();

    List<Post> getTenTheMostLikedPosts();
}
