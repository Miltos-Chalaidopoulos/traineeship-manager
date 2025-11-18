package traineeship_manager.domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluations")
public class Evaluation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private int motivation;
    private int efficiency;
    private int effectiveness;
    private boolean isProffesor;
    
    public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
    @JoinColumn(name = "traineeship_position_id", referencedColumnName = "id", nullable = false)
    private TraineeshipPosition traineeshipPosition;
	
    
    public TraineeshipPosition getTraineeshipPosition() {
		return traineeshipPosition;
	}
	public void setTraineeshipPosition(TraineeshipPosition traineeshipPosition) {
		this.traineeshipPosition = traineeshipPosition;
	}
	public int getId() {
    	return id;
    }
    public void setId(int s) {
    	id = s;
    }
    public int getMotivation() {
    	return motivation;
    }
    public void setMotivation(int s) {
    	motivation = s;
    }
    public int getEfficiency() {
    	return efficiency;
    }
    public void setEfficiency(int s) {
    	efficiency = s;
    }
    public int getEffectiveness() {
    	return effectiveness;
    }
    public void setEffectiveness(int s) {
    	effectiveness = s;
    }
    public String toString() {
    	return "Evaluation with id "+ id;
    }
	public boolean isProffesor() {
		return isProffesor;
	}
	public void setProffesor(boolean isProffesor) {
		this.isProffesor = isProffesor;
	}
}