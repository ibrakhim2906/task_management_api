package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByCompleted(boolean completed, Pageable pageable);
    Page<Task> findByCompletedFalseAndDueDateBefore(LocalDateTime date, Pageable pageable);
}
