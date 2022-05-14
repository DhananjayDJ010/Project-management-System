package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.model.*;

import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
	public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails, String projectIds);

	public List<ApiResponse> addUserStories(List<Integer> listOfIds,int sprintId);

	public ApiResponse updateUserStory(int id,
			UserStoryModel userStory);

	public ApiResponse createSubTask(int id, SubTaskModel subTask);

	public ApiResponse updateSubTask(int userStoryId, int id,
			SubTaskModel subTask);

	public List<ProjectDetailsModel> getAllDetails(String userId);

	public ProjectDataModel createProject(String userId, ProjectModel projectModel);

	public List<ProjectDataModel> getProjectsManaged(String managerId);

	public ApiResponse addSprint(SprintModel sprint);

	public ApiResponse updateSprint(SprintModel sprint, int id);

	List<SearchResponseModel> searchForDetails(Optional<Integer> id,
			Optional<String> name, String flag);

	public List<SearchResponseModel> searchForUsers(int projectId,
			Optional<Integer> id, Optional<String> name);

	public ApiResponse deleteSubTask(int id);

	public List<UserStoryModel> getUserStoryBySprint(int sprintId);

	public List<SprintResponseModel> getAllSprintDetails(String projectId);

}
