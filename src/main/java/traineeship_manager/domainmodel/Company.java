package traineeship_manager.domainmodel;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company{
	@Id
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "Company_location")
	private String companyLocation;
	
	public String getCompanyLocation() {
		return companyLocation;
	}
	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<TraineeshipPosition> getPositions() {
		return positions;
	}
	public void setPositions(List<TraineeshipPosition> positions) {
		this.positions = positions;
	}
	@Column(name = "companyName")
	private String companyName;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> positions;
	
	
	public String getName() {
    	return companyName;
    }
    public void setName(String s) {
    	companyName = s;
    }
    
    public String getUsername() {
    	return username;
    }
    public void setUsername(String s) {
    	username = s;
    }
    public String toString() {
    	return "Company "+ companyName;
    }
}