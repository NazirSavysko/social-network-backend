package social.network.backend.socialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByUser_Id(Integer userId, Pageable pageable);
}
