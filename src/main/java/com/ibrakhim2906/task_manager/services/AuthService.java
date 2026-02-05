package com.ibrakhim2906.task_manager.services;


import com.ibrakhim2906.task_manager.dtos.RegisterResponse;
import com.ibrakhim2906.task_manager.models.User;
import com.ibrakhim2906.task_manager.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    public RegisterResponse register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        String passwordHash = passwordEncoder.encode(password);
        User user = new User(email, passwordHash);
        userRepository.save(user);

        return RegisterResponse.from(user);
    }

}
