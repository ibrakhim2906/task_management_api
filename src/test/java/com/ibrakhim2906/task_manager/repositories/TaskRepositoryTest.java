package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.models.User;
import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByOwnerAndStatus_returnsOnlyMatchingStatus() {

        // Create User
        User u = new User();
        u.setEmail("test@test.com");
        u.setPasswordHash("67676767");
        userRepository.save(u);

        // Task 1: TODO
        Task t1 = new Task();
        t1.setOwner(u);
        t1.setDetails("todo task");
        t1.setStatus(TaskStatus.TODO);
        taskRepository.save(t1);

        // Task 2: DONE
        Task t2 = new Task();
        t2.setOwner(u);
        t2.setDetails("done task");
        t2.setStatus(TaskStatus.DONE);
        taskRepository.save(t2);

        var page = taskRepository.findByOwnerAndStatus(u, TaskStatus.TODO, PageRequest.of(0,10));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getDetails()).isEqualTo("todo task");
        assertThat(page.getContent().get(0).getStatus()).isEqualTo(TaskStatus.TODO);
    }
}
