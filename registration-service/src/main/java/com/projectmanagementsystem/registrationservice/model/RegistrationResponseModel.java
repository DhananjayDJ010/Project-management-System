package com.projectmanagementsystem.registrationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationResponseModel {
    private String emailId;
    private String firstName;
    private String lastName;
    private String userId;
    private String encryptedPassword;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
