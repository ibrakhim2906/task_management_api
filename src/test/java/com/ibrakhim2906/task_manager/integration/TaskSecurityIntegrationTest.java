package com.ibrakhim2906.task_manager.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskSecurityIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void userCannotAccessOthersTask() throws Exception {
        register("user1@test.com", "password123");
        String token1 = login("user1@test.com", "password123");

        Long taskId = createTask(token1, "info");

        register("user2@test.com", "password123");
        String token2 = login("user2@test.com", "password123");

        mockMvc.perform(get("/tasks/"+taskId)
                .header("Authorization", "Bearer "+token2))
                .andExpect(status().isNotFound());

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






}
