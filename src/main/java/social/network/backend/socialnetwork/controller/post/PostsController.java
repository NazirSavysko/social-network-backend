package social.network.backend.socialnetwork.controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.post.CreatePostDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.facade.PostFacade;

import java.util.Map;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/v1/posts")
public final class PostsController {

    private final PostFacade postFacade;

    @Autowired
    public PostsController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(final @RequestBody CreatePostDTO createPostDTO,
                                        final UriComponentsBuilder uriComponentsBuilder,
                                        final BindingResult result) {
        final GetPostDTO createdPost = this.postFacade.createPost(createPostDTO, result);

        return created(
                uriComponentsBuilder
                        .replacePath("/api/v1/posts/{postId}")
                        .build(Map.of("postId", createdPost.id()))
        ).body(createdPost);
    }
}
