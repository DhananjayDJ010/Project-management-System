package com.project.controller;

//import org.apache.logging.log4j.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import java.util.List;
import java.util.Optional;

import com.project.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public String post (@PathVariable("message") final String message){
		
		kafkaTemplate.send(TOPIC,new User(message,"tech"));
		
		if(message.startsWith("b")){
			kafkaTemplate.send(TOPIC1,new User(message,"tech"));
		}
		log.info("Test Successfully");
		return "Publish Successfully";
		
	}
	
	@PostMapping("/manager/{managerId}/create-project")
	public ResponseEntity<ProjectDataModel> createProject(@PathVariable("managerId") String userId, @RequestBody ProjectModel projectModel){
		
		ProjectDataModel response = service.createProject(userId,projectModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/project/{projectId}/add-users")
	public ResponseEntity<String> addUsersToProject(@PathVariable("projectId")int projectId,
			@RequestBody List<Integer>listOfUsers){
		
		String msg = service.addUsersToProject(projectId,listOfUsers);
		
		return ResponseEntity.status(HttpStatus.OK).body(msg);
		
	}
	
	@PostMapping("/create/user-stories")
	public ResponseEntity<List<ApiResponse>> createUserStory(@RequestBody List<UserStoryModel> userStoryDetails){
		
		List<ApiResponse> response = service.createUserStory(userStoryDetails);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/add/sprint/{sprintId}/user-stories")
	public ResponseEntity<List<ApiResponse>> addUserStory(@PathVariable("sprintId")String sprintId,
			@RequestBody List<Integer> listOfIds){
		
		List<ApiResponse> response = service.addUserStories(listOfIds);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PutMapping("/update/user-story/{id}")
	public ResponseEntity<ApiResponse> updateUserStory(@PathVariable("id") int id, @RequestBody UserStoryModel userStory){
		
		ApiResponse response = service.updateUserStory(id,userStory);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping("/create/user-story/{id}/sub-task")
	public ResponseEntity<ApiResponse> creatSubTask(@PathVariable("id") int id, @RequestBody SubTaskModel subTask){
		
		ApiResponse response = service.createSubTask(id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/create/user-story/{userStoryId}/sub-task/{id}")
	public ResponseEntity<ApiResponse> createSubTask(@PathVariable("userStoryId") int userStoryId,@PathVariable("id") 
	int id , @RequestBody SubTaskModel subTask){
		
		ApiResponse response = service.updateSubTask(userStoryId,id,subTask);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	@GetMapping("/projects")
	public ResponseEntity<List<ProjectModel>> getProjectDetails(@RequestParam("userId") int userId){
		
		Optional<List<ProjectModel>> projectDetails = service.getProjectDetails(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(projectDetails.get());
		
	}
	@GetMapping("/allDetails")
	public ResponseEntity<List<ProjectDetailsModel>> getAllDetails(@RequestParam("userId") int userId){
		
		List<ProjectDetailsModel> allDetails = service.getAllDetails(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(allDetails);
		
	}

	@GetMapping("/project/managed/{managerId}")
	public ResponseEntity<List<ProjectDataModel>> getProjectsManaged(@PathVariable("managerId") String managerId){

		return ResponseEntity.status(HttpStatus.OK).body(service.getProjectsManaged(managerId));
	}
}
