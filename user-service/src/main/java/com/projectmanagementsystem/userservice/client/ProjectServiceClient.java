package com.projectmanagementsystem.userservice.client;

import com.projectmanagementsystem.userservice.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "project-service")
public interface ProjectServiceClient {

    @GetMapping("/api/v1.0/project-tracker/project/managed/{managerId}")
    public List<ProjectDataModel> getProjectsManaged(@PathVariable("managerId") String managerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping("/api/v1.0/project-tracker/manager/{managerId}/create-project")
    public ProjectDataModel createProject(@PathVariable("managerId") String userId, @RequestBody ProjectModel projectModel,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
    @PostMapping("/api/v1.0/project-tracker/create/user-stories")
    public List<ApiResponse> createUserStory(@RequestBody List<UserStoryModel> userStoryDetails,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                             @RequestHeader("projectIds") String projectIds);

    @PostMapping("/api/v1.0/project-tracker/add/sprint/{sprintId}/user-stories")
    public List<ApiResponse> addUserStory(@PathVariable("sprintId")String sprintId,
                                                          @RequestBody List<Integer> listOfIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                          @RequestHeader("projectIds") String projectIds);

    @PutMapping("/api/v1.0/project-tracker/update/user-story/{id}")
    public ApiResponse updateUserStory(@PathVariable("id") int id, @RequestBody UserStoryModel userStory,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @RequestHeader("projectIds") String projectIds);

    @PostMapping("/api/v1.0/project-tracker/create/user-story/{id}/sub-task")
    public ApiResponse createSubTask(@PathVariable("id") int id, @RequestBody SubTaskModel subTask,
                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                    @RequestHeader("projectIds") String projectIds);

    @PutMapping("/api/v1.0/project-tracker/create/user-story/{userStoryId}/sub-task/{id}")
    public ApiResponse updateSubtask(@PathVariable("userStoryId") int userStoryId,@PathVariable("id")
            int id , @RequestBody SubTaskModel subTask, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestHeader("projectIds") String projectIds);

    @GetMapping("/api/v1.0/project-tracker/allDetails")
    public List<ProjectDetailsModel> getAllDetails(@RequestParam("userId") int userId,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
