package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import social.network.backend.socialnetwork.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByUser_Id(Integer userId, Pageable pageable);

    @Query("""
       SELECT p
       FROM Post p
       LEFT JOIN p.postComments c
       GROUP BY p
       ORDER BY COUNT(c) DESC
       """)
    List<Post> findTopTenMostCommentedPosts(Limit limit);

    @Query("""
       SELECT p
       FROM Post p
       LEFT JOIN p.postLikes l
       GROUP BY p
       ORDER BY COUNT(l) DESC
       """)
    List<Post> findTopTenMostLikedPosts(Limit limit);
}
