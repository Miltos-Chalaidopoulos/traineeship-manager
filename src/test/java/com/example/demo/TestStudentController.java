package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import traineeship_manager.controllers.StudentController;
import traineeship_manager.domainmodel.Student;
import traineeship_manager.domainmodel.TraineeshipPosition;
import traineeship_manager.repository.StudentRepository;
import traineeship_manager.repository.TraineeshipPositionRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class TestStudentController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Autowired
    private WebApplicationContext context;

    private Student mockStudent;
    private TraineeshipPosition mockPosition;

    @BeforeEach
    void setup() {
        mockStudent = new Student();
        mockStudent.setUsername("student1");

        mockPosition = new TraineeshipPosition();
        mockPosition.setStudentLogBook("Entry 1\nEntry 2");
        mockStudent.setAssignedForTrainship(mockPosition);

        Mockito.when(studentRepository.findByUsername("student1")).thenReturn(mockStudent);
    }

    @Test
    void testStudentControllerIsNotNull() {
        assertNotNull(context.getBean(StudentController.class));
    }

    @Test
    void testMockMvcIsNotNull() {
        assertNotNull(mockMvc);
    }

    @WithMockUser(username = "student1", authorities = "STUDENT")
    @Test 
    void testUserSavePage() throws Exception {  
        mockMvc.perform(post("/user/save")
                .param("username", "student1")
                .param("studentName", "John")
                .param("avgGrade", "8.5")
                .param("preferedLocation", "Athens")
                .param("interests", "coding")
                .param("skills", "java")
                .param("lookingForTraineeship", "true")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("user/dashboard"));
    }

    @WithMockUser(username = "student1", authorities = "STUDENT")
    @Test 
    void testShowPosition() throws Exception {
        mockMvc.perform(get("/user/positions/available"))
            .andExpect(status().isOk()) 
            .andExpect(view().name("user/available-positions"));
    }

    @WithMockUser(username = "student1", authorities = "STUDENT")
    @Test 
    void testShowBook() throws Exception {
        mockMvc.perform(get("/user/logbook"))
            .andExpect(status().isOk()) 
            .andExpect(view().name("user/view-logbook"))
            .andExpect(model().attributeExists("position"))
            .andExpect(model().attribute("logEntries", Arrays.asList("Entry 1", "Entry 2")));
    }

    @WithMockUser(username = "student1", authorities = "STUDENT")
    @Test
    void testClearAllBook() throws Exception {
        mockMvc.perform(post("/user/logbook/clearAll").with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/logbook"));

        Mockito.verify(traineeshipPositionRepository).save(Mockito.argThat(pos -> pos.getStudentLogBook() == null));
    }
}
