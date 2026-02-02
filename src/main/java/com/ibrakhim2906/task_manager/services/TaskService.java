package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private Long idCounter = 1L;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task add(Task task) {
        task.setId(idCounter++);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(false);

        taskRepository.save(task);
        return task;
    }

    public Task get(Long id) {
        return taskRepository.get(id);
    }

    public Collection<Task> getAll() {
        return taskRepository.getAll().values();
    }

    public Task update(Long id, Task task) {
        return taskRepository.update(id, task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
