package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import social.network.backend.socialnetwork.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("""
               SELECT u
               FROM User u
               LEFT JOIN u.posts p ON p.postDate BETWEEN :start AND :end
               GROUP BY u, FUNCTION('date_trunc', 'day', p.postDate)
               ORDER BY COUNT(p) DESC
            """)
    List<User> findTopUsersByAvgPostsPerActiveDay(Limit limit, final Instant start, final Instant end);
}
