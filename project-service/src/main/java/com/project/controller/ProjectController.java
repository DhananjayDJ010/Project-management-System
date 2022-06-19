package com.project.controller;

//import org.apache.logging.log4j.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.project.dto.User;
import com.project.service.ProjectService;

@RestController
@RequestMapping("/api/v1.0/project-tracker")
public class ProjectController {

	private final ProjectService service;
	
	public ProjectController(ProjectService service) {
		super();
		this.service = service;
	}

	private static final Logger log =  LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private KafkaTemplate<String,User> kafkaTemplate;
	
	private static final String TOPIC ="my-first-kafka-topic";
	
	private static final String TOPIC1 ="my-second-kafka-topic";


	@GetMapping("/project/hello")
	public ResponseEntity<WarmUpModel> healthCheck(){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		WarmUpModel warmUpModel = new WarmUpModel("Hello from project service");
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(warmUpModel);
	}

	@GetMapping("/publish/{message}")
	public String post (@PathVariable("message") final String message, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){

		kafkaTemplate.send(TOPIC,new User(message,"tech"));
		
		if(message.startsWith("b")){
			kafkaTemplate.send(TOPIC1,new User(message,"tech"));
		}
		log.info("Test Successfully");
		return "Publish Successfully";
		
	}
	
	@PostMapping("/manager/{managerId}/create-project")
	public ResponseEntity<ProjectDataModel> createProject(@PathVariable("managerId") String userId, @RequestBody ProjectModel projectModel,
														  @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ProjectDataModel response = service.createProject(userId,projectModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);
	}
	
	@PostMapping("/create/user-stories")
	public ResponseEntity<List<ApiResponse>> createUserStory(@RequestBody List<UserStoryModel> userStoryDetails,
															 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
															 @RequestHeader("projectIds") String projectIds){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		List<ApiResponse> response = service.createUserStory(userStoryDetails, projectIds);
		
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);
	}
	
	@PostMapping("/add/sprint/{sprintId}/user-stories")
	public ResponseEntity<List<ApiResponse>> addUserStory(@PathVariable("sprintId")int sprintId,
														  @RequestBody String listOfIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
														  @RequestHeader("projectIds") String projectId){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		String formattedIds = listOfIds.replace("\"","");
		List<Integer> userStoryIds = (Arrays.stream(formattedIds.split(",")).map(id -> Integer.parseInt(id)).collect(Collectors.toList()));
		List<ApiResponse> response = service.addUserStories(userStoryIds,sprintId);
		
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(response);
		
	}
	
	@PutMapping("/update/user-story/{id}")
	public ResponseEntity<ApiResponse> updateUserStory(@PathVariable("id") int id, @RequestBody UserStoryModel userStory,
													   @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ApiResponse response = service.updateUserStory(id,userStory);
		
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(response);
		
	}
	
	@PostMapping("/create/user-story/{id}/sub-task")
	public ResponseEntity<ApiResponse> creatSubTask(@PathVariable("id") int id, @RequestBody SubTaskModel subTask,
													@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
													@RequestHeader("projectIds") String projectIds){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ApiResponse response = service.createSubTask(id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);
		
	}
	
	@PutMapping("/create/user-story/{userStoryId}/sub-task/{id}")
	public ResponseEntity<ApiResponse> createSubTask(@PathVariable("userStoryId") int userStoryId,@PathVariable("id") 
	int id , @RequestBody SubTaskModel subTask, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
													 @RequestHeader("projectIds") String projectIds){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ApiResponse response = service.updateSubTask(userStoryId,id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);
		
	}

	@GetMapping("/backlog/user-story/{projectId}")
	public ResponseEntity<List<UserStoryModel>> getStoriesInBacklog(@PathVariable("projectId") String projectId,
																   @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		List<UserStoryModel> storiesInBacklog = service.getUserstoriesInBacklog(projectId);

		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(storiesInBacklog);

	}
	@GetMapping("/allDetails")
	public ResponseEntity<List<ProjectDetailsModel>> getAllDetails(@RequestParam("userId") String userId,
																   @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		List<ProjectDetailsModel> allDetails = service.getAllDetails(userId);
		
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(allDetails);
		
	}

	@GetMapping("/project/managed/{managerId}")
	public ResponseEntity<List<ProjectDataModel>> getProjectsManaged(@PathVariable("managerId") String managerId,
																	 @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(service.getProjectsManaged(managerId));
	}

	@PostMapping("/add/sprint")
	public ResponseEntity<ApiResponse> addSprint(@RequestBody SprintModel sprint,
												 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
												 @RequestHeader("projectIds") String projectId){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ApiResponse response = service.addSprint(sprint);

		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);

	}

	@PutMapping("/add/sprint/{id}")
	public ResponseEntity<ApiResponse> updateSprint(@RequestBody SprintModel sprint,@PathVariable("id")
			int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
												 @RequestHeader("projectIds") String projectIds){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		ApiResponse response = service.updateSprint(sprint,id);

		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);

	}
	
	@GetMapping("/search")
	public ResponseEntity<List<SearchResponseModel> >searchForDetails(@RequestParam Optional<Integer> id,@RequestParam Optional<String> name,
			@RequestParam String searchFlag){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		List<SearchResponseModel> searchResult=	service.searchForDetails(id,name,searchFlag);
		
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(searchResult);
	}
    
	@GetMapping("/search/{projectId}")
	public ResponseEntity<List<SearchResponseModel>> searchForUsers(@PathVariable("projectId") int projectId,
			@RequestParam Optional<Integer> id, @RequestParam Optional<String> name){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		List<SearchResponseModel> searchResult= service.searchForUsers(projectId, id, name);
		
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(searchResult);
	}

	@GetMapping("/get-details/user-story/{sprintId}")
	public ResponseEntity<List<UserStoryModel>> getUserStoriesForSprint(@PathVariable("sprintId") int sprintId){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(service.getUserStoryBySprint(sprintId));
	}

	@GetMapping("/get-details/sprints/{projectId}")
	public ResponseEntity<List<SprintResponseModel>> getAllSprintDetails(@PathVariable("projectId") String projectId){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(service.getAllSprintDetails(projectId));
	}

	@GetMapping("/get-details/sub-task/{userStoryId}")
	public ResponseEntity<List<SubTaskModel>> getAllSubTaskDetails(@PathVariable("userStoryId") int userStoryId){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(service.getAllSubTaskDetails(userStoryId));
	}

	@PostMapping("/get-details/project")
	public ResponseEntity<List<ProjectDataModel>> getProjectDetails(@RequestBody String projectIds){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		String formattedIds = projectIds.replace("\"","");
		List<String> userStoryIds = Arrays.asList(formattedIds.split(","));
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(service.getProjectDetails(userStoryIds));
	}
}
