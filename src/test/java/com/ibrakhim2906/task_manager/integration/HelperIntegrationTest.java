package com.ibrakhim2906.task_manager.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class HelperIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    // Register HELPER
    protected void register(String email, String password) throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(email, password)))
                .andExpect(status().isCreated());
    }


    // Login HELPER
    protected String login(String email, String password) throws Exception {
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email" : "%s",
                            "password" : "%s"
                        }
                        """.formatted(email, password)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response);
        return json.get("token").asText();
    }

    // Create Task (Returning ID) HELPER
    protected Long createTask(String token, String details) throws Exception {
        String response = mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "details" : "%s"
                        }
                        """.formatted(details)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response);
        return json.get("id").asLong();
    }

    protected void createTask(String token, String details, LocalDateTime dueDate, String taskStatus) throws Exception {
        if (details==null) {
            mockMvc.perform(post("/tasks")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                {
                                    "details" : "",
                                    "dueDate" : "%s",
                                    "status" : "%s"
                                }
                                """.formatted(dueDate, taskStatus)))
                    .andExpect(status().isCreated());
        }
        mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "details" : "%s",
                                    "dueDate" : "%s",
                                    "status" : "%s"
                                }
                                """.formatted(details, dueDate, taskStatus)))
                .andExpect(status().isCreated());
    }



    // Update Task HELPER
    protected void updateTask(String token, Long taskId) throws Exception {
        mockMvc.perform(patch("/tasks/"+taskId+"/status")
                        .header("Authorization","Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "status" : "DONE"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    // Get Task HELPER
    protected void getTaskAndCheckStatus(String token, Long taskId) throws Exception {
        mockMvc.perform(get("/tasks/"+taskId)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

}
