package social.network.backend.socialnetwork.controller.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdatePostPayload;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.post.UpdatePostDTO;
import social.network.backend.socialnetwork.facade.PostFacade;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/posts/{postId:\\d+}")
class PostController {

    private final PostFacade postFacade;

    @Autowired
    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @ModelAttribute("post")
    public @NotNull GetPostDTO getMessageId(@PathVariable("postId") Integer postId) {
        return  this.postFacade.getPostById(postId);
    }


    @GetMapping
    public ResponseEntity<?> getPost(final @ModelAttribute(value = "post") GetPostDTO post) {
        return ok(post);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(final @ModelAttribute(value = "post", binding = false) GetPostDTO post,
                                        final @RequestBody UpdatePostPayload postPayload,
                                        final BindingResult result){

        final UpdatePostDTO updatePostDTO = new UpdatePostDTO(
                postPayload.text(),
                postPayload.imageInFormatBase64(),
                post.id()
        );
        final GetPostDTO updatedPost = this.postFacade.updatePost(updatePostDTO,result);

        return ok(updatedPost);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(final @ModelAttribute(value = "post",binding = false) GetPostDTO post) {
        this.postFacade.deletePost(post.id());

        return status(OK)
                .contentType(APPLICATION_JSON)
                .body(Map.of("message", "Post with id >" + post.id() + "< has been deleted"));
    }
}
