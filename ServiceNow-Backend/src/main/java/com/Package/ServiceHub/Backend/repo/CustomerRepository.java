package com.Package.ServiceHub.Backend.repo;

import com.Package.ServiceHub.Backend.model.Customer;
import com.Package.ServiceHub.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser(User user); // correct

    // ❌ remove this line, it causes the error:
    // Customer findByEmail(String email); ❌ WRONG

    // ✅ Optional (if needed): add this instead
    Optional<Customer> findByUser_Email(String email);
}
