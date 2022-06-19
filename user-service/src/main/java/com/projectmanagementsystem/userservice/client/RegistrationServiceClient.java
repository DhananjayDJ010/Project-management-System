package com.projectmanagementsystem.userservice.client;

import com.projectmanagementsystem.userservice.model.ProjectAccessRequestModel;
import com.projectmanagementsystem.userservice.model.UserDetailsDTO;
import com.projectmanagementsystem.userservice.model.UserDetailsResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "registration-service", url="${client.baseUrl}")
public interface RegistrationServiceClient {

    @PostMapping("/api/v1.0/project-tracker/manager/manage-user")
    public List<UserDetailsResponseModel> manageProjectAccess(@RequestBody ProjectAccessRequestModel
                                                                                      projectAccessRequestModel,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                              @RequestHeader("projectIds") String projectIds,
                                                              @RequestHeader("create-project") String createProject);

    @GetMapping("api/v1.0/project-tracker/user/get-details/{emailId}")
    public UserDetailsDTO getUserDetailsByEmailId(@PathVariable String emailId,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    }
