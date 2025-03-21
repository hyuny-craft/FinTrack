package side.fintrack.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import side.fintrack.domain.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findMyEmail(String email);

    boolean existsByEmail(String email);

}
