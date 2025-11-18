package traineeship_manager.services;

import org.springframework.stereotype.Service;
import java.util.List;
import traineeship_manager.domainmodel.TraineeshipPosition;
import traineeship_manager.repository.TraineeshipPositionRepository;

@Service
public class TraineeshipPositionServiceImpl implements TraineeshipPositionService {
	private final TraineeshipPositionRepository positionRepository;

    public TraineeshipPositionServiceImpl(TraineeshipPositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<TraineeshipPosition> findByIsAssigned(boolean isAssigned) {
        return positionRepository.findByIsAssigned(isAssigned);
    }
    
    @Override
    public List<TraineeshipPosition> getUnassignedTraineeships() {
        return positionRepository.findByIsAssignedFalse();
    }
    
    @Override
    public void updatePassFailGrade(Long id, Boolean grade) {
        TraineeshipPosition position = positionRepository.findById(id).orElseThrow();
        position.setPassFailGrade(grade);
        positionRepository.save(position);
    }

}
