package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import social.network.backend.socialnetwork.entity.Image;
import social.network.backend.socialnetwork.entity.Post;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.entity.enums.Role;
import social.network.backend.socialnetwork.repository.PostRepository;
import social.network.backend.socialnetwork.service.UserService;
import social.network.backend.socialnetwork.utils.FileUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static social.network.backend.socialnetwork.validation.ErrorMessages.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @Test
    void getPostById_givenExistingId_shouldReturnPost() {
        // Given
        final Integer postId = 1;
        final Post expectedPost = Post.builder()
                .id(postId)
                .postText("Test post")
                .postDate(Instant.now())
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        // When
        final Post result = postServiceImpl.getPostById(postId);

        // Then
        assertNotNull(result);
        assertEquals(expectedPost, result);
        verify(postRepository).findById(postId);
    }

    @Test
    void getPostById_givenNonExistingId_shouldThrowException() {
        // Given
        final Integer postId = 999;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> postServiceImpl.getPostById(postId)
        );

        // Then
        assertEquals(ERROR_POST_NOT_FOUND, exception.getMessage());
        verify(postRepository).findById(postId);
    }

    @Test
    void deletePostById_shouldCallRepositoryDelete() {
        // Given
        final int postId = 1;

        // When
        postServiceImpl.deletePostById(postId);

        // Then
        verify(postRepository).deleteById(postId);
    }

    @Test
    void createPost_givenValidData_shouldCreatePost() {
        // Given
        final Integer userId = 1;
        final String imageBase64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD";
        final String postText = "Test post text";

        final User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .password("password")
                .role(Role.ROLE_USER)
                .posts(new ArrayList<>())
                .build();

        final Image image = Image.builder()
                .id(1)
                .filePath("/path/to/image.jpg")
                .build();

        final Post expectedPost = Post.builder()
                .id(1)
                .user(user)
                .postText(postText)
                .image(image)
                .postDate(Instant.now())
                .postLikes(List.of())
                .postComments(List.of())
                .build();

        when(userService.getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND)).thenReturn(user);

        try (MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class)) {
            fileUtilsMock.when(() -> FileUtils.writeToFile(user.getEmail(), "/9j/4AAQSkZJRgABAQAAAQABAAD"))
                    .thenReturn("/path/to/image.jpg");

            when(postRepository.save(any(Post.class))).thenReturn(expectedPost);

            // When
            final Post result = postServiceImpl.createPost(userId, imageBase64, postText);

            // Then
            assertNotNull(result);
            assertEquals(expectedPost, result);
            verify(userService).getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND);
            verify(postRepository).save(any(Post.class));
            fileUtilsMock.verify(() -> FileUtils.writeToFile(user.getEmail(), "/9j/4AAQSkZJRgABAQAAAQABAAD"));
        }
    }

    @Test
    void createPost_givenInvalidImageFormat_shouldThrowException() {
        // Given
        final Integer userId = 1;
        final String invalidImageBase64 = "data:text/plain;base64,dGVzdA==";
        final String postText = "Test post text";

        final User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .posts(List.of())
                .build();

        when(userService.getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND)).thenReturn(user);

        // When
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> postServiceImpl.createPost(userId, invalidImageBase64, postText)
        );

        // Then
        assertEquals(ERROR_POST_IMAGE_INVALID_FORMAT, exception.getMessage());
        verify(userService).getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void updatePost_givenExistingPost_shouldUpdateSuccessfully() {
        // Given
        final Integer postId = 1;
        final String newImageBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==";
        final String newText = "Updated post text";

        final User user = User.builder()
                .id(1)
                .email("test@example.com")
                .build();

        final Image oldImage = Image.builder()
                .id(1)
                .filePath("/old/path/image.jpg")
                .build();

        final Post existingPost = Post.builder()
                .id(postId)
                .user(user)
                .postText("Old text")
                .image(oldImage)
                .build();

        final Image newImage = Image.builder()
                .id(2)
                .filePath("/new/path/image.png")
                .build();

        final Post updatedPost = Post.builder()
                .id(postId)
                .user(user)
                .postText(newText)
                .image(newImage)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        try (MockedStatic<FileUtils> fileUtilsMock = mockStatic(FileUtils.class)) {
            fileUtilsMock.when(() -> FileUtils.writeToFile(user.getEmail(), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg=="))
                    .thenReturn("/new/path/image.png");

            when(postRepository.save(existingPost)).thenReturn(updatedPost);

            // When
            final Post result = postServiceImpl.updatePost(postId, newImageBase64, newText);

            // Then
            assertNotNull(result);
            assertEquals(updatedPost, result);
            verify(postRepository).findById(postId);
            verify(postRepository).save(existingPost);
            fileUtilsMock.verify(() -> FileUtils.deleteFile("/old/path/image.jpg"));
            fileUtilsMock.verify(() -> FileUtils.writeToFile(user.getEmail(), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg=="));
        }
    }

    @Test
    void updatePost_givenNonExistingPost_shouldThrowException() {
        // Given
        final Integer postId = 999;
        final String imageBase64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD";
        final String text = "Test text";

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> postServiceImpl.updatePost(postId, imageBase64, text)
        );

        // Then
        assertEquals(ERROR_POST_NOT_FOUND, exception.getMessage());
        verify(postRepository).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getAllPostsByUserId_shouldReturnPageOfPosts() {
        // Given
        final Integer userId = 1;
        final Pageable pageable = mock(Pageable.class);

        final Post post1 = Post.builder()
                .id(1)
                .postText("Post 1")
                .build();

        final Post post2 = Post.builder()
                .id(2)
                .postText("Post 2")
                .build();

        final List<Post> posts = List.of(post1, post2);
        final Page<Post> expectedPage = new PageImpl<>(posts, pageable, posts.size());

        when(postRepository.findAllByUser_Id(userId, pageable)).thenReturn(expectedPage);

        // When
        final Page<Post> result = postServiceImpl.getAllPostsByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        assertEquals(2, result.getContent().size());
        assertEquals(post1, result.getContent().get(0));
        assertEquals(post2, result.getContent().get(1));
        verify(postRepository).findAllByUser_Id(userId, pageable);
    }
}
