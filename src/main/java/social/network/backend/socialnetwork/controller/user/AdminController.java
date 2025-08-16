package social.network.backend.socialnetwork.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.network.backend.socialnetwork.dto.post.GetPostCount;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.facade.AdminFacade;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public final class AdminController {


    private final AdminFacade adminFacade;

    @GetMapping("/post/count")
    public ResponseEntity<?> getPostCount() {
        final GetPostCount getPostCount = this.adminFacade.postCount();

        return ok(getPostCount);
    }

    @GetMapping("/users/most-active")
    public ResponseEntity<?> getTenTheMostActiveUsers() {
        final List<UserShortDTO> tenTheMostActiveUsers = this.adminFacade.getTenTheMostActiveUsers();

        return ok(tenTheMostActiveUsers);
    }

    @GetMapping("/posts/top10/likes")
    public ResponseEntity<?> getTopTenPostsByLikes() {
        final List<GetPostDTO> posts = this.adminFacade.getTopTenPostByLikes();

        return ok(posts);
    }

    @GetMapping("/posts/top10/comments")
    public ResponseEntity<?> getTopTenPostsByComments() {
        final List<GetPostDTO> posts = this.adminFacade.getTopTenPostByComments();

        return ok(posts);
    }


}
