package social.network.backend.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
