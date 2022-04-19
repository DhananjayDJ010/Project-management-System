package com.projectmanagementsystem.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectAccessRequest {
    private String userId;
    private List<ProjectRoleModel> projectRoles;
}
