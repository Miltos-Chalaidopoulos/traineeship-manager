package traineeship_manager.recomendstrategies;

import traineeship_manager.domainmodel.*;
import java.util.List;
import java.util.ArrayList;

public class RecommendStudent {
	
	public static List<TraineeshipPosition> filterBySkills(Student student, List<TraineeshipPosition> positions) {
	    List<String> studentSkills = StringUtil.parseStringToList(student.getSkills());

	    List<TraineeshipPosition> matchingPositions = new ArrayList<>();

	    for (TraineeshipPosition position : positions) {
	        List<String> requiredSkills = StringUtil.parseStringToList(position.getSkills());

	        if (StringUtil.isSubset(requiredSkills, studentSkills)) {
	            matchingPositions.add(position);
	        }
	    }

	    return matchingPositions;
	}
	
	
	public static int evaluateMatchScore(Student student, TraineeshipPosition position) {
	    int score = 0;
	    List<String> studentInterests = StringUtil.parseStringToList(student.getInterests());
	    List<String> positionTopics = StringUtil.parseStringToList(position.getTopics());
	    for (String interest : studentInterests) {
	        if (positionTopics.contains(interest)) {
	            score += 1;
	        }
	    }
	    String studentLocation = student.getPreferedLocation();
	    String companyLocation = position.getCompany() != null ? position.getCompany().getCompanyLocation() : null;
	    if (studentLocation != null && companyLocation != null &&
	            studentLocation.trim().equalsIgnoreCase(companyLocation.trim())) {
	        score += 2;
	    }
	    return score;
	}
	
	public static List<TraineeshipPosition> recommendByStrategy(Student student, List<TraineeshipPosition> allPositions) {
		List<TraineeshipPosition> filteredPositions = filterBySkills(student, allPositions);
		filteredPositions.sort((p1, p2) -> {
		    int score1 = evaluateMatchScore(student, p1);
		    int score2 = evaluateMatchScore(student, p2);
		    return Integer.compare(score2, score1);
		});

		return filteredPositions;
	}
}
