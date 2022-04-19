package com.projectmanagementsystem.userservice.client;

import com.projectmanagementsystem.userservice.model.ProjectDataModel;
import com.projectmanagementsystem.userservice.model.ProjectModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "project-service")
public interface ProjectServiceClient {

    @GetMapping("/api/v1.0/project-tracker/project/managed/{managerId}")
    public List<ProjectDataModel> getProjectsManaged(@PathVariable("managerId") String managerId);

    @PostMapping("/api/v1.0/project-tracker/manager/{managerId}/create-project")
    public ProjectDataModel createProject(@PathVariable("managerId") String userId, @RequestBody ProjectModel projectModel);
}
