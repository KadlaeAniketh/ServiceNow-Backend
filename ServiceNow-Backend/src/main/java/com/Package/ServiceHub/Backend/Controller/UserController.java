package com.Package.ServiceHub.Backend.Controller;

import com.Package.ServiceHub.Backend.dto.UserProfileDTO;
import com.Package.ServiceHub.Backend.model.*;
import com.Package.ServiceHub.Backend.repo.CustomerRepository;
import com.Package.ServiceHub.Backend.repo.ServiceProviderRepository;
import com.Package.ServiceHub.Backend.repo.UserRepository;
import com.Package.ServiceHub.Backend.model.*;
import com.Package.ServiceHub.Backend.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/role")
    public ResponseEntity<?> updateUserRole(@RequestBody RoleRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        user.setRole(request.getRole());
        userRepository.save(user);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PostMapping("/profile")
    @Transactional
    public ResponseEntity<?> completeUserProfile(@RequestBody UserProfileRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String role = user.getRole();
        if (role == null) {
            return ResponseEntity.badRequest().body("Role not set for user");
        }

        if (role.equalsIgnoreCase("customer")) {
            Customer customer = customerRepository.findByUser(user).orElse(new Customer());
            customer.setUser(user);
            customer.setPhone(request.getPhone());
            customer.setStreet(request.getStreet());
            customer.setVillage(request.getVillage());
            customer.setMandal(request.getMandal());
            customer.setDistrict(request.getDistrict());
            customer.setState(request.getState());
            customer.setPincode(request.getPincode());

            customerRepository.save(customer);
            return ResponseEntity.ok("Customer profile saved successfully");
        }

        if (role.equalsIgnoreCase("provider")) {
            ServiceProvider provider = serviceProviderRepository.findByUser(user).orElse(new ServiceProvider());
            provider.setUser(user);
            provider.setPhone(request.getPhone());
            provider.setStreet(request.getStreet());
            provider.setVillage(request.getVillage());
            provider.setMandal(request.getMandal());
            provider.setDistrict(request.getDistrict());
            provider.setState(request.getState());
            provider.setPincode(request.getPincode());
            provider.setServices(request.getServices());

            serviceProviderRepository.save(provider);
            return ResponseEntity.ok("Provider profile saved successfully");
        }

        return ResponseEntity.badRequest().body("Invalid role");
    }

    // ✅ NEW: Get user profile without lazy loading issue
    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getUserProfile(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        UserProfileDTO profileDTO = new UserProfileDTO(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );

        return ResponseEntity.ok(profileDTO);
    }
}
