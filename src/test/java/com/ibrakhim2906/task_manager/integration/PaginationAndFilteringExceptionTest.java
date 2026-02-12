package com.ibrakhim2906.task_manager.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaginationAndFilteringExceptionTest extends HelperIntegrationTest{

    @Test
    void paginationReturnsCorrectPageSize() throws Exception {

        // User logic
        register("test@test.com", "password123");
        String token = login("test@test.com", "password123");

        // Create 12 tasks
        for (int i=1; i<=12; i++) {
            createTask(token, "task-"+i, LocalDateTime.now().plusDays(10), "TODO");
        }

        // (page 0 size 5 -> 2)
        mockMvc.perform(get("/tasks")
                        .header("Authorization", "Bearer "+token)
                .param("page", "0")
                .param("size", "5")
                .param("sort", "createdAt,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(12))
                .andExpect(jsonPath("$.totalPages").value(3));

    }

    @Test
    void filteringReturnCorrectStatusTasks() throws Exception {

        // User logic
        register("test@test.com", "password123");
        String token = login("test@test.com", "password123");

        for (int i=1; i<=6; i++) {
            createTask(token, "task-"+i, LocalDateTime.now().plusDays(10), "TODO");
        }

        for (int i=7; i<=12;i++) {
            createTask(token, "task-"+i, LocalDateTime.now().plusDays(10), "DONE");
        }

        mockMvc.perform(get("/tasks")
                .param("status", "DONE")
                .header("Authorization", "Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(6))
                .andExpect(jsonPath("$.content[*].status").
                        value(Matchers.everyItem(Matchers.is("DONE"))));
    }

    @Test
    void filteringReturnCorrectOverdueTasks() throws Exception {

        // User logic
        register("test@test.com", "password123");
        String token = login("test@test.com", "password123");

        createTask(token, "task1", LocalDateTime.now().minusDays(2), "TODO");
        createTask(token, "task2", LocalDateTime.now().minusDays(2), "TODO");
        createTask(token, "task3", LocalDateTime.now().plusDays(2), "TODO");

        mockMvc.perform(get("/tasks")
                .param("overdue" , "true")
                .header("Authorization", "Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(2));
    }
}
