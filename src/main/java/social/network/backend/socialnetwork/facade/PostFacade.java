package social.network.backend.socialnetwork.facade;

import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.post.CreatePostDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.post.UpdatePostDTO;

public interface PostFacade {
    GetPostDTO createPost(CreatePostDTO createPostDTO, final BindingResult result);

    GetPostDTO getPostById(Integer postId);

    void deletePost(Integer id);

    GetPostDTO updatePost(UpdatePostDTO post, final BindingResult result);
}
