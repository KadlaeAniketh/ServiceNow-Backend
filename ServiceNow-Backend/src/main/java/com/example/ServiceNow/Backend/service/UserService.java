package com.example.ServiceNow.Backend.service;

import com.example.ServiceNow.Backend.model.User;
import com.example.ServiceNow.Backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Register new user
    public User registerUser(User user) {
        // Optional: check if user already exists
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }
        return userRepo.save(user);
    }

    // Find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    // Assign role to a user
    public void assignRole(String email, String role) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setRole(role);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
}
