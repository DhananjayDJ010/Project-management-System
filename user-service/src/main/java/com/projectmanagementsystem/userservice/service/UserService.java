package com.projectmanagementsystem.userservice.service;

import com.projectmanagementsystem.userservice.model.CreateProjectResponseModel;
import com.projectmanagementsystem.userservice.model.ProjectModel;

public interface UserService {
    public CreateProjectResponseModel createProject(ProjectModel projectModel, String token);
}
