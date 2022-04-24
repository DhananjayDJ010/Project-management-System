package com.project.client;

import com.project.model.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "registration-service")
public interface RegistrationServiceClient {

    @GetMapping("api/v1.0/project-tracker/user/get-details/{emailId}")
    public UserDetailsDTO getUserDetailsByEmailId(@PathVariable String emailId,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    }
