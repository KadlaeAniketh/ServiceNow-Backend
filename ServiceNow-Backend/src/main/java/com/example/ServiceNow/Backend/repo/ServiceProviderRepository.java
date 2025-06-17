package com.example.ServiceNow.Backend.repo;

import com.example.ServiceNow.Backend.model.ServiceProvider;
import com.example.ServiceNow.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    boolean existsByUser(User user); // optional, for validations

    Optional<ServiceProvider> findByUser(User user);
}
