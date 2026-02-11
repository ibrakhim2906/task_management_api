package com.ibrakhim2906.task_manager.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InvalidJwtTokenExceptionTest extends HelperIntegrationTest{
    @Test
    void jwtTokenIsIncorrect() throws Exception {
        mockMvc.perform(get("/tasks")
                        .header("Authorization", "Bearer "+"abc.cde.123"))
                .andExpect(status().isForbidden());
    }
}
