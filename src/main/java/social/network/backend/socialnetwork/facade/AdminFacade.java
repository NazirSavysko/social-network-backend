package social.network.backend.socialnetwork.facade;

import social.network.backend.socialnetwork.dto.admin.TimeRangeDTO;
import social.network.backend.socialnetwork.dto.post.GetPostCount;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;

import java.util.List;

public interface AdminFacade {

    GetPostCount postCount(final TimeRangeDTO timeRangeDTO);

    List<UserShortDTO> getTenTheMostActiveUsers(final TimeRangeDTO timeRangeDTO);

    List<GetPostDTO> getTopTenPostByLikes(final TimeRangeDTO timeRangeDTO);

    List<GetPostDTO> getTopTenPostByComments(final TimeRangeDTO timeRangeDTO);
}
