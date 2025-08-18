package social.network.backend.socialnetwork.facade.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.admin.TimeRangeDTO;
import social.network.backend.socialnetwork.dto.post.GetPostCount;
import social.network.backend.socialnetwork.dto.post.GetPostDTO;
import social.network.backend.socialnetwork.dto.user.UserShortDTO;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.facade.AdminFacade;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.service.AdminService;

import java.util.List;

import static social.network.backend.socialnetwork.utils.MapperUtils.mapCollection;

@Component
@AllArgsConstructor
public final class AdminFacadeImpl implements AdminFacade {

    private final Mapper<User, UserShortDTO> userShortMapper;
    private final Mapper<Post, GetPostDTO> postMapper;
    private final AdminService adminService;


    @Contract("_ -> new")
    @Override
    public @NotNull GetPostCount postCount(final @NotNull TimeRangeDTO timeRangeDTO) {
        final int postCount = this.adminService.postCount(timeRangeDTO.start(),timeRangeDTO.end());

        return new GetPostCount(postCount);
    }

    @Override
    public List<UserShortDTO> getTenTheMostActiveUsers(final @NotNull TimeRangeDTO timeRangeDTO) {
        final List<User> mostActiveUsers = this.adminService.getTenTheMostActiveUsers(timeRangeDTO.start(),timeRangeDTO.end());

        return mapCollection(mostActiveUsers, this.userShortMapper::toDto);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable List<GetPostDTO> getTopTenPostByLikes(final @NotNull TimeRangeDTO timeRangeDTO) {
        final List<Post> postList = this.adminService.getTenTheMostLikedPosts(timeRangeDTO.start(),timeRangeDTO.end());

        return mapCollection(postList, this.postMapper::toDto);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable List<GetPostDTO> getTopTenPostByComments(final @NotNull TimeRangeDTO timeRangeDTO) {
        final List<Post> postList = this.adminService.getTenthMostCommentedPosts(timeRangeDTO.start(),timeRangeDTO.end());


        return mapCollection(postList, this.postMapper::toDto);
    }
}
