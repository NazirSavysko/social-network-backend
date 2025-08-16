package social.network.backend.socialnetwork.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.repository.UserRepository;

import static social.network.backend.socialnetwork.validation.ErrorMessages.ERROR_USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserDetailsServiceIml implements UserDetailsService {

    private final UserRepository userRepository;
    private final Mapper<User, UserDetails> userDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(ERROR_USER_NOT_FOUND)
        );

        return this.userDetailsMapper.toDto(user);
    }
}
