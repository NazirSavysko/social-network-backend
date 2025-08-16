package social.network.backend.socialnetwork.facade;

import social.network.backend.socialnetwork.dto.post.GetPostCount;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;

import java.util.List;

public interface AdminFacade {

    GetPostCount postCount();

    List<UserShortDTO> getTenTheMostActiveUsers();

    List<GetPostDTO> getTopTenPostByLikes();

    List<GetPostDTO> getTopTenPostByComments();
}
