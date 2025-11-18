package traineeship_manager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import traineeship_manager.domainmodel.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	Student findByUsername(String username);
	List<Student> findByLookingForTraineeshipTrue();
}
