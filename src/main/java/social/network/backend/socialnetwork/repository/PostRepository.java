package social.network.backend.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
