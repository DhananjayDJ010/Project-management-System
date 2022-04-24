package com.project.controller;

//import org.apache.logging.log4j.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import java.util.List;
import java.util.Optional;

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
		
		ProjectDataModel response = service.createProject(userId,projectModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/create/user-stories")
	public ResponseEntity<List<ApiResponse>> createUserStory(@RequestBody List<UserStoryModel> userStoryDetails,
															 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
															 @RequestHeader("projectIds") String projectIds){
		
		List<ApiResponse> response = service.createUserStory(userStoryDetails, projectIds);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/add/sprint/{sprintId}/user-stories")
	public ResponseEntity<List<ApiResponse>> addUserStory(@PathVariable("sprintId")String sprintId,
			@RequestBody List<Integer> listOfIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
														  @RequestHeader("projectIds") String projectIds){
		
		List<ApiResponse> response = service.addUserStories(listOfIds);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PutMapping("/update/user-story/{id}")
	public ResponseEntity<ApiResponse> updateUserStory(@PathVariable("id") int id, @RequestBody UserStoryModel userStory,
													   @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
													   @RequestHeader("projectIds") String projectIds){
		
		ApiResponse response = service.updateUserStory(id,userStory);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping("/create/user-story/{id}/sub-task")
	public ResponseEntity<ApiResponse> creatSubTask(@PathVariable("id") int id, @RequestBody SubTaskModel subTask,
													@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
													@RequestHeader("projectIds") String projectIds){
		
		ApiResponse response = service.createSubTask(id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/create/user-story/{userStoryId}/sub-task/{id}")
	public ResponseEntity<ApiResponse> createSubTask(@PathVariable("userStoryId") int userStoryId,@PathVariable("id") 
	int id , @RequestBody SubTaskModel subTask, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
													 @RequestHeader("projectIds") String projectIds){
		
		ApiResponse response = service.updateSubTask(userStoryId,id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}

	@GetMapping("/allDetails")
	public ResponseEntity<List<ProjectDetailsModel>> getAllDetails(@RequestParam("userId") int userId,
																   @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		
		List<ProjectDetailsModel> allDetails = service.getAllDetails(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(allDetails);
		
	}

	@GetMapping("/project/managed/{managerId}")
	public ResponseEntity<List<ProjectDataModel>> getProjectsManaged(@PathVariable("managerId") String managerId,
																	 @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
																	 @RequestHeader("projectIds") String projectIds){

		return ResponseEntity.status(HttpStatus.OK).body(service.getProjectsManaged(managerId));
	}
}
