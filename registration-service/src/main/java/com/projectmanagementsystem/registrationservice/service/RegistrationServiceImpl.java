package com.projectmanagementsystem.registrationservice.service;

import com.projectmanagementsystem.registrationservice.dao.RegistrationDAO;
import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import com.projectmanagementsystem.registrationservice.entity.ProjectUser;
import com.projectmanagementsystem.registrationservice.entity.ProjectUserKey;
import com.projectmanagementsystem.registrationservice.exception.UserAlreadyExistsException;
import com.projectmanagementsystem.registrationservice.exception.UserNotFoundException;
import com.projectmanagementsystem.registrationservice.model.ProjectAccessRequest;
import com.projectmanagementsystem.registrationservice.model.ProjectRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@EnableTransactionManagement
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationDAO registrationDAO;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationServiceImpl(RegistrationDAO registrationDAO, ModelMapper modelMapper,
                                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registrationDAO = registrationDAO;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDetailsDTO userSignup(UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailsDTO.getPassword()));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectUser signupEntity = modelMapper.map(userDetailsDTO, ProjectUser.class);
        signupEntity.setUserId(new ProjectUserKey(UUID.randomUUID().toString()));
        ProjectUser signUpCheckEntity = registrationDAO.findByEmailId(userDetailsDTO.getEmailId());
        if(Optional.ofNullable(signUpCheckEntity).isPresent()){
            throw new UserAlreadyExistsException("User with email ID" + userDetailsDTO.getEmailId() + "exists");
        }
        registrationDAO.save(signupEntity);
        userDetailsDTO.setUserId(signupEntity.getUserId().getUserId());
        return userDetailsDTO;
    }

    @Override
    @Transactional
    public UserDetailsDTO getUserDetailsByEmailId(String emailId) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectUser projectUser = registrationDAO.findByEmailId(emailId);
        if(projectUser == null){
            throw new UserNotFoundException("User with email ID " + emailId + "not found");
        }
        List<ProjectRoleModel> projectRoleModelList = new ArrayList<>();
        UserDetailsDTO userDetailsDTO = modelMapper.map(projectUser, UserDetailsDTO.class);
        userDetailsDTO.setUserId(projectUser.getUserId().getUserId());
        projectUser.getProjectRoles().forEach(projectRole -> {
            projectRoleModelList.add(new ProjectRoleModel(projectRole.getProjectRoleKey().getProjectId(),
                    projectRole.getCollaborationRole()));
        });
        userDetailsDTO.setProjectRoles(projectRoleModelList);
        return userDetailsDTO;
    }

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return StreamSupport.stream(registrationDAO.findAll().spliterator(), true)
                .map(user -> {
                    List<ProjectRoleModel> projectRoleModelList = new ArrayList<>();
                    UserDetailsDTO userDetailsDTO = modelMapper.map(user, UserDetailsDTO.class);
                    userDetailsDTO.setUserId(user.getUserId().getUserId());
                    user.getProjectRoles().forEach(projectRole -> {
                        projectRoleModelList.add(new ProjectRoleModel(projectRole.getProjectRoleKey().getProjectId(),
                                projectRole.getCollaborationRole()));
                    });
                    userDetailsDTO.setProjectRoles(projectRoleModelList);
                    return userDetailsDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDetailsDTO> getUsersForProject(List<String> projectIds) {
        List<UserDetailsDTO> allUsers = getAllUsers();
        return allUsers.stream().filter(user -> user.getProjectRoles().stream().filter(project -> projectIds.contains(project.getProjectId()))
                .collect(Collectors.toList()).size() > 0).collect(Collectors.toList());
    }

    @Override
    public List<String> getUser(String userId) {
        return registrationDAO.getUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProjectUser projectUser = registrationDAO.findByEmailId(username);
        if(projectUser == null){
            throw new UsernameNotFoundException("User with email ID " + username + "not found");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(projectUser.getUserRole().name());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new User(projectUser.getEmailId(), projectUser.getEncryptedPassword(), authorities);
    }
}
