package traineeship_manager.repository;

import java.util.List;
import traineeship_manager.domainmodel.Company;
import traineeship_manager.domainmodel.TraineeshipPosition;
import traineeship_manager.domainmodel.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TraineeshipPositionRepository extends JpaRepository<TraineeshipPosition, Long> {
	List<TraineeshipPosition> findByCompany(Company company);
	List<TraineeshipPosition> findByIsAssigned(boolean isAssigned);
	List<TraineeshipPosition> findByCompanyAndIsAssigned(Company company, boolean isAssigned);
	List<TraineeshipPosition> findByIsAssignedFalse();
	List<TraineeshipPosition> findBySupervisorIsNull();
	Optional<TraineeshipPosition> findById(Long id);
	List<TraineeshipPosition> findBySupervisor(Professor supervisor);
	List<TraineeshipPosition> findByStudentIsNotNullAndSupervisorIsNotNull();
}
