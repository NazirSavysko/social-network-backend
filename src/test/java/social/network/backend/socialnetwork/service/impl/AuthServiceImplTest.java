package social.network.backend.socialnetwork.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import social.network.backend.socialnetwork.security.jwt.JwtTokenFactory;
import social.network.backend.socialnetwork.security.jwt.impl.AuthServiceImpl;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JwtTokenFactory jwtTokenFactory;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void authenticateAndIssueJwt_success_returnsToken_andPassesCredentials() {
        // Given
        final String email = "user@example.com";
        final String rawPassword = "secret";

        final UserDetails principal = new User(email, "encoded", Collections.emptyList());
        final Authentication authentication = mock(Authentication.class);

        // When
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(jwtTokenFactory.createToken(principal)).thenReturn("jwt-123");

        final String token = authService.authenticateAndIssueJwt(email, rawPassword);

        // Then
        assertThat(token).isEqualTo("jwt-123");

        final ArgumentCaptor<Authentication> authCaptor = ArgumentCaptor.forClass(Authentication.class);
        verify(authenticationManager).authenticate(authCaptor.capture());
        Authentication passed = authCaptor.getValue();
        assertThat(passed).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(passed.getName()).isEqualTo(email);
        assertThat(passed.getCredentials()).isEqualTo(rawPassword);

        verify(jwtTokenFactory).createToken(principal);
    }

    @Test
    void authenticateAndIssueJwt_badCredentials_propagatesException() {
        // Given
        String email = "user@example.com";
        String rawPassword = "wrong";

        // When
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("bad creds"));

        // Then
        assertThrows(BadCredentialsException.class,
                () -> authService.authenticateAndIssueJwt(email, rawPassword));

        verifyNoInteractions(jwtTokenFactory);
    }
}

