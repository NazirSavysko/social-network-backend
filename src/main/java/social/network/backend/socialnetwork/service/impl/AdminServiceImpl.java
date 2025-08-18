package social.network.backend.socialnetwork.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.PostRepository;
import social.network.backend.socialnetwork.repository.UserRepository;
import social.network.backend.socialnetwork.service.AdminService;

import java.time.Instant;
import java.util.List;

import static org.springframework.data.domain.Limit.of;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final int LIMIT = 10;

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public int postCount(final Instant start, final Instant end) {
        return this.postRepository.countPostsByPeriod(start, end);
    }

    @Override
    public List<User> getTenTheMostActiveUsers(final Instant start, final Instant end) {
        return this.userRepository.findTopUsersByAvgPostsPerActiveDay(of(LIMIT), start, end);
    }

    @Override
    public List<Post> getTenthMostCommentedPosts(final Instant start, final Instant end) {
        return this.postRepository.findTopTenMostCommentedPostsByPeriod(of(LIMIT),start,end);
    }

    @Override
    public List<Post> getTenTheMostLikedPosts(final Instant start, final Instant end) {
        return this.postRepository.findTopTenMostLikedPostsByPeriod(of(LIMIT), start, end);
    }

    @Override
    public double getAveragePostCountByDay(final Instant start, final Instant end) {
       return this .postRepository.calculateAveragePostsPerDay(start, end);

    }
}
