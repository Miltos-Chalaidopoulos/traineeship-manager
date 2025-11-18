package traineeship_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import traineeship_manager.domainmodel.Professor;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
	Optional<Professor> findById(String username);
}
