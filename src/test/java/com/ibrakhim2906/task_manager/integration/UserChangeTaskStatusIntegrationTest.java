package com.ibrakhim2906.task_manager.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserChangeTaskStatusIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void UserCanChangeTaskStatus() throws Exception {

        register("test@test.com", "password123");
        String token = login("test@test.com", "password123");

        Long taskId = createTask(token, "info");
        updateTask(token, taskId);

        getTaskAndCheckStatus(token, taskId);

    }

    // Register HELPER
    private void register(String email, String password) throws Exception {
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
    private String login(String email, String password) throws Exception {
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
    private Long createTask(String token, String details) throws Exception {
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

    // Update Task HELPER
    private void updateTask(String token, Long taskId) throws Exception {
        mockMvc.perform(put("/tasks/"+taskId+"/status/set")
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
    private void getTaskAndCheckStatus(String token, Long taskId) throws Exception {
        mockMvc.perform(get("/tasks/"+taskId)
                .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }


}
