package com.Package.ServiceHub.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String street;
    private String village;
    private String mandal;
    private String district;
    private String state;
    private String pincode;

    private String verificationFilePath;

    @ElementCollection
    private List<String> services;

    @ElementCollection
    private List<String> preferences;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Convenience Getters
    public String getEmail() {
        return user != null ? user.getEmail() : null;
    }

    public String getFirstName() {
        return user != null ? user.getFirstName() : null;
    }

    public String getLastName() {
        return user != null ? user.getLastName() : null;
    }

    public String getRole() {
        return user != null ? user.getRole() : null;
    }

    // Convenience Setters
    public void setEmail(String email) {
        if (user != null) user.setEmail(email);
    }

    public void setFirstName(String firstName) {
        if (user != null) user.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        if (user != null) user.setLastName(lastName);
    }

    public void setRole(String role) {
        if (user != null) user.setRole(role);
    }

    public void setPassword(String password) {
        if (user != null) user.setPassword(password);
    }
}
