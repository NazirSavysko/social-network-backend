package social.network.backend.socialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.PostRepository;
import social.network.backend.socialnetwork.repository.UserRepository;

import java.util.List;

import static org.springframework.data.domain.Limit.of;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final int LIMIT = 10;

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public int postCount() {
        return (int) postRepository.count();
    }

    @Override
    public List<User> getTenTheMostActiveUsers() {
        return userRepository.findTopTenMostActiveUsers(of(LIMIT));
    }

    @Override
    public List<Post> getTenthMostCommentedPosts() {
        return postRepository.findTopTenMostCommentedPosts(of(LIMIT));
    }

    @Override
    public List<Post> getTenTheMostLikedPosts() {
        return postRepository.findTopTenMostLikedPosts(of(LIMIT));
    }
}
