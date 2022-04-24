package com.projectmanagementsystem.registrationservice.client;

import com.projectmanagementsystem.registrationservice.model.ProjectDataModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "project-service")
public interface ProjectServiceClient {
    @GetMapping("/api/v1.0/project-tracker/project/managed/{managerId}")
    public List<ProjectDataModel> getProjectsManaged(@PathVariable("managerId") String managerId,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
