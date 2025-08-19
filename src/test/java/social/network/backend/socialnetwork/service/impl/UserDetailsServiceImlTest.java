package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.entity.enums.Role;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.repository.UserRepository;
import social.network.backend.socialnetwork.security.UserDetailsServiceIml;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImlTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<User, UserDetails> userDetailsMapper;

    @InjectMocks
    private UserDetailsServiceIml userDetailsServiceIml;

    @Test
    void loadUserByUsername_givenExistingEmail_shouldReturnUserDetails() {
        // Given
        final String userEmail = "sdfgh@gmail.com";
        User user = new User();
        user.setName("testUser");
        user.setEmail(userEmail);
        user.setPassword("1234");

        // When
        when(userRepository.findByEmail(userEmail)).thenReturn(of(user));
        when(userDetailsMapper.toDto(user)).thenReturn(new UserDetails() {
            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(Role.ROLE_USER);
            }

        });

        UserDetails userDetails = userDetailsServiceIml.loadUserByUsername(userEmail);

        // Then
        assertNotNull(userDetails);
        assertEquals(userEmail, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_whenUserNotFound_shouldThrow() {
        // Given
        String userEmail = "absent@mail.com";
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceIml.loadUserByUsername(userEmail));
    }

}