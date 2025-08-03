package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import social.network.backend.socialnetwork.entity.Message;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.entity.enums.Role;
import social.network.backend.socialnetwork.repository.MessageRepository;
import social.network.backend.socialnetwork.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static social.network.backend.socialnetwork.validation.ErrorMessages.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageServiceImpl messageServiceImpl;

    @Test
    void getMessageById_givenExistingId_shouldReturnMessage() {
        // Given
        final Integer messageId = 1;
        final User sender = User.builder()
                .id(1)
                .email("sender@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User recipient = User.builder()
                .id(2)
                .email("recipient@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Message expectedMessage = Message.builder()
                .id(messageId)
                .messageText("Hello World")
                .messageDate(LocalDateTime.now())
                .sender(sender)
                .recipient(recipient)
                .build();

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

        // When
        final Message result = messageServiceImpl.getMessageById(messageId);

        // Then
        assertNotNull(result);
        assertEquals(expectedMessage, result);
        verify(messageRepository).findById(messageId);
    }

    @Test
    void getMessageById_givenNonExistingId_shouldThrowException() {
        // Given
        final Integer messageId = 999;
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> messageServiceImpl.getMessageById(messageId)
        );

        // Then
        assertEquals(ERROR_MESSAGE_NOT_FOUND, exception.getMessage());
        verify(messageRepository).findById(messageId);
    }

    @Test
    void deleteMessage_shouldCallRepositoryDelete() {
        // Given
        final Integer messageId = 1;

        // When
        messageServiceImpl.deleteMessage(messageId);

        // Then
        verify(messageRepository).deleteById(messageId);
    }

    @Test
    void getAllMessagesByUserId_givenExistingUser_shouldReturnMessages() {
        // Given
        final Integer userId = 1;
        final Pageable pageable = mock(Pageable.class);
        final Message message = mock(Message.class);
        final Page<Message> expectedPage = new PageImpl<>(List.of(message));

        doNothing().when(userService).isUserExistByIdOrThrow(userId);
        when(messageRepository.findAllBySender_Id(userId, pageable)).thenReturn(expectedPage);

        // When
        final Page<Message> result = messageServiceImpl.getAllMessagesByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        assertEquals(1, result.getContent().size());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(messageRepository).findAllBySender_Id(userId, pageable);
    }

    @Test
    void getAllMessagesByUserId_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        final Pageable pageable = mock(Pageable.class);

        doThrow(new NoSuchElementException(ERROR_USER_NOT_FOUND))
                .when(userService).isUserExistByIdOrThrow(userId);

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> messageServiceImpl.getAllMessagesByUserId(userId, pageable)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(messageRepository, never()).findAllBySender_Id(any(), any());
    }

    @Test
    void createMessage_givenValidData_shouldCreateMessage() {
        // Given
        final String content = "Hello World";
        final Integer senderId = 1;
        final Integer recipientId = 2;

        final User sender = User.builder()
                .id(senderId)
                .email("sender@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User recipient = User.builder()
                .id(recipientId)
                .email("recipient@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Message expectedMessage = Message.builder()
                .id(1)
                .messageText(content)
                .messageDate(LocalDateTime.now())
                .sender(sender)
                .recipient(recipient)
                .build();

        when(userService.getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND)).thenReturn(sender);
        when(userService.getUserByIdOrTrow(recipientId, ERROR_MESSAGE_RECIPIENT_NOT_FOUND)).thenReturn(recipient);
        when(messageRepository.save(any(Message.class))).thenReturn(expectedMessage);

        // When
        final Message result = messageServiceImpl.createMessage(content, senderId, recipientId);

        // Then
        assertNotNull(result);
        assertEquals(expectedMessage, result);
        verify(userService).getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(recipientId, ERROR_MESSAGE_RECIPIENT_NOT_FOUND);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void createMessage_givenNonExistingSender_shouldThrowException() {
        // Given
        final String content = "Hello World";
        final Integer senderId = 999;
        final Integer recipientId = 2;

        when(userService.getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND))
                .thenThrow(new NoSuchElementException(ERROR_MESSAGE_SENDER_NOT_FOUND));

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> messageServiceImpl.createMessage(content, senderId, recipientId)
        );

        // Then
        assertEquals(ERROR_MESSAGE_SENDER_NOT_FOUND, exception.getMessage());
        verify(userService).getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND);
        verify(userService, never()).getUserByIdOrTrow(recipientId, ERROR_MESSAGE_RECIPIENT_NOT_FOUND);
        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    void createMessage_givenNonExistingRecipient_shouldThrowException() {
        // Given
        final String content = "Hello World";
        final Integer senderId = 1;
        final Integer recipientId = 999;

        final User sender = User.builder()
                .id(senderId)
                .email("sender@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        when(userService.getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND)).thenReturn(sender);
        when(userService.getUserByIdOrTrow(recipientId, ERROR_MESSAGE_RECIPIENT_NOT_FOUND))
                .thenThrow(new NoSuchElementException(ERROR_MESSAGE_RECIPIENT_NOT_FOUND));

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> messageServiceImpl.createMessage(content, senderId, recipientId)
        );

        // Then
        assertEquals(ERROR_MESSAGE_RECIPIENT_NOT_FOUND, exception.getMessage());
        verify(userService).getUserByIdOrTrow(senderId, ERROR_MESSAGE_SENDER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(recipientId, ERROR_MESSAGE_RECIPIENT_NOT_FOUND);
        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    void updateMessage_givenExistingMessage_shouldUpdateSuccessfully() {
        // Given
        final Integer messageId = 1;
        final String newContent = "Updated message";

        final User sender = User.builder()
                .id(1)
                .email("sender@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User recipient = User.builder()
                .id(2)
                .email("recipient@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Message existingMessage = Message.builder()
                .id(messageId)
                .messageText("Old message")
                .messageDate(LocalDateTime.now())
                .sender(sender)
                .recipient(recipient)
                .build();

        final Message updatedMessage = Message.builder()
                .id(messageId)
                .messageText(newContent)
                .messageDate(LocalDateTime.now())
                .sender(sender)
                .recipient(recipient)
                .build();

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(existingMessage)).thenReturn(updatedMessage);

        // When
        final Message result = messageServiceImpl.updateMessage(messageId, newContent);

        // Then
        assertNotNull(result);
        assertEquals(updatedMessage, result);
        assertEquals(newContent, existingMessage.getMessageText());
        verify(messageRepository).findById(messageId);
        verify(messageRepository).save(existingMessage);
    }

    @Test
    void updateMessage_givenNonExistingMessage_shouldThrowException() {
        // Given
        final Integer messageId = 999;
        final String newContent = "Updated message";

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> messageServiceImpl.updateMessage(messageId, newContent)
        );

        // Then
        assertEquals(ERROR_MESSAGE_NOT_FOUND, exception.getMessage());
        verify(messageRepository).findById(messageId);
        verify(messageRepository, never()).save(any(Message.class));
    }
}