package social.network.backend.socialnetwork.mapper.impl.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.entity.User;
import social.network.backend.socialnetwork.mapper.Mapper;

import java.util.Collection;
import java.util.List;

@Component
public class UserDetailsMapperImpl implements Mapper<User, UserDetails> {

    @Override
    public UserDetails toDto(final User entity) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(entity.getRole());
            }

            @Override
            public String getPassword() {
                return entity.getPassword();
            }

            @Override
            public String getUsername() {
                return entity.getEmail();
            }
        };
    }
}
