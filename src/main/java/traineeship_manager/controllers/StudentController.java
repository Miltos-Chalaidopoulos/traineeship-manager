package traineeship_manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import traineeship_manager.domainmodel.Student;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import traineeship_manager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import traineeship_manager.repository.TraineeshipPositionRepository;
import traineeship_manager.domainmodel.TraineeshipPosition;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class StudentController {
	@Autowired
    private StudentRepository studentRepository;
	
	@Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;
	
    @GetMapping("/user/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }
    
    @GetMapping("/user/profile")
    public String userProfile(Model model, Principal principal) {
        String username = principal.getName();
        Student student = studentRepository.findByUsername(username);

        if (student == null) {
            student = new Student();
            student.setUsername(username);
        }

        model.addAttribute("profile", student);
        return "user/profile";
    }
    
    @PostMapping("/user/save")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "user/dashboard";
    }
    
    @GetMapping("/user/positions/available")
    public String showAvailablePositions(Model model) {
        List<TraineeshipPosition> availablePositions = traineeshipPositionRepository.findByIsAssigned(false);
        model.addAttribute("positions", availablePositions);
        return "user/available-positions";
    }
    
    @GetMapping("user/logbook")
    public String viewLogBook(Authentication authentication, Model model) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username);

        if (student != null) {
            TraineeshipPosition position = student.getAssignedForTrainship();
            model.addAttribute("position", position);

            if (position != null && position.getStudentLogBook() != null) {
                List<String> entries = Arrays.asList(position.getStudentLogBook().split("\n"));
                model.addAttribute("logEntries", entries);
            }
        }

        return "user/view-logbook";
    }

    @PostMapping("/user/logbook")
    public String submitLogBook(@RequestParam("logBookContent") String content, Authentication authentication) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username);

        if (student != null && student.getAssignedForTrainship() != null) {
            TraineeshipPosition position = student.getAssignedForTrainship();
            String previousLog = position.getStudentLogBook();

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String entry = timestamp + " - " + content;

            String updatedLog = (previousLog != null && !previousLog.isEmpty())
                    ? previousLog + "\n\n" + entry
                    : entry;

            position.setStudentLogBook(updatedLog);
            traineeshipPositionRepository.save(position);
        }

        return "redirect:/user/logbook";
    }
    
    @PostMapping("/user/logbook/clear")
    public String clearLastEntry(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username);

        if (student != null && student.getAssignedForTrainship() != null) {
            TraineeshipPosition position = student.getAssignedForTrainship();
            String currentLog = position.getStudentLogBook();

            if (currentLog != null && !currentLog.isEmpty()) {
                String[] logEntries = currentLog.split("\n\n");
                if (logEntries.length > 0) {
                    String newLog = String.join("\n\n", Arrays.copyOf(logEntries, logEntries.length - 1));
                    position.setStudentLogBook(newLog);
                    traineeshipPositionRepository.save(position);
                }
            }
        }

        return "redirect:/user/logbook";
    }
    
    @PostMapping("/user/logbook/clearAll")
    public String clearAllEntries(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username);

        if (student != null && student.getAssignedForTrainship() != null) {
            TraineeshipPosition position = student.getAssignedForTrainship();
            position.setStudentLogBook(null);
            traineeshipPositionRepository.save(position);
        }

        return "redirect:/user/logbook";
    }
}
