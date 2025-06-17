package com.example.ServiceNow.Backend.service;

import com.example.ServiceNow.Backend.model.ServiceProvider;
import com.example.ServiceNow.Backend.model.User;
import com.example.ServiceNow.Backend.repo.ServiceProviderRepository;
import com.example.ServiceNow.Backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceProviderService {

    @Autowired
    private ServiceProviderRepository providerRepo;

    @Autowired
    private UserRepository userRepo;

    // Step 1: Register user (basic details)
    public String register(ServiceProvider provider) {
        User user = provider.getUser();
        if (user == null || user.getEmail() == null) {
            return "Invalid user data";
        }

        if (!userRepo.existsByEmail(user.getEmail())) {
            return "User must be registered first";
        }

        if (providerRepo.existsByUser(user)) {
            return "Email already exists!";
        }

        providerRepo.save(provider);
        return "Registered successfully";
    }

    // Step 2: Save role
    public String saveRole(String email, String role) {
        User user = userRepo.findByEmail(email);
        if (user == null) return "User not found";

        Optional<ServiceProvider> optionalProvider = providerRepo.findByUser(user);
        if (optionalProvider.isEmpty()) return "Provider not found";

        ServiceProvider provider = optionalProvider.get();
        user.setRole(role);
        provider.setUser(user);
        providerRepo.save(provider); // Will cascade save user
        return "Role saved successfully";
    }

    // Step 3: Save profile details
    public String saveProfile(ServiceProvider profileData) {
        User user = userRepo.findByEmail(profileData.getUser().getEmail());
        if (user == null) return "User not found";

        ServiceProvider provider = providerRepo.findByUser(user).orElse(new ServiceProvider());
        provider.setUser(user);

        provider.setPhone(profileData.getPhone());
        provider.setStreet(profileData.getStreet());
        provider.setVillage(profileData.getVillage());
        provider.setMandal(profileData.getMandal());
        provider.setDistrict(profileData.getDistrict());
        provider.setState(profileData.getState());
        provider.setPincode(profileData.getPincode());

        if ("provider".equalsIgnoreCase(user.getRole())) {
            provider.setServices(profileData.getServices());
        } else if ("customer".equalsIgnoreCase(user.getRole())) {
            provider.setPreferences(profileData.getPreferences());
        }

        providerRepo.save(provider);
        return "Profile completed successfully";
    }

    public ServiceProvider getById(Long id) {
        return providerRepo.findById(id).orElse(null);
    }
}
