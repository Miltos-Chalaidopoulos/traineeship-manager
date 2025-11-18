package traineeship_manager.domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student{
	@Id
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "AM")
    private String AM;
	@Column(name = "student_name")
	private String studentName;
	@Column(name = "avg_Grade")
	private double avgGrade;
	@Column(name = "prefered_location")
	private String preferedLocation;
	private String interests;
    private String skills;
    private boolean lookingForTraineeship;
    
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private TraineeshipPosition assignedForTrainship;
    
    public String getAM() {
    	return AM;
    }
    public void setAM(String s) {
    	AM = s;
    }
    public String getUsername() {
    	return username;
    }
    public void setUsername(String s) {
    	username = s;
    }
    public String getStudentName() {
    	return studentName;
    }
    public void setStudentName(String s) {
    	studentName = s;
    }
    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double s) {
    	avgGrade = s;
    }
    public String getPreferedLocation() {
    	return preferedLocation;
    }
    public void setPreferedLocation(String s) {
    	preferedLocation = s;
    }
    public String getInterests(){
    	return interests;
    }
    public void setInterests(String s) {
    	interests = s;
    }
    public String getSkills() {
    	return skills;
    }
    public void setSkills(String s) {
    	skills = s;
    }
    public boolean isLookingForTraineeship() {
        return lookingForTraineeship;
    }
    public void setLookingForTraineeship(boolean s) {
    	lookingForTraineeship = s;
    }
    public TraineeshipPosition getAssignedForTrainship() {
        return assignedForTrainship;
    }

    public void setAssignedForTrainship(TraineeshipPosition assignedForTrainship) {
        this.assignedForTrainship = assignedForTrainship;
    }
    public String toString() {
    	return "Student " + studentName + " with username " + username;
    }
}
