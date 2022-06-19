package com.projectmanagementsystem.registrationservice.controller;

import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import com.projectmanagementsystem.registrationservice.exception.InvalidProjectAccessException;
import com.projectmanagementsystem.registrationservice.model.*;
import com.projectmanagementsystem.registrationservice.service.ProjectAccessService;
import com.projectmanagementsystem.registrationservice.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1.0/project-tracker")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ProjectAccessService projectAccessService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationController(RegistrationService registrationService,
                                  ProjectAccessService projectAccessService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.projectAccessService = projectAccessService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/registration/hello")
    public ResponseEntity<WarmupModel> healthCheck(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        WarmupModel warmUp = new WarmupModel("Hello from registration service");
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(warmUp);
    }

    @PostMapping("/user/register")
    public ResponseEntity<RegistrationResponseModel> userSignup(@RequestBody RegistrationRequestModel loginRequestModel) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDetailsDTO userDetailsDTO = modelMapper.map(loginRequestModel, UserDetailsDTO.class);
        UserDetailsDTO createdUser = registrationService.userSignup(userDetailsDTO);
        RegistrationResponseModel registrationResponseModel = modelMapper.map(createdUser, RegistrationResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(registrationResponseModel);
    }

    @GetMapping("/user/get-details/{emailId}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsByEmailId(@PathVariable String emailId,
                                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDetailsDTO loginDTO = registrationService.getUserDetailsByEmailId(emailId);
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(loginDTO);
    }

    @PostMapping("/manager/manage-user")
    public ResponseEntity<List<UserDetailsResponseModel>> manageProjectAccess(@RequestBody ProjectAccessRequestModel
                                                                        projectAccessRequestModel,
                                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                              @RequestHeader("projectIds") String projectIds,
                                                                              @RequestHeader("create-project") String createProject){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        List<String> projectIdsFromRequest = new ArrayList<>();
        List<String> projectIdsFromHeader = Arrays.asList(projectIds.split(","));
        for(ProjectAccessRequest projectAccessRequest : projectAccessRequestModel.getProjectAccessRequests()){
            for(ProjectRoleModel projectRoleModel : projectAccessRequest.getProjectRoles()){
                projectIdsFromRequest.add(projectRoleModel.getProjectId());
            }
        }
//        if(! projectIdsFromHeader.contains(projectIdsFromRequest))
//            throw new InvalidProjectAccessException("ProjectIds mismatch between header and request");
        List<UserDetailsResponseModel> response = new ArrayList<>();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        projectAccessRequestModel.getProjectAccessRequests().forEach(projectAccessRequest -> {
            UserDetailsDTO userDetailsDTO = modelMapper.map(projectAccessRequest, UserDetailsDTO.class);
            UserDetailsDTO singleUserResponse = projectAccessService.manageProjectAccess(userDetailsDTO);
            response.add(modelMapper.map(singleUserResponse, UserDetailsResponseModel.class));
        });
        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(response);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDetailsDTO>> getAllUsers(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(registrationService.getAllUsers());

    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserDetailsDTO>> getUsersForProject(@RequestHeader("projectIds") String projectIds){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        if(projectIds == null || projectIds.isEmpty()){
            throw new InvalidProjectAccessException("Project ids not passed in header");
        }
        List<String> projectIdsFromHeader = Arrays.asList(projectIds.split(","));
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(registrationService.getUsersForProject(projectIdsFromHeader));
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<List<String>> getUser(@PathVariable("userId") String userId){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(registrationService.getUser(userId));

    }


}
