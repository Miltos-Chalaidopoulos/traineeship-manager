package traineeship_manager.services;

import java.util.List;
import traineeship_manager.domainmodel.TraineeshipPosition;

public interface TraineeshipPositionService {
	List<TraineeshipPosition> findByIsAssigned(boolean isAssigned);
	List<TraineeshipPosition> getUnassignedTraineeships();
	public void updatePassFailGrade(Long id, Boolean grade);
}
