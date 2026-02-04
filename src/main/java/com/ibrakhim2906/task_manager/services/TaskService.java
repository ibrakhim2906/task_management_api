package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.dtos.TaskResponse;
import com.ibrakhim2906.task_manager.exceptions.InvalidTaskStateException;
import com.ibrakhim2906.task_manager.exceptions.TaskNotFoundException;
import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse add(String details) {
        Task task = new Task();
        task.setDetails(details);
        task.setCompleted(false);
        taskRepository.save(task);

        return TaskResponse.from(task);
    }

    public TaskResponse add(String details, LocalDateTime dueDate) {
        Task task = new Task();
        task.setDetails(details);
        task.setDueDate(dueDate);
        task.setCompleted(false);
        taskRepository.save(task);
        return TaskResponse.from(task);
    }

    public TaskResponse get(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskResponse.from(task);
    }
    public Collection<TaskResponse> getAll() {
        Collection<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskResponse::from).toList();
    }

    public Page<TaskResponse> getTasks(Boolean completed, Boolean overdue, Pageable pageable) {
        if (Boolean.TRUE.equals(overdue)) {
            return taskRepository.findByCompletedFalseAndDueDateBefore(LocalDateTime.now(), pageable).map(TaskResponse::from);
        }

        if (completed!=null) {
            return taskRepository.findByCompleted(completed, pageable).map(TaskResponse::from);
        }

        return taskRepository.findAll(pageable).map(TaskResponse::from);


    }

    private TaskRepository getTaskRepository() {
        return taskRepository;
    }

    @Transactional
    public TaskResponse update(Long id, String details) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        existing.setDetails(details);

        return TaskResponse.from(existing);
    }

    @Transactional
    public TaskResponse updateStatus(Long id) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (existing.isCompleted()) {
            throw new InvalidTaskStateException(id);
        }

        existing.setCompleted(true);

        return TaskResponse.from(existing);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}