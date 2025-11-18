package traineeship_manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import traineeship_manager.domainmodel.Professor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import traineeship_manager.domainmodel.TraineeshipPosition;
import java.util.List;
import java.util.Optional;

import traineeship_manager.domainmodel.*;
import traineeship_manager.repository.*;


@Controller
public class ProfessorController {
	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@RequestMapping("/professor/dashboard")
    public String getAdminHome(){
        return "professor/dashboard";
    }
	
	@GetMapping("/professor/profile")
	   public String showProfile(Model model, Principal principal) {
	       String username = principal.getName();
	       Professor professor = professorRepository.findById(username).orElse(new Professor());
	       professor.setUsername(username);
           model.addAttribute("profile", professor);
           return "professor/profile";
	    }
	
	@PostMapping("/professor/save")
    public String saveProfile(@ModelAttribute("profile") Professor professor, Principal principal) {
        professor.setUsername(principal.getName());
        professorRepository.save(professor);
        return "redirect:/professor/dashboard";
    }
	
	@GetMapping("professor/positions")
	public String viewPositions(Model model, Authentication authentication) {
	    String professorUsername = authentication.getName();
	    Optional<Professor> professorOpt = professorRepository.findById(professorUsername);

	    if (professorOpt.isEmpty()) {
	        model.addAttribute("errorMessage", "No profile found for the logged-in professor.");
	        model.addAttribute("positions", List.of());  // κενή λίστα
	        return "professor/positions";
	    }

	    Professor professor = professorOpt.get();
	    List<TraineeshipPosition> positions = traineeshipPositionRepository.findBySupervisor(professor);
	    model.addAttribute("positions", positions);

	    return "professor/positions";
	}
	
	@GetMapping("/professor/positions/evaluate/{id}")
	public String showProfessorEvaluationPage(@PathVariable Long id, Model model) {
	    TraineeshipPosition position = traineeshipPositionRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid traineeship position ID"));
	    model.addAttribute("positionTitle", position.getTitle());
	    model.addAttribute("studentName", position.getStudent() != null ? position.getStudent().getStudentName() : "Unknown Student");
	    model.addAttribute("positionId", id);
	    return "professor/evaluate-position";
	}

	@PostMapping("/professor/positions/evaluate/{id}")
	public String submitProfessorEvaluation(@PathVariable Long id, 
	                                        @RequestParam int motivation, 
	                                        @RequestParam int efficiency, 
	                                        @RequestParam int effectiveness, 
	                                        Model model) {
	    TraineeshipPosition position = traineeshipPositionRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid traineeship position ID"));


	    Evaluation existingEvaluation = evaluationRepository.findByTraineeshipPositionAndIsProffesorTrue(position);
	    
	    if (existingEvaluation != null) {
	        existingEvaluation.setMotivation(motivation);
	        existingEvaluation.setEfficiency(efficiency);
	        existingEvaluation.setEffectiveness(effectiveness);
	        evaluationRepository.save(existingEvaluation);
	        model.addAttribute("message", "Evaluation updated successfully!");
	    } else {
	        Evaluation evaluation = new Evaluation();
	        evaluation.setMotivation(motivation);
	        evaluation.setEfficiency(efficiency);
	        evaluation.setEffectiveness(effectiveness);
	        evaluation.setProffesor(true);
	        evaluation.setTraineeshipPosition(position);
	        position.getEvaluations().add(evaluation);
	        evaluationRepository.save(evaluation);
	        traineeshipPositionRepository.save(position);
	        model.addAttribute("message", "Evaluation saved successfully!");
	    }
	    
	    return "redirect:/professor/positions";
	}
}
