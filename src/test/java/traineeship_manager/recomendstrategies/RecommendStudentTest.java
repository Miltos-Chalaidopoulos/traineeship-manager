package traineeship_manager.recomendstrategies;

import org.junit.jupiter.api.Test;
import traineeship_manager.domainmodel.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RecommendStudentTest {

	@Test
	public void testFilterBySkills_basicMatch() {
	    Student student = new Student();
	    student.setSkills("Driving, Maths, Science");

	    TraineeshipPosition pos1 = new TraineeshipPosition();
	    pos1.setSkills("driving,maths,science");

	    TraineeshipPosition pos2 = new TraineeshipPosition();
	    pos2.setSkills("driving,maths,science,cooking");

	    TraineeshipPosition pos3 = new TraineeshipPosition();
	    pos3.setSkills("driving");

	    List<TraineeshipPosition> positions = List.of(pos1, pos2, pos3);

	    List<TraineeshipPosition> result = RecommendStudent.filterBySkills(student, positions);

	    assertEquals(2, result.size());
	    assertTrue(result.contains(pos1));
	    assertTrue(result.contains(pos3));
	    assertFalse(result.contains(pos2));
	}

	@Test
	public void testFilterBySkills_studentHasNoSkills() {
	    Student student = new Student();
	    student.setSkills("");

	    TraineeshipPosition pos = new TraineeshipPosition();
	    pos.setSkills("java");

	    List<TraineeshipPosition> positions = List.of(pos);

	    List<TraineeshipPosition> result = RecommendStudent.filterBySkills(student, positions);

	    assertTrue(result.isEmpty());
	}

	@Test
	public void testFilterBySkills_emptyPositionList() {
	    Student student = new Student();
	    student.setSkills("java");

	    List<TraineeshipPosition> positions = new ArrayList<>();

	    List<TraineeshipPosition> result = RecommendStudent.filterBySkills(student, positions);

	    assertTrue(result.isEmpty());
	}

	@Test
	public void testFilterBySkills_caseInsensitive() {
	    Student student = new Student();
	    student.setSkills("Java,Spring");

	    TraineeshipPosition pos = new TraineeshipPosition();
	    pos.setSkills("spring,java");

	    List<TraineeshipPosition> result = RecommendStudent.filterBySkills(student, List.of(pos));

	    assertEquals(1, result.size());
	    assertTrue(result.contains(pos));
	}

	@Test
	public void testFilterBySkills_exactMatchOnly() {
	    Student student = new Student();
	    student.setSkills("java");

	    TraineeshipPosition pos = new TraineeshipPosition();
	    pos.setSkills("java, spring");

	    List<TraineeshipPosition> result = RecommendStudent.filterBySkills(student, List.of(pos));

	    assertTrue(result.isEmpty());
	}
	
	@Test
	public void testMatchOnlyInterest() {
	    Student student = new Student();
	    student.setInterests("chess, space, diving");

	    Company company = new Company();
	    company.setCompanyLocation("Athens");

	    TraineeshipPosition position = new TraineeshipPosition();
	    position.setTopics("diving, robotics");
	    position.setCompany(company);

	    int score = RecommendStudent.evaluateMatchScore(student, position);
	    assertEquals(1, score);
	}

	@Test
	public void testMatchOnlyLocation() {
	    Student student = new Student();
	    student.setInterests("basketball, physics");
	    student.setPreferedLocation("Thessaloniki");

	    Company company = new Company();
	    company.setCompanyLocation("thessaloniki");

	    TraineeshipPosition position = new TraineeshipPosition();
	    position.setTopics("art, music");
	    position.setCompany(company);

	    int score = RecommendStudent.evaluateMatchScore(student, position);
	    assertEquals(2, score);
	}

	@Test
	public void testMatchBoth() {
	    Student student = new Student();
	    student.setInterests("space, chess, sports");
	    student.setPreferedLocation("Arta");

	    Company company = new Company();
	    company.setCompanyLocation("arta");

	    TraineeshipPosition position = new TraineeshipPosition();
	    position.setTopics("chess, diving");
	    position.setCompany(company);

	    int score = RecommendStudent.evaluateMatchScore(student, position);
	    assertEquals(3, score);
	}

	@Test
	public void testNoMatch() {
	    Student student = new Student();
	    student.setInterests("basketball, history");
	    student.setPreferedLocation("Patras");

	    Company company = new Company();
	    company.setCompanyLocation("Athens");

	    TraineeshipPosition position = new TraineeshipPosition();
	    position.setTopics("physics, chemistry");
	    position.setCompany(company);

	    int score = RecommendStudent.evaluateMatchScore(student, position);
	    assertEquals(0, score);
	}

	@Test
	public void testNullCompany() {
	    Student student = new Student();
	    student.setInterests("maths, computers");
	    student.setPreferedLocation("Patras");

	    TraineeshipPosition position = new TraineeshipPosition();
	    position.setTopics("computers, AI");
	    position.setCompany(null);

	    int score = RecommendStudent.evaluateMatchScore(student, position);
	    assertEquals(1, score);
	}
	
	@Test
	public void testRecommendByStrategyOrderingAndFiltering() {
	    Student student = new Student();
	    student.setSkills("Driving,Maths,Science");
	    student.setInterests("chess,ai,sports");
	    student.setPreferedLocation("Athens");

	    Company company1 = new Company();
	    company1.setCompanyLocation("Athens");

	    Company company2 = new Company();
	    company2.setCompanyLocation("Thessaloniki");

	    Company company3 = new Company();
	    company3.setCompanyLocation("Athens");

	    TraineeshipPosition p1 = new TraineeshipPosition();
	    p1.setSkills("driving,maths,science");
	    p1.setTopics("chess,ethics");
	    p1.setCompany(company1);

	    TraineeshipPosition p2 = new TraineeshipPosition();
	    p2.setSkills("driving,maths,science");
	    p2.setTopics("sports");
	    p2.setCompany(company2);

	    TraineeshipPosition p3 = new TraineeshipPosition();
	    p3.setSkills("driving,maths,science");
	    p3.setTopics("economy");
	    p3.setCompany(company3);

	    TraineeshipPosition p4 = new TraineeshipPosition();
	    p4.setSkills("biology");
	    p4.setTopics("ai");
	    p4.setCompany(company1);

	    List<TraineeshipPosition> positions = Arrays.asList(p1, p2, p3, p4);

	    List<TraineeshipPosition> recommended = RecommendStudent.recommendByStrategy(student, positions);

	    assertEquals(3, recommended.size());
	    assertEquals(p1, recommended.get(0));
	    assertEquals(p3, recommended.get(1));
	    assertEquals(p2, recommended.get(2));
	}


}