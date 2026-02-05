package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
