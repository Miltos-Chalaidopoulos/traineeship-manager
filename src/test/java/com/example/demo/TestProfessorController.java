package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import java.security.Principal;
import traineeship_manager.controllers.ProfessorController;
import traineeship_manager.domainmodel.Professor;
import traineeship_manager.repository.ProfessorRepository;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestProfessorController {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ProfessorController procontroller;
	
	@MockBean
    private ProfessorRepository professorRepository;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testProfessorControllerIsNotNull() {
		Assertions.assertNotNull(procontroller);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	@Test 
	void testShowBoard() throws Exception {
		mockMvc.perform(get("/professor/dashboard")).
		andExpect(status().isOk()). 
		andExpect(view().name("professor/dashboard")); 		
	}
	
	
	
	
	@Test 
	void testSaveProfessor() throws Exception {
		
		Principal mockPrincipal = () -> "prof1";

        when(professorRepository.save(any(Professor.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/professor/save")
                .with(csrf())
                .with(request -> {
                    request.setUserPrincipal(mockPrincipal);
                    return request;
                })
                .param("professorName", "Uncle Bob")
                .param("interests", "Sotfwere Engineering, Softwere Development"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/professor/dashboard"));

        verify(professorRepository, times(1)).save(any(Professor.class));
    }
	
	

}
