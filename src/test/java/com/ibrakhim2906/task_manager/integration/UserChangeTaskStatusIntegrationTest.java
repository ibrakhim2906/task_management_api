package com.ibrakhim2906.task_manager.integration;

import org.junit.jupiter.api.Test;

class UserChangeTaskStatusIntegrationTest extends HelperIntegrationTest {

    @Test
    void UserCanChangeTaskStatus() throws Exception {
        register("change_status@test.com", "password123");
        String token = login("change_status@test.com", "password123");

        Long taskId = createTask(token, "info");
        updateTask(token, taskId);

        getTaskAndCheckStatus(token, taskId);
    }
}
