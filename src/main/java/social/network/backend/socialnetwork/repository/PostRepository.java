package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import social.network.backend.socialnetwork.entity.Post;

import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByUser_Id(Integer userId, Pageable pageable);

    @Query("""
            SELECT COUNT(p)
            FROM Post p
            WHERE p.postDate >= :start AND p.postDate <= :end
            """)
    int countPostsByPeriod(Instant start, Instant end);

    @Query("""
                SELECT avg(cnt)
                FROM (
                    SELECT count(p) as cnt
                    FROM Post p
                    WHERE p.postDate BETWEEN :start AND :end
                    GROUP BY p.postDate
                )
            """)
    int calculateAveragePostsPerDay(Instant start, Instant end);

    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN p.postComments c
            WHERE p.postDate BETWEEN :start AND :end
            GROUP BY p
            ORDER BY COUNT(c) DESC
            """)
    List<Post> findTopTenMostCommentedPostsByPeriod(Limit of, Instant start, Instant end);

    @Query("""
                SELECT p
                FROM Post p
                LEFT JOIN p.postLikes l
                WHERE p.postDate BETWEEN :start AND :end
                GROUP BY p
                ORDER BY COUNT(l) DESC, p.id DESC
            """)
    List<Post> findTopTenMostLikedPostsByPeriod(Limit of, Instant start, Instant end);
}
