package com.project.service;

import com.project.client.RegistrationServiceClient;
import com.project.dao.ProjectRepository;
import com.project.dao.SprintRepository;
import com.project.dao.SubTaskRepository;
import com.project.dao.UserStoryRepository;
import com.project.dto.*;
import com.project.exception.InvalidProjectAccessException;
import com.project.exception.SprintNotFoundException;
import com.project.model.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
	private final SprintRepository sprintRepository;
	private final RegistrationServiceClient client;
	
	public ProjectServiceImpl(UserStoryRepository userRepository,
							  ModelMapper modelmapper, ApiResponse respose, SubTaskRepository subTaskRepository,
							  ProjectRepository projectRepository, SprintRepository sprintRepository,
							  RegistrationServiceClient client) {
		super();
		this.userStoryRepository = userRepository;
		this.modelMapper = modelmapper;
		this.response=respose;
		this.subTaskRepository=subTaskRepository;
		this.projectRepository=projectRepository;
		this.sprintRepository = sprintRepository;
		this.client =client;
	}
	
	@Override
	public List<ApiResponse> createUserStory(List<UserStoryModel> userStoryDetails, String projectIds){
		 
		List<Integer> listOfIds = new ArrayList<>();
		List<ApiResponse> responseList = new ArrayList<>();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<UserStoryDTO> userStoryDTO = modelMapper.map(userStoryDetails,new TypeToken<List<UserStoryDTO>>(){}.getType());

		List<ProjectDTO> projects = projectRepository.findProjectDetailsById(Arrays.asList(projectIds.split(",")));
		if(projects.isEmpty()){
			throw new InvalidProjectAccessException("Project id is invalid");
		}
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
	public List<ApiResponse> addUserStories(List<Integer> listOfIds,int sprintId) {
		
		List<ApiResponse> responseList = new ArrayList<>();
		if(sprintId==0){
			throw new SprintNotFoundException("Sprint Id is missing for this user story");
		}
		List<Integer> list =userStoryRepository.setStatusById(Status.DEFINED, listOfIds,sprintId);
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
		subTaskDTO.setUserStoryId(id);
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
	public List<ProjectDetailsModel> getAllDetails(String userId) {
		List<String> listOfProjectIds = client.getUser(userId);

		List<ProjectDetailsModel>allDetails = projectRepository.getAllDetails(listOfProjectIds);
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
	public List<ProjectDataModel> getProjectsManaged(String managerId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ProjectDTO> projectDTO = projectRepository.findByManagerId(managerId);
		return projectDTO.stream().map(project -> modelMapper.map(project, ProjectDataModel.class)).collect(Collectors.toList());
	}
	@Override
	public ApiResponse addSprint(SprintModel sprint){
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SprintDTO sprintDTO = modelMapper.map(sprint,SprintDTO.class);

		SprintDTO dto = sprintRepository.save(sprintDTO);

		response.setId(dto.getId());
		response.setStatus(HttpStatus.CREATED.name());
		log.info("Sprint created with id : {} " , dto.getId());
		return  response;


	}
   @Override
	public ApiResponse updateSprint(SprintModel sprint, int id){
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SprintDTO sprintDTO = modelMapper.map(sprint,SprintDTO.class);

		Optional<SprintDTO> dto = sprintRepository.findById(id);

		if(dto.isPresent()){
			if( sprintDTO.getProjectId()!=null)
				dto.get().setProjectId(sprintDTO.getProjectId());

			if(null != sprintDTO.getDuration())
				dto.get().setDuration(sprintDTO.getDuration());

			if(null != sprintDTO.getStartDate())
				dto.get().setStartDate(sprintDTO.getStartDate());

			if(null!= sprintDTO.getEndDate())
				dto.get().setEndDate(sprintDTO.getEndDate());

			dto.get().setSprintActive(sprintDTO.isSprintActive());

			SprintDTO sprintDTO1 = sprintRepository.save(dto.get());

			response.setId(sprintDTO1.getId());
			response.setStatus("updated");

		}

		return response;
	}

	
@Override
 public List<SearchResponseModel> searchForDetails(Optional<Integer> id,Optional<String> name, String flag){
	 SearchResponseModel model = new SearchResponseModel();
	 List<SearchResponseModel> searchList = new ArrayList<>();
	 
	 if(id.isPresent()){
		 if(flag.equalsIgnoreCase("project")){
			 
			List<Map<String,Object>> searchResults = projectRepository.searchById(id.get());
			
			for( Map<String,Object> s:searchResults){
				model.setId((int) s.get("id"));
				model.setName((String) s.get("name"));
				
				searchList.add(model);
			}
		 }else if(flag.equalsIgnoreCase("sprint")){
				 
					List<Map<String,Object>> searchResults = sprintRepository.searchById(id.get());
					
					for( Map<String,Object> s:searchResults){
						model.setId((int) s.get("id"));
						model.setName((String) s.get("name"));
						
						searchList.add(model);
					}
		}else if(flag.equalsIgnoreCase("userStory")){
				 
				List<Map<String,Object>> searchResults = userStoryRepository.searchById(id.get());
				
				for( Map<String,Object> s:searchResults){
					model.setId((int) s.get("id"));
					model.setName((String) s.get("name"));
					
					searchList.add(model);
				}
		}else {
			 
			List<Map<String,Object>> searchResults = projectRepository.searchById(id.get());
			
			for( Map<String,Object> s:searchResults){
				model.setId((int) s.get("id"));
				model.setName((String) s.get("name"));
				
				searchList.add(model);
			}
		 }
	}else{
		
		if(flag.equalsIgnoreCase("project")){
			 
			List<Map<String,Object>> searchResults = projectRepository.searchByName(name.get());
			
			for( Map<String,Object> s:searchResults){
				model.setId((int) s.get("id"));
				model.setName((String) s.get("name"));
				
				searchList.add(model);
			}
		 }else if(flag.equalsIgnoreCase("sprint")){
				 
					List<Map<String,Object>> searchResults = sprintRepository.searchByName(name.get());
					
					for( Map<String,Object> s:searchResults){
						model.setId((int) s.get("id"));
						model.setName((String) s.get("name"));
						
						searchList.add(model);
					}
		}else if(flag.equalsIgnoreCase("userStory")){
				 
				List<Map<String,Object>> searchResults = userStoryRepository.searchByName(name.get());
				
				for( Map<String,Object> s:searchResults){
					model.setId((int) s.get("id"));
					model.setName((String) s.get("name"));
					
					searchList.add(model);
				}
		}else {
			 
			List<Map<String,Object>> searchResults = projectRepository.searchByName(name.get());
			
			for( Map<String,Object> s:searchResults){
				model.setId((int) s.get("id"));
				model.setName((String) s.get("name"));
				
				searchList.add(model);
			}
		 }
			
		}
	 return searchList;
		 }

@Override
public List<SearchResponseModel> searchForUsers(int projectId,
		Optional<Integer> id, Optional<String> name) {

	List<SearchResponseModel> searchList = new ArrayList<>();
	
	StringBuilder sb = new StringBuilder();
	sb.append("select u.user_id,concat(u.first_name,' ',u.last_name) as name from project_user u inner join"
			+ "project_role p pn u.user_id = p.user_id");
	if(id.isPresent()){
		sb.append("where p.project_id="+projectId +" and u.user_id="+id.get());
	}else{
		sb.append("where p.project_id="+projectId +" and (u.first_name="+name.get() +"||"+" u.last_name="+name.get());
	}
	
	//rest call to reg service
	return null;
}

@Transactional
@Override
public ApiResponse deleteSubTask(int id) {
	subTaskRepository.deleteById(id);
	response.setId(id);
	response.setStatus("deleted");
	
	return response;
}
	 
 }

