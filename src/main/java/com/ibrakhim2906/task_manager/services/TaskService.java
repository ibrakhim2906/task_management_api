package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.dtos.TaskResponse;
import com.ibrakhim2906.task_manager.exceptions.InvalidTaskStateException;
import com.ibrakhim2906.task_manager.exceptions.TaskNotFoundException;
import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.models.User;
import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import com.ibrakhim2906.task_manager.repositories.UserRepository;
import com.ibrakhim2906.task_manager.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // HELPER Getting Current User

    private User requireCurrentUser() {
        String email = CurrentUser.email();
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    //

    public TaskResponse add(String details) {
        User me = requireCurrentUser();

        Task task = new Task();
        task.setDetails(details);
        task.setOwner(me);

        taskRepository.save(task);

        return TaskResponse.from(task);

    }

    public TaskResponse add(String details, LocalDateTime dueDate, TaskStatus status) {
        User me = requireCurrentUser();


        Task task = new Task();
        task.setDetails(details);
        task.setOwner(me);

        if (dueDate != null) task.setDueDate(dueDate);
        if (status != null) task.setStatus(status);
        else task.setStatus(TaskStatus.TODO);

        taskRepository.save(task);
        return TaskResponse.from(task);
    }

    public TaskResponse get(Long id) {
        User me = requireCurrentUser();

        Task task = taskRepository.findByIdAndOwner(id, me)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return TaskResponse.from(task);
    }

    public Page<TaskResponse> getTasks(TaskStatus status, Boolean overdue, Pageable pageable) {
        User me = requireCurrentUser();

        if (Boolean.TRUE.equals(overdue)) {
            return taskRepository.findByOwnerAndStatusNotAndDueDateBefore(me,TaskStatus.DONE,LocalDateTime.now(), pageable).map(TaskResponse::from);
        }

        if (status!=null) {
            return taskRepository.findByOwnerAndStatus(me, status, pageable).map(TaskResponse::from);
        }

        return taskRepository.findByOwner(me, pageable).map(TaskResponse::from);


    }

    @Transactional
    public TaskResponse update(Long id, String details, LocalDateTime dueDate, TaskStatus taskStatus) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me).orElseThrow(() -> new TaskNotFoundException(id));

        if (dueDate.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Due date cannot be in the past");
        }

        existing.setDetails(details);
        existing.setDueDate(dueDate);
        existing.setStatus(taskStatus);


        return TaskResponse.from(existing);
    }

    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatus status) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me).orElseThrow(() -> new TaskNotFoundException(id));

        if (existing.getStatus() == status) {
            throw new InvalidTaskStateException(id);
        }

        existing.setStatus(status);

        return TaskResponse.from(existing);
    }

    public void delete(Long id) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskRepository.delete(existing);
    }
}