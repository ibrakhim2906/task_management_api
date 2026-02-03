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
        Task task = taskService.add(req.details());
        return TaskResponse.from(task);
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable Long id) {
        Task task = taskService.get(id);
        return TaskResponse.from(task);
    }

    @GetMapping
    public Collection<TaskResponse> getAll() {
        Collection<Task> tasks = taskService.getAll();
        return tasks.stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/completed")
    public Collection<TaskResponse> getCompletedTasks() {
        return taskService.getCompletedTasks().stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/overdue")
    public Collection<TaskResponse> overdue() {
        return taskService.getOverdueTasks().stream().map(TaskResponse::from).toList();
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest req) {
        Task task = taskService.update(id, req.details());
        return TaskResponse.from(task);
    }

    @PutMapping("/{id}/complete")
    public TaskResponse updateStatus(@PathVariable Long id) {
        return TaskResponse.from(taskService.updateStatus(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {taskService.delete(id);
    }
}
