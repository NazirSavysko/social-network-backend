package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Limit;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.PostRepository;
import social.network.backend.socialnetwork.repository.UserRepository;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Instant start;
    private Instant end;

    @BeforeEach
    void setUp() {
        start = Instant.parse("2024-01-01T00:00:00Z");
        end = Instant.parse("2024-12-31T23:59:59Z");
    }

    @Test
    void postCount_returnsRepositoryResult() {
        when(postRepository.countPostsByPeriod(start, end)).thenReturn(42);

        final int result = adminService.postCount(start, end);

        assertThat(result).isEqualTo(42);
        verify(postRepository).countPostsByPeriod(start, end);
    }

    @Test
    void getTenTheMostActiveUsers_passesLimit10_andReturnsData() {
        final List<User> users = List.of(new User(), new User());
        when(userRepository.findTopUsersByAvgPostsPerActiveDay(any(Limit.class), eq(start), eq(end)))
                .thenReturn(users);

        final List<User> result = adminService.getTenTheMostActiveUsers(start, end);

        assertThat(result).isSameAs(users);
        final ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
        verify(userRepository).findTopUsersByAvgPostsPerActiveDay(limitCaptor.capture(), eq(start), eq(end));
        assertThat(limitCaptor.getValue()).isEqualTo(Limit.of(10));
    }

    @Test
    void getTenthMostCommentedPosts_passesLimit10_andReturnsData() {
        final List<Post> posts = List.of(new Post(), new Post(), new Post());
        when(postRepository.findTopTenMostCommentedPostsByPeriod(any(Limit.class), eq(start), eq(end)))
                .thenReturn(posts);

        final List<Post> result = adminService.getTenthMostCommentedPosts(start, end);

        assertThat(result).isSameAs(posts);
        final ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
        verify(postRepository).findTopTenMostCommentedPostsByPeriod(limitCaptor.capture(), eq(start), eq(end));
        assertThat(limitCaptor.getValue()).isEqualTo(Limit.of(10));
    }

    @Test
    void getTenTheMostLikedPosts_passesLimit10_andReturnsData() {
        final List<Post> posts = List.of(new Post());
        when(postRepository.findTopTenMostLikedPostsByPeriod(any(Limit.class), eq(start), eq(end)))
                .thenReturn(posts);

        final List<Post> result = adminService.getTenTheMostLikedPosts(start, end);

        assertThat(result).isSameAs(posts);
        final ArgumentCaptor<Limit> limitCaptor = ArgumentCaptor.forClass(Limit.class);
        verify(postRepository).findTopTenMostLikedPostsByPeriod(limitCaptor.capture(), eq(start), eq(end));
        assertThat(limitCaptor.getValue()).isEqualTo(Limit.of(10));
    }

    @Test
    void getAveragePostCountByDay_returnsRepositoryDouble() {
        when(postRepository.calculateAveragePostsPerDay(start, end)).thenReturn(3.5D);

        final double result = adminService.getAveragePostCountByDay(start, end);

        assertThat(result).isEqualTo(3.5D);
        verify(postRepository).calculateAveragePostsPerDay(start, end);
    }
}

