package com.projectmanagementsystem.userservice.service;

import com.projectmanagementsystem.userservice.client.ProjectServiceClient;
import com.projectmanagementsystem.userservice.client.RegistrationServiceClient;
import com.projectmanagementsystem.userservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
@Slf4j
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final ProjectServiceClient projectServiceClient;
    private final RegistrationServiceClient registrationServiceClient;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, ProjectServiceClient projectServiceClient, RegistrationServiceClient registrationServiceClient) {
        this.modelMapper = modelMapper;
        this.projectServiceClient = projectServiceClient;
        this.registrationServiceClient = registrationServiceClient;
    }

    @Override
    @Transactional
    public CreateProjectResponseModel createProject(ProjectModel projectModel, String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ProjectAccessRequestModel mainRequest = new ProjectAccessRequestModel();
        List<ProjectRoleModel> mainRoleSubpart = new ArrayList<>();
        List<ProjectAccessRequest> access = new ArrayList<>();
        ProjectAccessRequest projectAccessRequest = new ProjectAccessRequest();
        ProjectRoleModel projectRoleModel = new ProjectRoleModel();

        ProjectDataModel project = projectServiceClient.createProject(authentication.getName(), projectModel);

        projectRoleModel.setProjectId(project.getProjectId());
        projectRoleModel.setCollaborationRole(CollaborationRole.PROJECT_MANAGER);
        mainRoleSubpart.add(projectRoleModel);
        projectAccessRequest.setProjectRoles(mainRoleSubpart);
        projectAccessRequest.setUserId(authentication.getName());
        access.add(projectAccessRequest);
        mainRequest.setProjectAccessRequests(access);

        List<UserDetailsResponseModel> response = registrationServiceClient
                .manageProjectAccess(mainRequest, token, project.getProjectId(), "true");
        CreateProjectResponseModel finalResponse = new CreateProjectResponseModel();
        finalResponse.setProjectData(project);
        finalResponse.setUserDetailsResponseModels(response);

        return finalResponse;
    }
}
