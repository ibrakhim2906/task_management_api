package com.ibrakhim2906.task_manager.integration;

import org.junit.jupiter.api.Test;

class InvalidTaskRequestExceptionTest extends HelperIntegrationTest{

    @Test
    void InvalidRequestFields() throws Exception{

        register("test@test.com", "password123");

        String token = login("test@test.com", "password123");

        createTask(token, null);

    }
}
