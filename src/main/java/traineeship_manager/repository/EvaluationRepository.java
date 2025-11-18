package traineeship_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traineeship_manager.domainmodel.Evaluation;
import traineeship_manager.domainmodel.TraineeshipPosition;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
	Evaluation findByTraineeshipPositionAndIsProffesorFalse(TraineeshipPosition traineeshipPosition);
	Evaluation findByTraineeshipPositionAndIsProffesorTrue(TraineeshipPosition traineeshipPosition);
}
