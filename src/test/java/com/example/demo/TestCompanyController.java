package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import traineeship_manager.controllers.CompanyController;
import traineeship_manager.domainmodel.Company;
import traineeship_manager.repository.CompanyRepository;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestCompanyController {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CompanyController compcontroller;
	
	@MockBean
	private CompanyRepository companyRepository;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		Company company = new Company();
	    company.setUsername("company1");
	    company.setCompanyLocation("Ioannina");
	    company.setName("company1");
	    when(companyRepository.findById("company1")).thenReturn(Optional.of(company));
    }
	
	@Test
	void testCompanyControllerIsNotNull() {
		Assertions.assertNotNull(compcontroller);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	@Test 
	void testShowBoard() throws Exception {
		mockMvc.perform(get("/company/dashboard")).
		andExpect(status().isOk()). 
		andExpect(view().name("company/dashboard")); 		
	}
	
	
	
	
	@Test 
	void testSaveCompany() throws Exception {
		mockMvc.perform(post("/company/save")
		.param("company","company1"))
				
			
		.andExpect(status().isOk())
		.andExpect(view().name("company/dashboard"));
		
		
	}
	@Test 
	void testAddPosition() throws Exception {
		mockMvc.perform(get("/company/addposition")).
		andExpect(status().isOk()). 
		andExpect(view().name("company/add-position"));
		
		
	}
}
