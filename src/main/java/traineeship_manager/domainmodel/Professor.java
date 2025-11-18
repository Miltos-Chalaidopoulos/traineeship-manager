package traineeship_manager.domainmodel;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    private String professorName;
    private String interests;
    
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> supervisedPositions;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getProfessorName() {
        return professorName;
    }
    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
    
    public String getInterests() {
        return interests;
    }
    public void setInterests(String interests) {
        this.interests = interests;
    }
    
    public List<TraineeshipPosition> getSupervisedPositions() {
        return supervisedPositions;
    }
    
    public void setSupervisedPositions(List<TraineeshipPosition> supervisedPositions) {
    	this.supervisedPositions = supervisedPositions;
    }
    
    @Override
    public String toString() {
        return "Professor " + professorName + " (Username: " + username + ")";
    }
}
