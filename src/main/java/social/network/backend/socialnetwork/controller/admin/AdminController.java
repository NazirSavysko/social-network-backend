package social.network.backend.socialnetwork.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.network.backend.socialnetwork.dto.admin.TimeRangeDTO;
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

    @PostMapping("/post/count")
    public ResponseEntity<?> getPostCount(final @RequestBody TimeRangeDTO timeRangeDTO) {
        final GetPostCount getPostCount = this.adminFacade.postCount(timeRangeDTO);

        return ok(getPostCount);
    }

    @PostMapping("/users/most-active")
    public ResponseEntity<?> getTenTheMostActiveUsers(final @RequestBody TimeRangeDTO timeRangeDTO) {
        final List<UserShortDTO> tenTheMostActiveUsers = this.adminFacade.getTenTheMostActiveUsers(timeRangeDTO);

        return ok(tenTheMostActiveUsers);
    }

    @PostMapping("/posts/top10/likes")
    public ResponseEntity<?> getTopTenPostsByLikes(final @RequestBody TimeRangeDTO timeRangeDTO) {
        final List<GetPostDTO> posts = this.adminFacade.getTopTenPostByLikes(timeRangeDTO);

        return ok(posts);
    }

    @PostMapping("/posts/top10/comments")
    public ResponseEntity<?> getTopTenPostsByComments(final @RequestBody TimeRangeDTO timeRangeDTO) {
        final List<GetPostDTO> posts = this.adminFacade.getTopTenPostByComments(timeRangeDTO);

        return ok(posts);
    }


}
