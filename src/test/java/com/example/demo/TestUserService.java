package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;




import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import traineeship_manager.domainmodel.User;
import traineeship_manager.services.UserService;

@TestPropertySource(
		  locations = "classpath:application.properties")
@SpringBootTest
class TestUserService {

	@Autowired 
	UserService userservice;
	
	
	
	
	
	@Test
	void testUserServiceIsNotNull() {
		Assertions.assertNotNull(userservice);
	}
	
	@Test
	void testUserIsPresent() {
		
		User usee = new User();
		boolean a = userservice.isUserPresent(usee);
		Assertions.assertEquals(false, a);
	}

}
