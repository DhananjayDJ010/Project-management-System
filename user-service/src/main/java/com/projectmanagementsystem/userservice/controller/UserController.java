package com.projectmanagementsystem.userservice.controller;

import com.projectmanagementsystem.userservice.model.*;
import com.projectmanagementsystem.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/project-tracker")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/manager/create-project")
    public ResponseEntity<CreateProjectResponseModel> createProject(@RequestBody ProjectModel projectModel,
                                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createProject(projectModel, token));
    }

    @PostMapping("/notify")
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.sendNotification(notification));
    }

    @PostMapping("/manager/manage-user")
    public ResponseEntity<List<UserDetailsResponseModel>> manageProjectAccess(@RequestBody ProjectAccessRequestModel
                                                                                      projectAccessRequestModel,
                                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                              @RequestHeader("projectIds") String projectIds,
                                                                              @RequestHeader("create-project") String createProject) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.manageProjects(projectAccessRequestModel, token, projectIds,
                "false"));
    }

    @PostMapping("/create/user-stories")
    public ResponseEntity<List<ApiResponse>> createUserStory(@RequestBody List<UserStoryModel> userStoryDetails,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                             @RequestHeader("projectIds") String projectIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserStory(userStoryDetails, projectIds, token));
    }

    @PostMapping("/add/sprint/{sprintId}/user-stories")
    public ResponseEntity<List<ApiResponse>> addUserStory(@PathVariable("sprintId") String sprintId,
                                                          @RequestBody List<Integer> listOfIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                          @RequestHeader("projectIds") String projectIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUserStories(sprintId, listOfIds, token, projectIds));
    }

    @PutMapping("/update/user-story/{id}")
    public ResponseEntity<ApiResponse> updateUserStory(@PathVariable("id") int id, @RequestBody UserStoryModel userStory,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @RequestHeader("projectIds") String projectIds) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserStory(id, userStory, token, projectIds));
    }

    @PostMapping("/create/user-story/{id}/sub-task")
    public ResponseEntity<ApiResponse> createSubTask(@PathVariable("id") int id, @RequestBody SubTaskModel subTask,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestHeader("projectIds") String projectIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createSubTask(id, subTask, token, projectIds));
    }

    @PutMapping("/create/user-story/{userStoryId}/sub-task/{id}")
    public ResponseEntity<ApiResponse> createSubTask(@PathVariable("userStoryId") int userStoryId, @PathVariable("id")
            int id, @RequestBody SubTaskModel subTask, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestHeader("projectIds") String projectIds) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateSubTask(userStoryId, id, subTask, token, projectIds));
    }

    @GetMapping("/allDetails")
    public ResponseEntity<List<ProjectDetailsModel>> getAllDetails(@RequestParam("userId") int userId,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllDetails(userId, token));
    }

    @GetMapping("/project/managed/{managerId}")
    public ResponseEntity<List<ProjectDataModel>> getProjectsManaged(@PathVariable("managerId") String managerId,
                                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProjectsManaged(managerId, token));
    }
}
