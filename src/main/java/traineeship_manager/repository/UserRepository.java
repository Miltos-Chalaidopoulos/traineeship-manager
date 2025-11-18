package traineeship_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import traineeship_manager.domainmodel.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}

