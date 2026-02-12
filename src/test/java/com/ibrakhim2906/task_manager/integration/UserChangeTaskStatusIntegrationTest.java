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

class UserChangeTaskStatusIntegrationTest extends HelperIntegrationTest{

    @Test
    void UserCanChangeTaskStatus() throws Exception {

        register("test@test.com", "password123");
        String token = login("test@test.com", "password123");

        Long taskId = createTask(token, "info");
        updateTask(token, taskId);

        getTaskAndCheckStatus(token, taskId);
    }
}
