package com.projectmanagementsystem.registrationservice.service;

import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface RegistrationService extends UserDetailsService {
    public UserDetailsDTO userSignup(UserDetailsDTO userDetailsDTO);
    public UserDetailsDTO getUserDetailsByEmailId(String emailId);
    public List<UserDetailsDTO> getAllUsers();
    public List<UserDetailsDTO> getUsersForProject(List<String> projectIds);
}
