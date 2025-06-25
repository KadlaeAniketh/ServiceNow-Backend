package com.Package.ServiceHub.Backend.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    private String email;
    private String phone;
    private String street;
    private String village;
    private String mandal;
    private String district;
    private String state;
    private String pincode;

    private List<String> preferences; // for customer

    private String businessName; // for provider
    private List<String> services;
    private String experience;
    private String availability;
    private String pricing;
}