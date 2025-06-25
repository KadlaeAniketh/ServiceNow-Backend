package com.Package.ServiceHub.Backend.Controller;

import com.Package.ServiceHub.Backend.model.LoginRequest;
import com.Package.ServiceHub.Backend.model.User;
import com.Package.ServiceHub.Backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174") // Your frontend port
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"User not found\"}");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body("{\"message\": \"Invalid password\"}");
        }

        return ResponseEntity.ok().body(
                String.format("{\"message\": \"Login successful\", \"role\": \"%s\", \"email\": \"%s\"}",
                        user.getRole(), user.getEmail()));
    }
}
