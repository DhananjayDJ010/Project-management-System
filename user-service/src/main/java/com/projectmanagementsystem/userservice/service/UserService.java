package com.projectmanagementsystem.userservice.service;

import com.projectmanagementsystem.userservice.model.*;

import java.util.List;

public interface UserService {
    public CreateProjectResponseModel createProject(ProjectModel projectModel, String token);
    public Notification sendNotification(Notification notification);
    public List<UserDetailsResponseModel> manageProjects(ProjectAccessRequestModel projectAccessRequestModel, String token,
                                                        String projectIds, String createProject);
    public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails, String projectIds, String token);

    public List<ApiResponse> addUserStories(String sprintId, List<Integer> listOfIds, String token, String projectIds);

    public ApiResponse updateUserStory(int id,
                                       UserStoryModel userStory, String token, String projectIds);

    public ApiResponse createSubTask(int id, SubTaskModel subTask, String token, String projectIds);

    public ApiResponse updateSubTask(int userStoryId, int id,
                                     SubTaskModel subTask, String token, String projectIds);

    public List<ProjectDetailsModel> getAllDetails(int userId, String token);

    public List<ProjectDataModel> getProjectsManaged(String managerId, String token);

}
