package traineeship_manager.domainmodel;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "traineeship_positions")
public class TraineeshipPosition {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    private String title;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String topics;
    private String skills;
    private boolean isAssigned;
    
    @Column(name = "student_log_book")
    private String studentLogBook;
    private boolean passFailGrade;
    
    @ManyToOne
    @JoinColumn(name = "professor_username", referencedColumnName = "username")
    private Professor supervisor;


    @ManyToOne(optional = false)
    @JoinColumn(name = "company_username", referencedColumnName = "username", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "traineeshipPosition", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;
    
    public List<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	@OneToOne
    @JoinColumn(name = "student_username")
    private Student student;

    
    public int getId() {
    	return id;
    }
    public void setId(int s) {
    	id = s;
    }
    public String getTitle() {
    	return title;
    }
    public void setTitle(String s) {
    	title = s;
    }
    public String getDescription() {
    	return description;
    }
    public void setDescription(String s) {
    	description = s;
    }
    public LocalDate getFromDate() {
    	return fromDate;
    }
    public void setFromDate(LocalDate s) {
    	fromDate = s;
    }
    public LocalDate getToDate() {
    	return toDate;
    }
    public void setToDate(LocalDate s) {
    	toDate = s;
    }
    public String getTopics() {
    	return topics;
    }
    public void setTopics(String s) {
    	topics = s;
    }
    public String getSkills() {
    	return skills;
    }
    public void setSkills(String s) {
    	skills = s;
    }
    public boolean getIsAssigned() {
    	return isAssigned;
    }
    public void setIsAssigned(boolean s) {
    	isAssigned = s;
    }
    public String getStudentLogBook() {
    	return studentLogBook;
    }
    public void setStudentLogBook(String s) {
    	studentLogBook = s;
    }
    public boolean getPassFailGrade() {
    	return passFailGrade;
    }
    public void setPassFailGrade(boolean s) {
    	passFailGrade = s;
    }
    public void setCompany(Company s) {
    	company = s;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public Professor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Professor supervisor) {
        this.supervisor = supervisor;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String toString() {
    	return "Traineeship Posision "+title+" with id"+id;
    }
}