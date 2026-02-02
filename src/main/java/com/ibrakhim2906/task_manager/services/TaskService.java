package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskService {

    private TaskRepository taskRepository;
    private Long idCounter = 1L;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task add(@Valid @RequestBody Task task) {
        task.setId(idCounter++);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(false);

        taskRepository.save(task);
        return task;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task get(@PathVariable Long id) {
        return taskRepository.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Task> getAll() {
        return taskRepository.getAll().values();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskRepository.update(id, task);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        taskRepository.delete(id);
    }







}
