package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.exceptions.InvalidTaskStateException;
import com.ibrakhim2906.task_manager.exceptions.TaskNotFoundException;
import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task add(String details) {
        Task task = new Task();
        task.setDetails(details);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(false);

        return taskRepository.save(task);
    }
    public Task get(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
    public Collection<Task> getAll() {
        return taskRepository.findAll();
    }

    public Collection<Task> getCompletedTasks() {
        return taskRepository.findByCompleted(true);
    }

    public Collection<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDateTime.now());
    }

    @Transactional
    public Task update(Long id, String details) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        existing.setDetails(details);
        return existing;
    }

    @Transactional
    public Task updateStatus(Long id) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (existing.isCompleted()) {
            throw new InvalidTaskStateException(id);
        }

        existing.setStatus(true);

        return existing;
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
    }
}