package com.example.montrack.Service;

import com.example.montrack.DTO.ApiResponse;
import com.example.montrack.Models.User;
import com.example.montrack.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<User> register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return new ApiResponse<>(false, "User data is invalid", null);
        }

        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (usernameExists) {
            return new ApiResponse<>(false, "Username already registerd", null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return new ApiResponse<>(true, "User registered successfully", savedUser);
        }
    }

    public ApiResponse<User> login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return new ApiResponse<>(false, "Invalid username or password", null);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ApiResponse<>(false, "Invalid username or password", null);
        }

        return new ApiResponse<>(true, "Login successful", user);
    }

    public ApiResponse<Void> logout() {
        return new ApiResponse<>(true, "Logout successful", null);
    }

    public ApiResponse<User> update(Long userId, User updatedUser) {
        if (updatedUser == null) {
            return new ApiResponse<>(false, "User data is invalid", null);
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return new ApiResponse<>(false, "User not found", null);
        }

        User existingUser = optionalUser.get();
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(existingUser.getUsername())) {
            boolean usernameExists = userRepository.findByUsername(updatedUser.getUsername()).isPresent();
            if (usernameExists) {
                return new ApiResponse<>(false, "Username already exists", null);
            }
        }

        // Update fields if provided
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setSaldo(updatedUser.getSaldo());
        existingUser.setFullname(updatedUser.getFullname());
        User savedUser = userRepository.save(existingUser);
        return new ApiResponse<>(true, "User updated successfully", savedUser);
    }
}