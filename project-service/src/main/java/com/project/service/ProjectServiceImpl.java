package com.project.service;

import com.project.dao.ProjectRepository;
import com.project.dao.SubTaskRepository;
import com.project.dao.UserStoryRepository;
import com.project.dto.ProjectDTO;
import com.project.dto.Status;
import com.project.dto.SubTaskDTO;
import com.project.dto.UserStoryDTO;
import com.project.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private static final Logger log =  LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	private final UserStoryRepository userStoryRepository;
	private final SubTaskRepository subTaskRepository;
	private final ProjectRepository projectRepository;
	private final ModelMapper modelMapper;
	private final ApiResponse response;
	
	public ProjectServiceImpl(UserStoryRepository userRepository,
			ModelMapper modelmapper,ApiResponse respose,SubTaskRepository subTaskRepository,
			ProjectRepository projectRepository) {
		super();
		this.userStoryRepository = userRepository;
		this.modelMapper = modelmapper;
		this.response=respose;
		this.subTaskRepository=subTaskRepository;
		this.projectRepository=projectRepository;
	}
	
	@Override
	public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails){
		 
		List<Integer> listOfIds = new ArrayList<>();
		List<ApiResponse> responseList = new ArrayList<>();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<UserStoryDTO> userStoryDTO = modelMapper.map(userStoryDetails,new TypeToken<List<UserStoryDTO>>(){}.getType());
		
		for(UserStoryDTO userStory :userStoryDTO){
			userStory.setBacklog(true);
			userStory.setStatus(Status.NEW);
		}
		List<UserStoryDTO> list = userStoryRepository.saveAll(userStoryDTO);
		
		for(UserStoryDTO userStory:list){
			listOfIds.add(userStory.getId());
		}
		
		for(int id:listOfIds){
			response.setId(id);
			response.setStatus("created");
			responseList.add(response);
		}
		
		return responseList;
	}

	@Override
	public List<ApiResponse> addUserStories(List<Integer> listOfIds) {
		
		List<ApiResponse> responseList = new ArrayList<>();
		List<Integer> list =userStoryRepository.setStatusById(Status.DEFINED, listOfIds);
		for(int id :list){
			response.setId(id);
			response.setStatus("added");
			responseList.add(response);
		}
		return responseList;
	}

	@Override
	public ApiResponse updateUserStory(int id,
			UserStoryModel userStory) {
		System.out.println(userStory.getStatus());
		Optional<UserStoryDTO> userStory1 = userStoryRepository.findById(id);
		System.out.println("after dao call");
		if(userStory1.isPresent()){
			if(userStory.getAssignedUser()!=null && !userStory.getAssignedUser().isEmpty()){
				userStory1.get().setAssignedUser(userStory.getAssignedUser());
			}
			if(userStory.getStoryPoints()!=0){
				userStory1.get().setStoryPoints(userStory.getStoryPoints());
			}
			if(userStory.getStatus()!=null){
				userStory1.get().setStatus(userStory.getStatus());
			}
			if(userStory.getEstimatedEfforts()!=0){
				userStory1.get().setEstimatedEfforts(userStory.getEstimatedEfforts());
			}
			if(userStory.getConsumedEfforts()!=0){
				userStory1.get().setConsumedEfforts(userStory.getConsumedEfforts());
			}
			if(userStory.getRemainingEfforts()!=0){
				userStory1.get().setRemainingEfforts(userStory.getRemainingEfforts());
			}
			
			userStoryRepository.save(userStory1.get());
			
			response.setId(id);
			response.setStatus("updated");
		}
	
		return response;
	}

	@Override
	public ApiResponse createSubTask(int id, SubTaskModel subTask) {
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SubTaskDTO subTaskDTO = modelMapper.map(subTask,SubTaskDTO.class);
		
		SubTaskDTO dto= subTaskRepository.save(subTaskDTO);
		
		response.setId(dto.getId());
		response.setStatus("created");
		return response;
	}

	@Override
	public ApiResponse updateSubTask(int userStoryId, int id,
			SubTaskModel subTask) {
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SubTaskDTO subTaskDTO = modelMapper.map(subTask,SubTaskDTO.class);
		
		Optional<SubTaskDTO> subTaskDTO1 = subTaskRepository.findById(id);
		
		if(subTaskDTO1.isPresent()){
			if(subTaskDTO.getAssignedUser()!=null && !subTaskDTO.getAssignedUser().isEmpty()){
				subTaskDTO1.get().setAssignedUser(subTaskDTO.getAssignedUser());
			}
			if(subTaskDTO.getEstimatedEfforts()!=0){
				subTaskDTO1.get().setEstimatedEfforts(subTaskDTO.getEstimatedEfforts());
			}
			if(subTaskDTO.getConsumedEfforts()!=0){
				subTaskDTO1.get().setConsumedEfforts(subTaskDTO.getConsumedEfforts());
			}
			if(subTaskDTO.getRemainingEfforts()!=0){
				subTaskDTO1.get().setConsumedEfforts(subTaskDTO.getConsumedEfforts());
			}
			
			SubTaskDTO dto = subTaskRepository.save(subTaskDTO1.get());
			
			response.setId(dto.getId());
			response.setStatus("created");
			
		}
		
		return response;
	}

	@Override
	public Optional<List<ProjectModel> >getProjectDetails(int userId) {
		
		//rest template call to registration service to fetch project ids corresponding to logged in user
		List<ProjectModel> projectsDetails1= new ArrayList<>();
		List<Integer> projectIds = new ArrayList<>();
		Optional<List<Integer>>list= Optional.of(projectIds);
		if(list.isPresent()){
		List<ProjectDTO>projectsDetails = projectRepository.findAllById(projectIds);
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		 projectsDetails1 =modelMapper.map(projectsDetails,new TypeToken<List<ProjectModel>>(){}.getType());
		 
		 return Optional.of(projectsDetails1);
		}else{
		return Optional.empty();
		}
	}

	@Override
	public List<ProjectDetailsModel> getAllDetails(int userId) {
		
		List<ProjectDetailsModel>allDetails = projectRepository.getAllDetails();
		/*ProjectDetailsModel projectDetailsModel = new ProjectDetailsModel();
		
		if(allDetails!=null && !allDetails.isEmpty()){
			for(Map<String,Object> details :allDetails){
				projectDetailsModel.setProject_id((int) details.get("project_id"));
				projectDetailsModel.setProject_name((String) details.get("project_name"));
				projectDetailsModel.setProject_type((String) details.get("project_type"));
				projectDetailsModel.setProject_owner((String) details.get("project_owner"));
				projectDetailsModel.setSprint_id((int) details.get("sprint_id"));
				projectDetailsModel.setSprint_name((String) details.get("sprint_name"));
				projectDetailsModel.setStartDate((String) details.get("start_date"));
				projectDetailsModel.setEnd_date((String) details.get("end_date"));
				projectDetailsModel.setDuration((String) details.get("duration"));
				projectDetailsModel.setSprintActive((boolean)details.get("isSprintActive"));
				projectDetailsModel.setUser_story_id((int) details.get("user_story_id"));
				projectDetailsModel.setUser_story_name((String) details.get("user_story_name"));
				projectDetailsModel.setUser_story_assignedUser((String) details.get("user_story_assignedUeser"));
				projectDetailsModel.setUser_story_status((String) details.get("user_story_status"));
				projectDetailsModel.setEstimated_efforts((int) details.get("estimated_efforts"));
				
			}
		}*/
		return allDetails;
	}

	@Override
	public ProjectDataModel createProject(String userId,ProjectModel projectModel) {
		projectModel.setManagerId(userId);
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ProjectDTO projectDTO = modelMapper.map(projectModel,ProjectDTO.class);

		ProjectDTO projectDTO1 = projectRepository.save(projectDTO);
		
		return modelMapper.map(projectDTO1, ProjectDataModel.class);
	}

	@Override
	public String addUsersToProject(int projectId, List<Integer> listOfUsers) {
	//rest call to reg service to update project id in users table
		
		return "Users are added to project";
	}

	@Override
	public List<ProjectDataModel> getProjectsManaged(String managerId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ProjectDTO> projectDTO = projectRepository.findByManagerId(managerId);
		return projectDTO.stream().map(project -> modelMapper.map(project, ProjectDataModel.class)).collect(Collectors.toList());
	}

}
