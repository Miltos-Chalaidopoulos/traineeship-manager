package traineeship_manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import traineeship_manager.domainmodel.Company;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import traineeship_manager.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import traineeship_manager.domainmodel.*;
import traineeship_manager.repository.*;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanyController {
	@Autowired
    private CompanyRepository companyRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;
	
	@RequestMapping("/company/dashboard")
    public String getAdminHome(){
        return "company/dashboard";
    }
	
	@GetMapping("/company/profile")
    public String companyProfile(Model model, Principal principal) {
        String username = principal.getName();
        Company company = companyRepository.findById(username).orElse(new Company());
        company.setUsername(username);
        model.addAttribute("profile", company);
        return "company/profile";
    }
	
	@PostMapping("/company/save")
    public String saveCompany(@ModelAttribute Company company) {
		companyRepository.save(company);
        return "company/dashboard"; 
    }	

	@GetMapping("/company/addposition")
	public String showAddPositionForm(Model model) {
	    model.addAttribute("addposition", new TraineeshipPosition());
	    return "company/add-position";
	}

	
	@PostMapping("/company/saveposition")
	public String savePosition(@ModelAttribute("position") TraineeshipPosition position,Model model, Principal principal) {
	    String username = principal.getName();
	    Optional<Company> companyOpt = companyRepository.findById(username);

	    if (companyOpt.isEmpty()) {
	        model.addAttribute("addposition", position);
	        model.addAttribute("errorMessage", "You need to create a company profile before adding positions.");
	        return "company/add-position";
	    }

	    position.setCompany(companyOpt.get());
	    traineeshipPositionRepository.save(position);
	    return "redirect:/company/dashboard";
	}

    
    @GetMapping("/company/positions/available")
    public String showAvailablePositions(Model model, Principal principal) {
        String username = principal.getName();
        Company company = companyRepository.findById(username).orElse(null);
        if (company == null) {
            return "redirect:/company/dashboard";
        }
        List<TraineeshipPosition> availablePositions = traineeshipPositionRepository.findByCompanyAndIsAssigned(company, false);
        model.addAttribute("positions", availablePositions);
        return "company/positions-list";
    }

    @GetMapping("/company/positions/assigned")
    public String showAssignedPositions(Model model, Principal principal) {
        String username = principal.getName();
        Company company = companyRepository.findById(username).orElse(null);
        if (company == null) {
            return "redirect:/company/dashboard";
        }

        List<TraineeshipPosition> assignedPositions = traineeshipPositionRepository.findByCompanyAndIsAssigned(company, true);
        model.addAttribute("positions", assignedPositions);
        return "company/positions-list";
    }
    
    @PostMapping("/company/positions/delete/{id}")
    public String deletePosition(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Company company = companyRepository.findById(username).orElse(null);

        if (company != null) {
            TraineeshipPosition position = traineeshipPositionRepository.findById(id).orElse(null);
            if (position != null && position.getCompany().equals(company)) {
                traineeshipPositionRepository.delete(position);
            }
        }

        return "redirect:/company/positions/available";
    }
    
    @GetMapping("/company/positions/evaluate/{id}")
    public String showEvaluationPage(@PathVariable Long id, Model model) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid traineeship position ID"));
        model.addAttribute("positionTitle", position.getTitle());
        model.addAttribute("studentName", position.getStudent() != null ? position.getStudent().getStudentName() : "Unknown Student");
        model.addAttribute("positionId", id);
        return "company/evaluate-position";
    }

    
    @PostMapping("/company/positions/evaluate/{id}")
    public String submitEvaluation(@PathVariable Long id, 
                                    @RequestParam int motivation, 
                                    @RequestParam int efficiency, 
                                    @RequestParam int effectiveness, 
                                    Model model) {
        TraineeshipPosition position = traineeshipPositionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid traineeship position ID"));

        Evaluation existingEvaluation = evaluationRepository.findByTraineeshipPositionAndIsProffesorFalse(position);
        
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
            evaluation.setProffesor(false);
            evaluation.setTraineeshipPosition(position);
            position.getEvaluations().add(evaluation);
            evaluationRepository.save(evaluation);
            traineeshipPositionRepository.save(position);
            model.addAttribute("message", "Evaluation saved successfully!");
        }
        
        return "redirect:/company/positions/assigned";
    }
}

