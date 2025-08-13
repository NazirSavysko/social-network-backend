package social.network.backend.socialnetwork.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;
import social.network.backend.socialnetwork.repository.UserRepository;

@Service
@AllArgsConstructor
public final class UserDetailsServiceIml implements UserDetailsService {

    private final UserRepository userRepository;
    private final Mapper<User, UserDetails> userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return this.userDetailsMapper.toDto(user);
    }
}
