package social.network.backend.socialnetwork.service;

import org.jetbrains.annotations.NotNull;
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
}
