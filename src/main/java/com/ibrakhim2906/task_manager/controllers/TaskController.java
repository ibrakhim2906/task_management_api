package com.ibrakhim2906.task_manager.controllers;

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
    public Task add(@Valid @RequestBody Task task) {
        return taskService.add(task);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task get(@PathVariable Long id) {
       return taskService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Task> getAll() {
        return taskService.getAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }



}
