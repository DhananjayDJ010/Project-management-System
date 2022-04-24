package com.projectmanagementsystem.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsResponseModel {
    private String emailId;
    private String firstName;
    private String lastName;
    private String userId;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private List<ProjectRoleModel> projectRoles;
}
