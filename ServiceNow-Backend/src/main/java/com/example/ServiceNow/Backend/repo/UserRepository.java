package com.example.ServiceNow.Backend.repo;

import com.example.ServiceNow.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Fetch user by email
    User findByEmail(String email);

    // Check if a user exists by email
    boolean existsByEmail(String email);
}
