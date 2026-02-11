package com.ibrakhim2906.task_manager.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskSecurityIntegrationTest extends HelperIntegrationTest {
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
}
