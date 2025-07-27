package social.network.backend.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social.network.backend.socialnetwork.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
