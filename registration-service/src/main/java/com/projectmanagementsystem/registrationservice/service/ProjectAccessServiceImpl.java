package com.projectmanagementsystem.registrationservice.service;

import com.projectmanagementsystem.registrationservice.dao.ProjectAccessDAO;
import com.projectmanagementsystem.registrationservice.dao.RegistrationDAO;
import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import com.projectmanagementsystem.registrationservice.entity.ProjectRole;
import com.projectmanagementsystem.registrationservice.entity.ProjectRoleKey;
import com.projectmanagementsystem.registrationservice.entity.ProjectUser;
import com.projectmanagementsystem.registrationservice.entity.ProjectUserKey;
import com.projectmanagementsystem.registrationservice.exception.UserNotFoundException;
import com.projectmanagementsystem.registrationservice.model.ProjectRoleModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class ProjectAccessServiceImpl implements ProjectAccessService{
    private final ProjectAccessDAO projectAccessDAO;
    private final RegistrationDAO registrationDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectAccessServiceImpl(ProjectAccessDAO projectAccessDAO,
                                    RegistrationDAO registrationDAO, ModelMapper modelMapper) {
        this.projectAccessDAO = projectAccessDAO;
        this.registrationDAO = registrationDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDetailsDTO manageProjectAccess(UserDetailsDTO userDetailsDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectUserKey projectUserKey = new ProjectUserKey(userDetailsDTO.getUserId());
        Optional<ProjectUser> projectUserOptional = registrationDAO.findById(projectUserKey);
        if(projectUserOptional.isPresent()){
            throw new UserNotFoundException("User with id " + userDetailsDTO.getUserId() + "not found");
        }
        ProjectUser projectUser = projectUserOptional.get();
        List<ProjectRole> projectRoleEntityList = new ArrayList<>();
        userDetailsDTO.getProjectRoles().forEach(projectRoleModel -> {
            ProjectRoleKey projectRoleKey = new ProjectRoleKey(projectRoleModel.getProjectId(), projectUserKey);
            Optional<ProjectRole> projectRoleOptional = projectAccessDAO.findById(projectRoleKey);
            ProjectRole projectRole;
            if(projectRoleOptional.isPresent()
            ) {
                projectRole = new ProjectRole();
                projectRole.setProjectRoleKey(projectRoleKey);
            }
            else {
                projectRole = projectRoleOptional.get();
            }
            projectRole.setCollaborationRole(projectRoleModel.getCollaborationRole());
            projectRole.setProjectUser(projectUser);
            projectRoleEntityList.add(projectRole);
        });
        projectUser.getProjectRoles().addAll(projectRoleEntityList);
        registrationDAO.save(projectUser);
        List<ProjectRoleModel> projectRoleModelList = projectRoleEntityList.stream().map(projectRole ->
                new ProjectRoleModel(projectRole.getProjectRoleKey().getProjectId(), projectRole.getCollaborationRole()))
                .collect(Collectors.toList());
        UserDetailsDTO singleUserResponse = modelMapper.map(projectUser, UserDetailsDTO.class);
        singleUserResponse.setUserId(projectUser.getUserId().getUserId());
        singleUserResponse.setProjectRoles(projectRoleModelList);
        return singleUserResponse;
    }
}
