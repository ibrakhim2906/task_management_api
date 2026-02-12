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

    /**
     * Create new task for specific user (TaskStatus Defaults to TODO)
     * @param details Task description
     * @return TaskResponse containing created task information
     */
    public TaskResponse add(String details) {
        User me = requireCurrentUser();

        Task task = new Task();
        task.setDetails(details);
        task.setStatus(TaskStatus.TODO);
        task.setOwner(me);

        taskRepository.save(task);

        return TaskResponse.from(task);

    }

    /**
     * Create a task with more fields for task description filled out, user related
     * @param details Task description
     * @param dueDate Optional deadline for the task
     * @param status Initial status (default TODO)
     * @return TaskResponse with task information
     */
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

    /**
     * Get single task
     * @param id ID of the task needed
     * @return TaskResponse with needed task
     */
    public TaskResponse get(Long id) {
        User me = requireCurrentUser();

        Task task = taskRepository.findByIdAndOwner(id, me)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return TaskResponse.from(task);
    }

    /**
     * Get paged multiple tasks (filtering with status, being task overdue)
     * @param status Filtering parameter based on task
     * @param overdue Filtering parameter based on task being past deadline and still have status TODO
     * @param pageable Pagination
     * @return Page of TaskResponse - list of tasks requested
     */
    public Page<TaskResponse> getTasks(TaskStatus status, Boolean overdue, Pageable pageable) {
        User me = requireCurrentUser();

        if (Boolean.TRUE.equals(overdue)) {
            return taskRepository.findByOwnerAndStatusAndDueDateBefore(me, TaskStatus.TODO, LocalDateTime.now(), pageable).map(TaskResponse::from);
        }

        if (status!=null) {
            return taskRepository.findByOwnerAndStatus(me, status, pageable).map(TaskResponse::from);
        }

        return taskRepository.findByOwner(me, pageable).map(TaskResponse::from);

    }

    /**
     * Full replacement of the task with given information
     * @param id Path variable to access specific tasks
     * @param details Task information that should be put in
     * @param dueDate DueDate information that should be put in
     * @param taskStatus Task status information that should put in
     * @return TaskResponse with information of updated task (same id)
     */
    @Transactional
    public TaskResponse update(Long id, String details, LocalDateTime dueDate, TaskStatus taskStatus) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me).orElseThrow(() -> new TaskNotFoundException(id));

        if (dueDate!=null && dueDate.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Due date cannot be in the past");
        }

        existing.setDetails(details);
        existing.setDueDate(dueDate);
        existing.setStatus(taskStatus);


        return TaskResponse.from(existing);
    }

    /**
     * Updating status of the specific task
     * @param id Path variable to access specific task
     * @param status Desired status to be set
     * @return TaskResponse with current status for requested task
     */
    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatus status) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (existing.getStatus() == status) {
            throw new InvalidTaskStateException(id);
        }

        existing.setStatus(status);

        return TaskResponse.from(existing);
    }

    /**
     * Delete specific user task based on ID
     * @param id ID of the task to be deleted
     */
    public void delete(Long id) {
        User me = requireCurrentUser();

        Task existing = taskRepository.findByIdAndOwner(id, me)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskRepository.delete(existing);
    }
}