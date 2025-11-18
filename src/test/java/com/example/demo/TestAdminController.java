package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import traineeship_manager.controllers.AdminController;
import traineeship_manager.domainmodel.Company;
import traineeship_manager.domainmodel.Professor;
import traineeship_manager.domainmodel.Student;
import traineeship_manager.domainmodel.TraineeshipPosition;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
class TestAdminController {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AdminController admincontroller;

    private Student mockStudent;
    private Professor mockProfessor;
    private TraineeshipPosition mockPosition;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockStudent = new Student();
        mockStudent.setUsername("student1");
        mockStudent.setAM("1000");
        mockStudent.setSkills("Java,Python");
        mockStudent.setInterests("C++");
        mockStudent.setLookingForTraineeship(true);
        mockStudent.setPreferedLocation("Athens");

        mockProfessor = new Professor();
        mockProfessor.setUsername("professor1");
        mockProfessor.setInterests("AI,Databases");
        mockProfessor.setSupervisedPositions(new ArrayList<>());

        mockPosition = new TraineeshipPosition();
        mockPosition.setId(1);
        mockPosition.setTitle("Internship position 1");
        mockPosition.setIsAssigned(false);
        mockPosition.setSkills("Java");
        mockPosition.setTopics("C++");
        mockPosition.setCompany(new Company());
        mockPosition.getCompany().setCompanyLocation("Athens");
    }

    @Test
    void testAdminControllerIsNotNull() {
        assertNotNull(admincontroller);
    }

    @Test
    void testMockMvcIsNotNull() {
        assertNotNull(mockMvc);
    }

    @Test
    void testDashboardView() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"));
    }

    @Test
    void testStudentsLookingForTraineeshipView() throws Exception {
        mockMvc.perform(get("/admin/studentsLookingForTraineeship"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studentsLookingForTraineeship"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    void testAssignPositionRedirects() throws Exception {
        mockMvc.perform(post("/admin/assign")
                        .param("studentUsername", "student1")
                        .param("positionId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/studentsLookingForTraineeship"));
    }

    @Test
    void testShowAllProfessors() throws Exception {
        mockMvc.perform(get("/admin/professors"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/professors"))
                .andExpect(model().attributeExists("professors"));
    }

    @Test
    void testShowInProgressTraineeships() throws Exception {
        mockMvc.perform(get("/admin/traineeships"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/traineeships"))
                .andExpect(model().attributeExists("inProgressPositions"));
    }
}
