package com.ibrakhim2906.task_manager.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MissingJwtTokenExceptionTest extends HelperIntegrationTest {
    @Test
    void jwtTokenIsNull() throws Exception {
        mockMvc.perform(get("/tasks")
                .header("Authorization", "Bearer  "))
                .andExpect(status().isForbidden());
    }
}
