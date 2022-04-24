package com.projectmanagementsystem.userservice.service;

import com.projectmanagementsystem.userservice.client.ProjectServiceClient;
import com.projectmanagementsystem.userservice.client.RegistrationServiceClient;
import com.projectmanagementsystem.userservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, Notification> kafkaTemplate;
    private static final String KAFKA_TOPIC = "NotificationTopic";

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, ProjectServiceClient projectServiceClient, RegistrationServiceClient registrationServiceClient,
                           KafkaTemplate<String, Notification> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.projectServiceClient = projectServiceClient;
        this.registrationServiceClient = registrationServiceClient;
        this.kafkaTemplate = kafkaTemplate;
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

        ProjectDataModel project = projectServiceClient.createProject(authentication.getName(), projectModel, token);

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

    @Override
    public Notification sendNotification(Notification notification){
        kafkaTemplate.send(KAFKA_TOPIC, notification);
        return notification;
    }

    @Override
    public List<UserDetailsResponseModel> manageProjects(ProjectAccessRequestModel projectAccessRequestModel, String token, String projectIds, String createProject) {
        return registrationServiceClient.manageProjectAccess(projectAccessRequestModel, token, projectIds, "false");
    }

    @Override
    public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails, String projectIds, String token) {
        return projectServiceClient.createUserStory(userStoryDetails, token, projectIds);
    }

    @Override
    public List<ApiResponse> addUserStories(String sprintId, List<Integer> listOfIds, String token, String projectIds) {
        return projectServiceClient.addUserStory(sprintId, listOfIds, token, projectIds);
    }

    @Override
    public ApiResponse updateUserStory(int id, UserStoryModel userStory, String token, String projectIds) {
        return projectServiceClient.updateUserStory(id, userStory, token, projectIds);
    }

    @Override
    public ApiResponse createSubTask(int id, SubTaskModel subTask, String token, String projectIds) {
        return projectServiceClient.createSubTask(id, subTask, token, projectIds);
    }

    @Override
    public ApiResponse updateSubTask(int userStoryId, int id, SubTaskModel subTask, String token, String projectIds) {
        return projectServiceClient.updateSubtask(userStoryId, id, subTask, token, projectIds);
    }

    @Override
    public List<ProjectDetailsModel> getAllDetails(int userId, String token) {
        return projectServiceClient.getAllDetails(userId, token);
    }

    @Override
    public List<ProjectDataModel> getProjectsManaged(String managerId, String token) {
        return projectServiceClient.getProjectsManaged(managerId, token);
    }
}
