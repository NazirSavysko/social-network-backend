package social.network.backend.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.PostComment;

interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
}
