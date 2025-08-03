package social.network.backend.socialnetwork.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.Post;



public interface PostService {

    @Transactional(readOnly = true)
    Post getPostById(int id);

    @Transactional(rollbackFor = Exception.class)
    void deletePostById(int id);

    @Transactional(rollbackFor = Exception.class)
    @NotNull Post createPost(Integer userId, String imageInFormatBase64, String postText);

    @Transactional(rollbackFor = Exception.class)
    Post updatePost(Integer id, String imageInFormatBase64 , String text);

    @Transactional(readOnly = true)
    Page<Post> getAllPostsByUserId(Integer userId, Pageable pageable);
}
