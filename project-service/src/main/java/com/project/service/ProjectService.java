package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.model.*;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
	public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails);

	public List<ApiResponse> addUserStories(List<Integer> listOfIds);

	public ApiResponse updateUserStory(int id,
			UserStoryModel userStory);

	public ApiResponse createSubTask(int id, SubTaskModel subTask);

	public ApiResponse updateSubTask(int userStoryId, int id,
			SubTaskModel subTask);

	public Optional<List<ProjectModel>> getProjectDetails(int userId);

	public List<ProjectDetailsModel> getAllDetails(int userId);

	public ProjectDataModel createProject(String userId, ProjectModel projectModel);

	public String addUsersToProject(int projectId, List<Integer> listOfUsers);

	public List<ProjectDataModel> getProjectsManaged(String managerId);
}
