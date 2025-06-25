package com.Package.ServiceHub.Backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Convenience Getters from User
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

    // Setters that propagate to User
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

    public String getAddress() {
        return String.format(
                "%s, %s, %s, %s, %s, %s",
                street != null ? street : "",
                village != null ? village : "",
                mandal != null ? mandal : "",
                district != null ? district : "",
                state != null ? state : "",
                pincode != null ? pincode : ""
        ).replaceAll(",\\s*,", ",").trim();
    }
}
