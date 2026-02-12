package com.ibrakhim2906.task_manager.services;

import com.ibrakhim2906.task_manager.exceptions.InvalidTaskStateException;
import com.ibrakhim2906.task_manager.models.Task;
import com.ibrakhim2906.task_manager.models.User;
import com.ibrakhim2906.task_manager.models.enums.TaskStatus;
import com.ibrakhim2906.task_manager.repositories.TaskRepository;
import com.ibrakhim2906.task_manager.repositories.UserRepository;
import com.ibrakhim2906.task_manager.security.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    // mocks
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService service;

    private User me;

    @BeforeEach
    void setUp() {
        me = new User();
        me.setEmail("test@test.com");
        me.setPasswordHash("676767");
    }

    @Test
    void add_defaultStatusToDo_statusInNull() {

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(me));

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        when(taskRepository.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        try (MockedStatic<CurrentUser> mocked = mockStatic(CurrentUser.class)) {
            mocked.when(CurrentUser::email).thenReturn("test@test.com");

            service.add("info", LocalDateTime.now().plusDays(1), null);

            Task saved = captor.getValue();
            assertThat(saved.getStatus()).isEqualTo(TaskStatus.TODO);
            assertThat(saved.getOwner()).isEqualTo(me);
            assertThat(saved.getDetails()).isEqualTo("info");
        }
    }

    @Test
    void updateStatusThrow_whenSettingSameStatus() {

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(me));

        Task existing = new Task();
        existing.setId(1L);
        existing.setOwner(me);
        existing.setStatus(TaskStatus.TODO);

        when(taskRepository.findByIdAndOwner(1L, me)).thenReturn(Optional.of(existing));


        try (MockedStatic<CurrentUser> mocked = mockStatic(CurrentUser.class)) {
            mocked.when(CurrentUser::email).thenReturn("test@test.com");

            assertThatThrownBy(() -> service.updateStatus(1L, TaskStatus.TODO))
                    .isInstanceOf(InvalidTaskStateException.class);

            verify(taskRepository, never()).save(any(Task.class));
        }
    }

}
