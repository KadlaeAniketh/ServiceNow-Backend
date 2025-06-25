package com.Package.ServiceHub.Backend.service;

import com.Package.ServiceHub.Backend.model.Customer;
import com.Package.ServiceHub.Backend.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Step 1: Register
    public String registerUser(Customer customerRequest) {
        // Check if user already exists
        Optional<Customer> existing = customerRepository.findByUser(customerRequest.getUser());
        if (existing.isPresent()) {
            return "Email already exists";
        }

        // Save new customer
        customerRepository.save(customerRequest);
        return "User registered successfully";
    }

    // Step 2: Set Role
    public String setUserRole(String email, String role) {
        Customer customer = customerRepository.findAll()
                .stream()
                .filter(c -> c.getEmail() != null && c.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (customer == null) return "User not found";

        customer.setRole(role);
        customerRepository.save(customer);
        return "Role saved successfully";
    }

    // Step 3: Complete Profile
    public String completeProfile(Customer profileData) {
        Customer customer = customerRepository.findAll()
                .stream()
                .filter(c -> c.getEmail() != null && c.getEmail().equals(profileData.getEmail()))
                .findFirst()
                .orElse(null);

        if (customer == null) return "User not found";

        // Update customer profile
        customer.setPhone(profileData.getPhone());
        customer.setStreet(profileData.getStreet());
        customer.setVillage(profileData.getVillage());
        customer.setMandal(profileData.getMandal());
        customer.setDistrict(profileData.getDistrict());
        customer.setState(profileData.getState());
        customer.setPincode(profileData.getPincode());

        customerRepository.save(customer);
        return "Profile completed successfully";
    }
}
