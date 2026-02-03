package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findByCompleted(boolean completed);
    Collection<Task> findByDueDateBefore(LocalDateTime date);
}
