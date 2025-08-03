package social.network.backend.socialnetwork.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.Image;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.repository.PostRepository;
import social.network.backend.socialnetwork.service.PostService;
import social.network.backend.socialnetwork.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static java.util.Base64.getDecoder;
import static social.network.backend.socialnetwork.utils.FileUtils.deleteFile;
import static social.network.backend.socialnetwork.utils.FileUtils.writeToFile;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(final PostRepository postRepository,final UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Post getPostById(final int id) {
        return this.getPostByIdOrThrow(id);
    }

    @Override
    public void deletePostById(final int id) {
       this.postRepository.deleteById(id);
    }

    @Override
    public @NotNull Post createPost(final Integer userId, final String imageInFormatBase64, final String postText) {
        final User user = this.userService.getUserByIdOrTrow(userId, "User not found");

        final Image image = this.createImage(imageInFormatBase64, user.getEmail());

        final Post post = Post.builder()
                .user(user)
                .postText(postText)
                .image(image)
                .postDate(now())
                .postLikes(List.of())
                .postComments(List.of())
                .build();

        user.getPosts().add(post);
        return this.postRepository.save(post);
    }

    @Override
    public Post updatePost(final Integer id, final String imageInFormatBase64, final String text) {
        final Post post = this.getPostByIdOrThrow(id);

        final Image image = this.createImage(imageInFormatBase64, post.getUser().getEmail());

        final String filePath = post.getImage().getFilePath();
        deleteFile(filePath);

        post.setImage(image);
        post.setPostText(text);

        return this.postRepository.save(post);
    }

    @Override
    public Page<Post> getAllPostsByUserId(final Integer userId, final Pageable pageable) {
        return this.postRepository.findAllByUser_Id(userId, pageable);
    }

    private Image createImage(final @NotNull String imageInFormatBase64, final String directory) {
        String base64 = imageInFormatBase64;
        if (base64.startsWith("data:")) {
            base64 = base64.substring(base64.indexOf(',') + 1);
        }
        final byte[] data = getDecoder().decode(base64);

        final String filePath = writeToFile(directory, data);

        return Image.builder()
                .filePath(filePath)
                .build();
    }

    private Post getPostByIdOrThrow(final Integer postId) {
        return this.postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
    }
}
