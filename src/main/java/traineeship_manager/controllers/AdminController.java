package traineeship_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import traineeship_manager.domainmodel.Student;
import traineeship_manager.repository.StudentRepository;
import java.util.List;
import traineeship_manager.domainmodel.TraineeshipPosition;
import traineeship_manager.repository.TraineeshipPositionRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import traineeship_manager.domainmodel.Professor;
import traineeship_manager.repository.ProfessorRepository;
import traineeship_manager.recomendstrategies.RecommendStudent;
import traineeship_manager.recomendstrategies.RecommendProfessor;
import traineeship_manager.services.TraineeshipPositionService;

@Controller
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TraineeshipPositionRepository traineeshipRepo;
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private TraineeshipPositionService traineeshipService;
    
    @RequestMapping("/admin/dashboard")
    public String getAdminHome(){
        return "admin/dashboard";
    }
    
    @GetMapping("/admin/studentsLookingForTraineeship")
    public String showStudentsLookingForTraineeship(Model model) {
        List<Student> studentsLookingForTraineeship = studentRepository.findByLookingForTraineeshipTrue();
        model.addAttribute("students", studentsLookingForTraineeship);
        return "admin/studentsLookingForTraineeship"; 
    }

    @PostMapping("/admin/assign")
    public String assignPositionToStudent(@RequestParam String studentUsername, @RequestParam Long positionId) {
        Student student = studentRepository.findByUsername(studentUsername);
        TraineeshipPosition position = traineeshipRepo.findById(positionId).orElse(null);

        if (student != null && position != null && !position.getIsAssigned()) {
            student.setLookingForTraineeship(false);
            position.setIsAssigned(true);
            position.setStudent(student);
            
            studentRepository.save(student);
            traineeshipRepo.save(position);
        }

        return "redirect:/admin/studentsLookingForTraineeship";
    }
    
    @GetMapping("/admin/recommendedPositions")
    public String findRecommendedTraineeships(@RequestParam("username") String username, Model model) {
    	Student student = studentRepository.findByUsername(username);
    	List<TraineeshipPosition> unassignedPositions = traineeshipRepo.findByIsAssignedFalse();
    	List<TraineeshipPosition> recommendedPositions = RecommendStudent.recommendByStrategy(student, unassignedPositions);

    	model.addAttribute("student", student);
    	model.addAttribute("positions", recommendedPositions);
    	return "admin/recommended_positions";

    } 
    
    @GetMapping("/admin/professors")
    public String showAllProfessors(Model model) {
        List<Professor> professors = professorRepository.findAll();
        model.addAttribute("professors", professors);
        return "admin/professors";
    }

    @GetMapping("/admin/findTraineeshipForProfessor")
    public String findTraineeshipForProfessor(@RequestParam("username") String username, Model model) {
    	Professor professor = professorRepository.findById(username).orElseThrow();
        List<TraineeshipPosition> unassignedPositions = traineeshipRepo.findBySupervisorIsNull();
        List<TraineeshipPosition> recommendedPositions = RecommendProfessor.recommendByStrategy(professor, unassignedPositions);
        model.addAttribute("professor", professor);
        model.addAttribute("unassignedPositions", recommendedPositions);
        return "admin/professor_traineeships";
    }
    
    @PostMapping("/admin/assignSupervisor")
    public String assignSupervisorToPosition(@RequestParam Long positionId, 
                                             @RequestParam String professorUsername) {
        TraineeshipPosition position = traineeshipRepo.findById(positionId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid position ID"));

        Professor professor = professorRepository.findById(professorUsername)
            .orElseThrow(() -> new IllegalArgumentException("Invalid professor username"));

        position.setSupervisor(professor);
        traineeshipRepo.save(position);

        return "redirect:/admin/findTraineeshipForProfessor?username=" + professorUsername;
    }

    @GetMapping("/admin/traineeships")
    public String showInProgress(Model model) {
        List<TraineeshipPosition> inProgress = traineeshipRepo.findByStudentIsNotNullAndSupervisorIsNotNull();
        model.addAttribute("inProgressPositions", inProgress);
        return "admin/traineeships";
    }
    
    @GetMapping("/admin/traineeship")
    public String showTraineeshipDetails(@RequestParam("id") Long id, Model model) {
        TraineeshipPosition position = traineeshipRepo.findById(id).orElse(null);
        if (position == null) {
            return "error";
        }
        model.addAttribute("position", position);
        return "admin/traineeship-details";
    }
    
    @PostMapping("/admin/traineeships/grade")
    public String updatePassFailGrade(@RequestParam Long positionId, @RequestParam Boolean passFailGrade) {
    	traineeshipService.updatePassFailGrade(positionId, passFailGrade);
    	return "redirect:/admin/traineeships";
    }
}
