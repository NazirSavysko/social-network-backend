package social.network.backend.socialnetwork.controller.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.post.CreatePostDTO;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.facade.PostFacade;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostsController {

    private final PostFacade postFacade;

    @PostMapping("/create")
    @PreAuthorize(value = "hasRole('ADMIN') or @userServiceImpl.getUserById(#createPostDTO.userId()).email == principal.username")
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

    @GetMapping("/user/{userId:\\d+}")
    public ResponseEntity<?> getAllPostsByUserId(final @PageableDefault(sort = {"postDate"},direction = DESC) Pageable pageable,
                                                 final @PathVariable("userId") Integer userId) {
        final Page<GetPostDTO> postList = this.postFacade.getAllPostsByUserId(userId,pageable);

        return ok(postList);
    }
}
