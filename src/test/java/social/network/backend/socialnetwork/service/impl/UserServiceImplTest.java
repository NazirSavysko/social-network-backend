package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.entity.enums.Role;
import social.network.backend.socialnetwork.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static social.network.backend.socialnetwork.validation.ErrorMessages.ERROR_USER_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void getUserById_givenExistingId_shouldReturnUser() {
        // Given
        final Integer userId = 1;
        final User expectedUser = User.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .password("password")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        final User result = userServiceImpl.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_givenNonExistingId_shouldThrowException() {
        // Given
        final Integer userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userServiceImpl.getUserById(userId)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void deleteUser_shouldCallRepositoryDelete() {
        // Given
        final Integer userId = 1;

        // When
        userServiceImpl.deleteUser(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void createUser_givenValidData_shouldCreateUser() {
        // Given
        final String email = "test@example.com";
        final String name = "John";
        final String surname = "Doe";
        final String password = "password";

        final User expectedUser = User.builder()
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .role(Role.ROLE_USER)
                .messages(List.of())
                .receivedMessages(List.of())
                .posts(List.of())
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // When
        final User result = userServiceImpl.createUser(email, name, surname, password);

        // Then
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_givenExistingEmail_shouldThrowException() {
        // Given
        final String email = "existing@example.com";
        final String name = "John";
        final String surname = "Doe";
        final String password = "password";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.createUser(email, name, surname, password)
        );

        // Then
        assertEquals("User with existing@example.com email already exists.", exception.getMessage());
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_givenExistingUser_shouldUpdateSuccessfully() {
        // Given
        final Integer userId = 1;
        final String email = "updated@example.com";
        final String name = "Jane";
        final String surname = "Smith";
        final String password = "newpassword";

        final User existingUser = User.builder()
                .id(userId)
                .email("old@example.com")
                .name("John")
                .surname("Doe")
                .password("oldpassword")
                .role(Role.ROLE_USER)
                .build();

        final User updatedUser = User.builder()
                .id(userId)
                .email(email)
                .name(name)
                .surname(surname)
                .password(password)
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // When
        final User result = userServiceImpl.updateUser(userId, email, name, surname, password);

        // Then
        assertNotNull(result);
        assertEquals(updatedUser, result);
        assertEquals(email, existingUser.getEmail());
        assertEquals(name, existingUser.getName());
        assertEquals(surname, existingUser.getSurname());
        assertEquals(password, existingUser.getPassword());
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        final String email = "test@example.com";
        final String name = "John";
        final String surname = "Doe";
        final String password = "password";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userServiceImpl.updateUser(userId, email, name, surname, password)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void isUserExistByIdOrThrow_givenExistingUser_shouldNotThrowException() {
        // Given
        final Integer userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> userServiceImpl.isUserExistByIdOrThrow(userId));
        verify(userRepository).existsById(userId);
    }

    @Test
    void isUserExistByIdOrThrow_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userServiceImpl.isUserExistByIdOrThrow(userId)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userRepository).existsById(userId);
    }

    @Test
    void getUserByIdOrTrow_givenExistingUser_shouldReturnUser() {
        // Given
        final Integer userId = 1;
        final String errorMessage = "Custom error message";
        final User expectedUser = User.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .surname("Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        final User result = userServiceImpl.getUserByIdOrTrow(userId, errorMessage);

        // Then
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserByIdOrTrow_givenNonExistingUser_shouldThrowExceptionWithCustomMessage() {
        // Given
        final Integer userId = 999;
        final String errorMessage = "Custom error message";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> userServiceImpl.getUserByIdOrTrow(userId, errorMessage)
        );

        // Then
        assertEquals(errorMessage, exception.getMessage());
        verify(userRepository).findById(userId);
    }
}