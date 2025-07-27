package social.network.backend.socialnetwork.controller.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.post.UpdatePostDTO;
import social.network.backend.socialnetwork.facade.PostFacade;

import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<?> getPost(final @ModelAttribute("post") GetPostDTO post) {
        return ok(post);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(final @ModelAttribute("post") UpdatePostDTO post,
                                        final BindingResult result){

        final GetPostDTO updatedPost = this.postFacade.updatePost(post,result);

        return ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(final @ModelAttribute("post") GetPostDTO post) {
        this.postFacade.deletePost(post.id());

        return ok().build();
    }
}
