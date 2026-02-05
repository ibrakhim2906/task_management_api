package com.ibrakhim2906.task_manager.dtos;

import com.ibrakhim2906.task_manager.models.User;

public record RegisterResponse(Long id, String email) {
    public static RegisterResponse from(User user) {
        return new RegisterResponse(user.getId(), user.getEmail());
    }
}
