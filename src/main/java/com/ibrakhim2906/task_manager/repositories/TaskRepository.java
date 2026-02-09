package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.models.User;
import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndOwner(Long id, User owner);

    Page<Task> findByOwner(User user, Pageable pageable);

    Page<Task> findByOwnerAndStatus(User user, TaskStatus status, Pageable pageable);

    Page<Task> findByOwnerAndStatusNotAndDueDateBefore(User user,TaskStatus status, LocalDateTime date, Pageable pageable);
}
