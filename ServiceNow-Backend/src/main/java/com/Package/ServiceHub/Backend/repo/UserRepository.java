package com.Package.ServiceHub.Backend.repo;

import com.Package.ServiceHub.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Fetch user by email
    User findByEmail(String email);

    // Check if a user exists by email
    boolean existsByEmail(String email);
}
