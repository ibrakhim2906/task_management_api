package com.ibrakhim2906.task_manager.controllers;

import com.ibrakhim2906.task_manager.dtos.TaskRequest;
import com.ibrakhim2906.task_manager.dtos.TaskResponse;
import com.ibrakhim2906.task_manager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return taskService.add(req.details(), req.dueDate());
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable Long id) {
        return taskService.get(id);
    }

    @GetMapping
    public Page<TaskResponse> getAll(@RequestParam(required = false) Boolean completed,
                                     @RequestParam(required = false) Boolean overdue,
                                     @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return taskService.getTasks(completed, overdue, pageable);
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
