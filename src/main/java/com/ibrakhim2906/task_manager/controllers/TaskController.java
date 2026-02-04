package com.ibrakhim2906.task_manager.controllers;

import com.ibrakhim2906.task_manager.dtos.TaskRequest;
import com.ibrakhim2906.task_manager.dtos.TaskResponse;
import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService=taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse add(@Valid @RequestBody TaskRequest req) {
        return taskService.add(req.details());
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable Long id) {
        return taskService.get(id);
    }

    @GetMapping
    public Collection<TaskResponse> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/completed")
    public Collection<TaskResponse> getCompletedTasks() {
        return taskService.getCompletedTasks();
    }

    @GetMapping("/overdue")
    public Collection<TaskResponse> overdue() {
        return taskService.getOverdueTasks();
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest req) {
        return taskService.update(id, req.details());
    }

    @PutMapping("/{id}/complete")
    public TaskResponse updateStatus(@PathVariable Long id) {
        return taskService.updateStatus(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
