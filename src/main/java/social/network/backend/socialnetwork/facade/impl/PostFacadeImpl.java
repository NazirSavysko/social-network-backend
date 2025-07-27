package social.network.backend.socialnetwork.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import social.network.backend.socialnetwork.dto.post.CreatePostDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.post.UpdatePostDTO;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.facade.PostFacade;
import social.network.backend.socialnetwork.mapper.impl.post.PostMapperImpl;
import social.network.backend.socialnetwork.service.PostService;
import social.network.backend.socialnetwork.validation.DtoValidator;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapDto;

@Component
public class PostFacadeImpl implements PostFacade {

    private final PostMapperImpl postMapper;
    private final DtoValidator validator;
    private final PostService postService;

    @Autowired
    public PostFacadeImpl(final PostMapperImpl postMapper,
                          final DtoValidator validator,
                          final PostService postService) {
        this.postMapper = postMapper;
        this.validator = validator;
        this.postService = postService;
    }

    @Override
    public GetPostDTO createPost(final CreatePostDTO createPostDTO, final BindingResult result) {
        this.validator.validate(createPostDTO, result);

        return mapDto(this.postService.createPost(
                createPostDTO.userId(),
                createPostDTO.imageInFormatBase64(),
                createPostDTO.postText()),this.postMapper::toDto);
    }

    @Override
    public GetPostDTO getPostById(final Integer postId) {
        final Post post = this.postService.getPostById(postId);

        return mapDto(post, this.postMapper::toDto);
    }

    @Override
    public void deletePost(final Integer id) {
        postService.deletePostById(id);
    }

    @Override
    public GetPostDTO updatePost(final UpdatePostDTO post, final BindingResult result) {
        this.validator.validate(post,result);

        final Post updatedPost = this.postService.updatePost(
                post.id(),
                post.imageInFormatBase64(),
                post.text()
        );

        return mapDto(updatedPost, this.postMapper::toDto);
    }
}
