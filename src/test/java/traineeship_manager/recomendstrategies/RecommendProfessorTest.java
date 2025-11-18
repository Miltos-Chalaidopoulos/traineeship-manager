package traineeship_manager.recomendstrategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import traineeship_manager.domainmodel.Professor;
import traineeship_manager.domainmodel.TraineeshipPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RecommendProfessorTest {

    private RecommendProfessor recommendProfessor;
    private Professor professor;
    private TraineeshipPosition position;

    @BeforeEach
    public void setUp() {
        recommendProfessor = new RecommendProfessor();
        professor = mock(Professor.class);
        position = mock(TraineeshipPosition.class);
    }

    @Test
    public void testCalculateScore_WithMatchingInterestAndTopic() {
        when(professor.getInterests()).thenReturn("AI, Machine Learning, Data Science");
        when(position.getTopics()).thenReturn("AI, Data Science, Blockchain");

        TraineeshipPosition mockPosition1 = mock(TraineeshipPosition.class);
        TraineeshipPosition mockPosition2 = mock(TraineeshipPosition.class);
        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList(mockPosition1, mockPosition2));

        int score = recommendProfessor.calculateScore(professor, position);
        assertEquals(11, score);
    }

    @Test
    public void testCalculateScore_WithNoMatchingInterestOrTopic() {
        when(professor.getInterests()).thenReturn("AI, Machine Learning");
        when(position.getTopics()).thenReturn("Blockchain, Cloud Computing");

        TraineeshipPosition mockPosition = mock(TraineeshipPosition.class);
        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList(mockPosition));

        int score = recommendProfessor.calculateScore(professor, position);
        assertEquals(9, score);
    }

    @Test
    public void testCalculateScore_WithMatchingInterestButNoMatchingTopic() {
        when(professor.getInterests()).thenReturn("AI, Machine Learning, Data Science");
        when(position.getTopics()).thenReturn("Blockchain, Cloud Computing");

        TraineeshipPosition mockPosition = mock(TraineeshipPosition.class);
        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList(mockPosition));

        int score = recommendProfessor.calculateScore(professor, position);
        assertEquals(9, score);
    }

    @Test
    public void testCalculateScore_WithNoMatchingInterestAndTopicAndNoSupervisedPositions() {
        when(professor.getInterests()).thenReturn("AI, Data Science");
        when(position.getTopics()).thenReturn("Blockchain, Cloud Computing");

        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList());

        int score = recommendProfessor.calculateScore(professor, position);
        assertEquals(10, score);
    }

    @Test
    public void testCalculateScore_WithMultipleSupervisedPositions() {
        when(professor.getInterests()).thenReturn("AI, Machine Learning");
        when(position.getTopics()).thenReturn("AI, Data Science");

        TraineeshipPosition mockPosition1 = mock(TraineeshipPosition.class);
        TraineeshipPosition mockPosition2 = mock(TraineeshipPosition.class);
        TraineeshipPosition mockPosition3 = mock(TraineeshipPosition.class);
        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList(mockPosition1, mockPosition2, mockPosition3));

        int score = recommendProfessor.calculateScore(professor, position);
        assertEquals(10, score);
    }

    @Test
    public void testRecommendByStrategy_ReturnsSortedPositions() {
        when(professor.getInterests()).thenReturn("AI, Machine Learning");

        TraineeshipPosition pos1 = mock(TraineeshipPosition.class);
        when(pos1.getTopics()).thenReturn("AI");

        TraineeshipPosition pos2 = mock(TraineeshipPosition.class);
        when(pos2.getTopics()).thenReturn("Blockchain");

        TraineeshipPosition pos3 = mock(TraineeshipPosition.class);
        when(pos3.getTopics()).thenReturn("Machine Learning");

        when(professor.getSupervisedPositions()).thenReturn(Arrays.asList());

        List<TraineeshipPosition> all = Arrays.asList(pos2, pos1, pos3);

        List<TraineeshipPosition> result = recommendProfessor.recommendByStrategy(professor, all);

        assertEquals(pos1, result.get(0));
        assertEquals(pos3, result.get(1));
        assertEquals(pos2, result.get(2));
    }
}