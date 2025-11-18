package traineeship_manager.recomendstrategies;

import traineeship_manager.domainmodel.*;

import java.util.ArrayList;
import java.util.List;

public class RecommendProfessor {

    static public int calculateScore(Professor professor, TraineeshipPosition position) {
        int score = 10;

        List<String> professorInterests = StringUtil.parseStringToList(professor.getInterests());
        List<String> positionTopics = StringUtil.parseStringToList(position.getTopics());

        if (StringUtil.hasOverlap(professorInterests, positionTopics)) {
            score += 3;
        }

        int load = professor.getSupervisedPositions().size();
        score -= load;

        return Math.max(score, 0);
    }
    
    static public List<TraineeshipPosition> recommendByStrategy(Professor professor, List<TraineeshipPosition> allPositions) {
        List<TraineeshipPosition> sortedPositions = new ArrayList<>(allPositions);

        sortedPositions.sort((p1, p2) -> {
            int score1 = calculateScore(professor, p1);
            int score2 = calculateScore(professor, p2);
            return Integer.compare(score2, score1);
        });

        return sortedPositions;
    }
}
