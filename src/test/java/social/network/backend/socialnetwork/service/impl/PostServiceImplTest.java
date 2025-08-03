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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.time.LocalDateTime.now;
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
        final int postId = 1;
        final User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final Image image = Image.builder()
                .id(1)
                .filePath("/path/to/image.jpg")
                .build();

        final Post expectedPost = Post.builder()
                .id(postId)
                .postText("Test post content")
                .postDate(now())
                .user(user)
                .image(image)
                .postLikes(List.of())
                .postComments(List.of())
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
        final int postId = 999;
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
    void createPost_givenValidDataWithImage_shouldCreatePost() {
        // Given
        final Integer userId = 1;
        final String imageBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==";
        final String postText = "Test post content";
        final String expectedFilePath = "/path/to/saved/image.png";

        final User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .posts(new ArrayList<>())
                .build();

        final Post expectedPost = Post.builder()
                .id(1)
                .postText(postText)
                .postDate(now())
                .user(user)
                .image(Image.builder().filePath(expectedFilePath).build())
                .postLikes(new ArrayList<>())
                .postComments(new ArrayList<>())
                .build();

        when(userService.getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND)).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenReturn(expectedPost);

        // When
        try (MockedStatic<FileUtils> mockedFileUtils = mockStatic(FileUtils.class)) {
            mockedFileUtils.when(() -> FileUtils.writeToFile(eq("test@example.com"), anyString(), eq(".png")))
                    .thenReturn(expectedFilePath);

            final Post result = postServiceImpl.createPost(userId, imageBase64, postText);

            // Then
            assertNotNull(result);
            assertEquals(expectedPost, result);
            verify(userService).getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND);
            verify(postRepository).save(any(Post.class));
            mockedFileUtils.verify(() -> FileUtils.writeToFile(eq("test@example.com"), anyString(), eq(".png")));
        }
    }

    @Test
    void createPost_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        final String imageBase64 = "data:image/png;base64,test";
        final String postText = "Test post content";

        when(userService.getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND))
                .thenThrow(new NoSuchElementException(ERROR_USER_NOT_FOUND));

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> postServiceImpl.createPost(userId, imageBase64, postText)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userService).getUserByIdOrTrow(userId, ERROR_USER_NOT_FOUND);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void createPost_givenInvalidImageFormat_shouldThrowException() {
        // Given
        final Integer userId = 1;
        final String invalidImageBase64 = "data:image/bmp;base64,invalidformat";
        final String postText = "Test post content";

        final User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
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
        final String newImageBase64 = "data:image/png;base64,newImage";
        final String newText = "Updated post content";
        final String oldFilePath = "/path/to/old/image.jpg";
        final String newFilePath = "/path/to/new/image.png";

        final User user = User.builder()
                .id(1)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final Image oldImage = Image.builder()
                .id(1)
                .filePath(oldFilePath)
                .build();

        final Post existingPost = Post.builder()
                .id(postId)
                .postText("Old post content")
                .postDate(now())
                .user(user)
                .image(oldImage)
                .postLikes(List.of())
                .postComments(List.of())
                .build();

        final Post updatedPost = Post.builder()
                .id(postId)
                .postText(newText)
                .postDate(now())
                .user(user)
                .image(Image.builder().filePath(newFilePath).build())
                .postLikes(List.of())
                .postComments(List.of())
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(updatedPost);

        // When
        try (MockedStatic<FileUtils> mockedFileUtils = mockStatic(FileUtils.class)) {
            mockedFileUtils.when(() -> FileUtils.writeToFile(eq("test@example.com"), anyString(), eq(".png")))
                    .thenReturn(newFilePath);

            final Post result = postServiceImpl.updatePost(postId, newImageBase64, newText);

            // Then
            assertNotNull(result);
            assertEquals(updatedPost, result);
            verify(postRepository).findById(postId);
            verify(postRepository).save(existingPost);
            mockedFileUtils.verify(() -> FileUtils.deleteFile(oldFilePath));
            mockedFileUtils.verify(() -> FileUtils.writeToFile(eq("test@example.com"), anyString(), eq(".png")));
        }
    }

    @Test
    void updatePost_givenNonExistingPost_shouldThrowException() {
        // Given
        final Integer postId = 999;
        final String newImageBase64 = "data:image/png;base64,newImage";
        final String newText = "Updated post content";

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> postServiceImpl.updatePost(postId, newImageBase64, newText)
        );

        // Then
        assertEquals(ERROR_POST_NOT_FOUND, exception.getMessage());
        verify(postRepository).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getAllPostsByUserId_shouldReturnPosts() {
        // Given
        final Integer userId = 1;
        final Pageable pageable = mock(Pageable.class);
        final Post post = mock(Post.class);
        final Page<Post> expectedPage = new PageImpl<>(List.of(post));

        when(postRepository.findAllByUser_Id(userId, pageable)).thenReturn(expectedPage);

        // When
        final Page<Post> result = postServiceImpl.getAllPostsByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        assertEquals(1, result.getContent().size());
        verify(postRepository).findAllByUser_Id(userId, pageable);
    }
}