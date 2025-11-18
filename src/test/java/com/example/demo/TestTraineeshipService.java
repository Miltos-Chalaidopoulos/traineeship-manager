package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import traineeship_manager.domainmodel.TraineeshipPosition;
import traineeship_manager.services.TraineeshipPositionService;

@TestPropertySource(
		  locations = "classpath:application.properties")
@SpringBootTest
class TestTraineeshipService {

	@Autowired 
	TraineeshipPositionService traineeshippositionservice;
	
	@Test
	void testTraineeshipIsNotNull() {
		Assertions.assertNotNull(traineeshippositionservice);
	}
	
	@Test
	void testFindByAssigned() {
		List<TraineeshipPosition> stored = traineeshippositionservice.findByIsAssigned(true);
		for(int i=0; i<stored.size();i++) {
			Assertions.assertNotNull(stored.get(i));
			Assertions.assertEquals(true, stored.get(i).getIsAssigned());
		}
	}
	
	
	@Test
	void testFindByUnassigned() {
		List<TraineeshipPosition> storedtwo = traineeshippositionservice.getUnassignedTraineeships();
		for(int i=0; i<storedtwo.size();i++) {
			Assertions.assertNotNull(storedtwo.get(i));
			Assertions.assertEquals(false, storedtwo.get(i).getIsAssigned());
			
		}
	}

}
