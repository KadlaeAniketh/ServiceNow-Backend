package com.example.ServiceNow.Backend;

import com.example.ServiceNow.Backend.model.LoginRequest;
import com.example.ServiceNow.Backend.model.Customer;
import com.example.ServiceNow.Backend.model.ServiceProvider;
import com.example.ServiceNow.Backend.model.User;
import com.example.ServiceNow.Backend.repo.CustomerRepository;
import com.example.ServiceNow.Backend.repo.ServiceProviderRepository;
import com.example.ServiceNow.Backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174")
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ServiceProviderRepository providerRepo;

    @PostMapping("/customer/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null || !"customer".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Customer not found\"}");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Invalid password\"}");
        }

        Customer customer = customerRepo.findByUser(user).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Customer profile not found\"}");
        }

        return ResponseEntity.ok("{\"message\": \"Login successful\"}");
    }

    @PostMapping("/provider/login")
    public ResponseEntity<?> loginProvider(@RequestBody LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null || !"provider".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Service Provider not found\"}");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Invalid password\"}");
        }

        ServiceProvider provider = providerRepo.findByUser(user).orElse(null);
        if (provider == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Provider profile not found\"}");
        }

        return ResponseEntity.ok("{\"message\": \"Login successful\"}");
    }
}
