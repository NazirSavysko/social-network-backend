package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import social.network.backend.socialnetwork.entity.Subscription;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.entity.enums.Role;
import social.network.backend.socialnetwork.repository.SubscriptionRepository;
import social.network.backend.socialnetwork.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static social.network.backend.socialnetwork.validation.ErrorMessages.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Test
    void getSubscriptionById_givenExistingId_shouldReturnSubscription() {
        // Given
        final Integer subscriptionId = 1;
        final User subscriber = User.builder()
                .id(1)
                .email("subscriber@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User target = User.builder()
                .id(2)
                .email("target@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Subscription expectedSubscription = Subscription.builder()
                .id(subscriptionId)
                .subscriber(subscriber)
                .target(target)
                .subscribedAt(LocalDateTime.now())
                .build();

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(expectedSubscription));

        // When
        final Subscription result = subscriptionServiceImpl.getSubscriptionById(subscriptionId);

        // Then
        assertNotNull(result);
        assertEquals(expectedSubscription, result);
        verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void getSubscriptionById_givenNonExistingId_shouldThrowException() {
        // Given
        final Integer subscriptionId = 999;
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.getSubscriptionById(subscriptionId)
        );

        // Then
        assertEquals(ERROR_SUBSCRIPTION_NOT_FOUND, exception.getMessage());
        verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void createSubscription_givenValidData_shouldCreateSubscription() {
        // Given
        final Integer subscriberId = 1;
        final Integer targetId = 2;

        final User subscriber = User.builder()
                .id(subscriberId)
                .email("subscriber@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User target = User.builder()
                .id(targetId)
                .email("target@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Subscription expectedSubscription = Subscription.builder()
                .id(1)
                .subscriber(subscriber)
                .target(target)
                .subscribedAt(LocalDateTime.now())
                .build();

        when(userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND)).thenReturn(subscriber);
        when(userService.getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND)).thenReturn(target);
        when(subscriptionRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetId)).thenReturn(false);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(expectedSubscription);

        // When
        final Subscription result = subscriptionServiceImpl.createSubscription(subscriberId, targetId);

        // Then
        assertNotNull(result);
        assertEquals(expectedSubscription, result);
        verify(userService).getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);
        verify(subscriptionRepository).existsBySubscriber_IdAndTarget_Id(subscriberId, targetId);
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void createSubscription_givenNonExistingSubscriber_shouldThrowException() {
        // Given
        final Integer subscriberId = 999;
        final Integer targetId = 2;

        when(userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND))
                .thenThrow(new NoSuchElementException(ERROR_SUBSCRIBER_NOT_FOUND));

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.createSubscription(subscriberId, targetId)
        );

        // Then
        assertEquals(ERROR_SUBSCRIBER_NOT_FOUND, exception.getMessage());
        verify(userService).getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        verify(userService, never()).getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);
        verify(subscriptionRepository, never()).existsBySubscriber_IdAndTarget_Id(any(), any());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void createSubscription_givenNonExistingTarget_shouldThrowException() {
        // Given
        final Integer subscriberId = 1;
        final Integer targetId = 999;

        final User subscriber = User.builder()
                .id(subscriberId)
                .email("subscriber@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        when(userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND)).thenReturn(subscriber);
        when(userService.getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND))
                .thenThrow(new NoSuchElementException(ERROR_TARGET_NOT_FOUND));

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.createSubscription(subscriberId, targetId)
        );

        // Then
        assertEquals(ERROR_TARGET_NOT_FOUND, exception.getMessage());
        verify(userService).getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);
        verify(subscriptionRepository, never()).existsBySubscriber_IdAndTarget_Id(any(), any());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void createSubscription_whenSubscriptionAlreadyExists_shouldThrowException() {
        // Given
        final Integer subscriberId = 1;
        final Integer targetId = 2;

        final User subscriber = User.builder()
                .id(subscriberId)
                .email("subscriber@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User target = User.builder()
                .id(targetId)
                .email("target@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        when(userService.getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND)).thenReturn(subscriber);
        when(userService.getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND)).thenReturn(target);
        when(subscriptionRepository.existsBySubscriber_IdAndTarget_Id(subscriberId, targetId)).thenReturn(true);

        // When
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> subscriptionServiceImpl.createSubscription(subscriberId, targetId)
        );

        // Then
        assertEquals(ERROR_SUBSCRIPTION_ALREADY_EXISTS, exception.getMessage());
        verify(userService).getUserByIdOrTrow(subscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(targetId, ERROR_TARGET_NOT_FOUND);
        verify(subscriptionRepository).existsBySubscriber_IdAndTarget_Id(subscriberId, targetId);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void updateSubscription_givenExistingSubscription_shouldUpdateSuccessfully() {
        // Given
        final Integer subscriptionId = 1;
        final Integer newSubscriberId = 2;
        final Integer newTargetId = 3;

        final User oldSubscriber = User.builder()
                .id(1)
                .email("old@example.com")
                .name("Old")
                .surname("User")
                .role(Role.ROLE_USER)
                .build();

        final User newSubscriber = User.builder()
                .id(newSubscriberId)
                .email("new_subscriber@example.com")
                .name("John")
                .surname("Doe")
                .role(Role.ROLE_USER)
                .build();

        final User newTarget = User.builder()
                .id(newTargetId)
                .email("new_target@example.com")
                .name("Jane")
                .surname("Smith")
                .role(Role.ROLE_USER)
                .build();

        final Subscription existingSubscription = Subscription.builder()
                .id(subscriptionId)
                .subscriber(oldSubscriber)
                .target(oldSubscriber)
                .subscribedAt(LocalDateTime.now())
                .build();

        final Subscription updatedSubscription = Subscription.builder()
                .id(subscriptionId)
                .subscriber(newSubscriber)
                .target(newTarget)
                .subscribedAt(LocalDateTime.now())
                .build();

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        when(userService.getUserByIdOrTrow(newSubscriberId, ERROR_SUBSCRIBER_NOT_FOUND)).thenReturn(newSubscriber);
        when(userService.getUserByIdOrTrow(newTargetId, ERROR_TARGET_NOT_FOUND)).thenReturn(newTarget);
        when(subscriptionRepository.save(existingSubscription)).thenReturn(updatedSubscription);

        // When
        final Subscription result = subscriptionServiceImpl.updateSubscription(subscriptionId, newSubscriberId, newTargetId);

        // Then
        assertNotNull(result);
        assertEquals(updatedSubscription, result);
        assertEquals(newSubscriber, existingSubscription.getSubscriber());
        assertEquals(newTarget, existingSubscription.getTarget());
        verify(subscriptionRepository).findById(subscriptionId);
        verify(userService).getUserByIdOrTrow(newSubscriberId, ERROR_SUBSCRIBER_NOT_FOUND);
        verify(userService).getUserByIdOrTrow(newTargetId, ERROR_TARGET_NOT_FOUND);
        verify(subscriptionRepository).save(existingSubscription);
    }

    @Test
    void updateSubscription_givenNonExistingSubscription_shouldThrowException() {
        // Given
        final Integer subscriptionId = 999;
        final Integer subscriberId = 1;
        final Integer targetId = 2;

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.updateSubscription(subscriptionId, subscriberId, targetId)
        );

        // Then
        assertEquals(ERROR_SUBSCRIPTION_NOT_FOUND, exception.getMessage());
        verify(subscriptionRepository).findById(subscriptionId);
        verify(userService, never()).getUserByIdOrTrow(any(), any());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void deleteSubscription_shouldCallRepositoryDelete() {
        // Given
        final Integer subscriptionId = 1;

        // When
        subscriptionServiceImpl.deleteSubscription(subscriptionId);

        // Then
        verify(subscriptionRepository).deleteById(subscriptionId);
    }

    @Test
    void countSubscriptionsByUserId_shouldReturnCount() {
        // Given
        final Integer userId = 1;
        final long expectedCount = 5L;

        when(subscriptionRepository.countByTarget_Id(userId)).thenReturn(expectedCount);

        // When
        final int result = subscriptionServiceImpl.countSubscriptionsByUserId(userId);

        // Then
        assertEquals((int) expectedCount, result);
        verify(subscriptionRepository).countByTarget_Id(userId);
    }

    @Test
    void countSubscribersByUserId_shouldReturnCount() {
        // Given
        final Integer userId = 1;
        final long expectedCount = 3L;

        when(subscriptionRepository.countBySubscriber_Id(userId)).thenReturn(expectedCount);

        // When
        final int result = subscriptionServiceImpl.countSubscribersByUserId(userId);

        // Then
        assertEquals((int) expectedCount, result);
        verify(subscriptionRepository).countBySubscriber_Id(userId);
    }

    @Test
    void getSubscriptionsByUserId_givenExistingUser_shouldReturnSubscriptions() {
        // Given
        final Integer userId = 1;
        final Pageable pageable = mock(Pageable.class);
        final Subscription subscription = mock(Subscription.class);
        final Page<Subscription> expectedPage = new PageImpl<>(List.of(subscription));

        doNothing().when(userService).isUserExistByIdOrThrow(userId);
        when(subscriptionRepository.findAllBySubscriber_id(userId, pageable)).thenReturn(expectedPage);

        // When
        final Page<Subscription> result = subscriptionServiceImpl.getSubscriptionsByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        assertEquals(1, result.getContent().size());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(subscriptionRepository).findAllBySubscriber_id(userId, pageable);
    }

    @Test
    void getSubscriptionsByUserId_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        final Pageable pageable = mock(Pageable.class);

        doThrow(new NoSuchElementException(ERROR_USER_NOT_FOUND))
                .when(userService).isUserExistByIdOrThrow(userId);

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.getSubscriptionsByUserId(userId, pageable)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(subscriptionRepository, never()).findAllBySubscriber_id(any(), any());
    }

    @Test
    void getSubscribersByUserId_givenExistingUser_shouldReturnSubscribers() {
        // Given
        final Integer userId = 1;
        final Pageable pageable = mock(Pageable.class);
        final Subscription subscription = mock(Subscription.class);
        final Page<Subscription> expectedPage = new PageImpl<>(List.of(subscription));

        doNothing().when(userService).isUserExistByIdOrThrow(userId);
        when(subscriptionRepository.findAllByTarget_id(userId, pageable)).thenReturn(expectedPage);

        // When
        final Page<Subscription> result = subscriptionServiceImpl.getSubscribersByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        assertEquals(1, result.getContent().size());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(subscriptionRepository).findAllByTarget_id(userId, pageable);
    }

    @Test
    void getSubscribersByUserId_givenNonExistingUser_shouldThrowException() {
        // Given
        final Integer userId = 999;
        final Pageable pageable = mock(Pageable.class);

        doThrow(new NoSuchElementException(ERROR_USER_NOT_FOUND))
                .when(userService).isUserExistByIdOrThrow(userId);

        // When
        final NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> subscriptionServiceImpl.getSubscribersByUserId(userId, pageable)
        );

        // Then
        assertEquals(ERROR_USER_NOT_FOUND, exception.getMessage());
        verify(userService).isUserExistByIdOrThrow(userId);
        verify(subscriptionRepository, never()).findAllByTarget_id(any(), any());
    }
}