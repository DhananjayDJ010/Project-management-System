package com.projectmanagementsystem.registrationservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectmanagementsystem.registrationservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class ProjectUser {
    @Email
    private String emailId;
    @EmbeddedId
    private ProjectUserKey userId;
    private String encryptedPassword;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectUser")
    private List<ProjectRole> projectRoles = new ArrayList<>();
}
