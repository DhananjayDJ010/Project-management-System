package com.projectmanagementsystem.userservice.controller;

import com.projectmanagementsystem.userservice.model.CreateProjectResponseModel;
import com.projectmanagementsystem.userservice.model.ProjectDataModel;
import com.projectmanagementsystem.userservice.model.ProjectModel;
import com.projectmanagementsystem.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
                                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createProject(projectModel, token));
    }
}
