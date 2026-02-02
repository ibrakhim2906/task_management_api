package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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
    public Task add(@RequestBody Task task) {
        task.setId(idCounter++);
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
    public Map<Long, Task> getAll() {
        return taskRepository.getAll();
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
