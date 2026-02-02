package com.ibrakhim2906.task_manager.repositories;

import com.ibrakhim2906.task_manager.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.management.RuntimeErrorException;
import java.util.HashMap;
import java.util.Map;

public class TaskRepository {
    Map<Long, Task> repo;

    public TaskRepository() {
        repo = new HashMap<>();
    }

    public Task save(Task task) {
        repo.put(task.getId(), task);
        return task;
    }

    public Task get(Long id) {
        Task req = repo.get(id);
        if (req==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        return req;
    }

    public void delete(Long id) {
        if (!repo.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        repo.remove(id);
    }
}
