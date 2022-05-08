package com.projectmanagementsystem.registrationservice.security;

import com.projectmanagementsystem.registrationservice.client.ProjectServiceClient;
import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import com.projectmanagementsystem.registrationservice.exception.InvalidProjectAccessException;
import com.projectmanagementsystem.registrationservice.exception.JWTException;
import com.projectmanagementsystem.registrationservice.exception.UserNotFoundException;
import com.projectmanagementsystem.registrationservice.model.CollaborationRole;
import com.projectmanagementsystem.registrationservice.model.ProjectDataModel;
import com.projectmanagementsystem.registrationservice.model.ProjectRoleModel;
import com.projectmanagementsystem.registrationservice.model.UserRole;
import com.projectmanagementsystem.registrationservice.service.RegistrationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final Environment environment;
    private final RegistrationService registrationService;
    private final ProjectServiceClient projectServiceClient;

    public AuthorizationFilter(Environment environment, RegistrationService registrationService,
                               ProjectServiceClient projectServiceClient){
        this.environment = environment;
        this.registrationService = registrationService;
        this.projectServiceClient = projectServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("**/user/**"))
            filterChain.doFilter(request, response);
        else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            List<SimpleGrantedAuthority> rolesFinal = new ArrayList<>();
            boolean caseCreate = false;
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try{
                    String token = authorizationHeader.split(" ")[1];
                    Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
                            .parseClaimsJws(token);
                    String userName = jwsClaims.getBody().getSubject();
                    System.out.println(userName);
                    ArrayList<HashMap<String, String>> rolesFromToken = (ArrayList<HashMap<String, String>>) jwsClaims.getBody().get("roles");
                    for(HashMap<String, String> role : rolesFromToken){
                        for(String key : role.keySet())
                            rolesFinal.add(new SimpleGrantedAuthority(role.get(key)));
                    }

                    UserDetailsDTO userDetailsDTO = registrationService.getUserDetailsByEmailId(userName);

                    if(! userName.equals(userDetailsDTO.getEmailId()))
                        throw new UserNotFoundException("Authentication failed: Invalid user");

                    if(! request.getServletPath().contains("get-all-users") && ! request.getServletPath().contains("managed") &&
                            ! request.getServletPath().contains("get-details")){
                        String projectIds = request.getHeader("projectIds");
                        if(projectIds == null || projectIds.isEmpty()) {
                            throw new InvalidProjectAccessException("ProjectIds header is not passed in the request");
                        }
                        List<String> projectIdList = Arrays.asList(projectIds.split(","));
                        List<String> projectIdsProcessed = userDetailsDTO.getProjectRoles().stream()
                                .map(projectRoleModel -> projectRoleModel.getProjectId()).collect(Collectors.toList());
                        List<CollaborationRole> filtered = userDetailsDTO.getProjectRoles().stream()
                                .filter(projectRoleModel -> projectIdList.contains(projectRoleModel.getProjectId()))
                                .map(projectRoleModel -> projectRoleModel.getCollaborationRole())
                                .collect(Collectors.toList());

                        if(userDetailsDTO.getUserRole().equals(UserRole.MANAGER)) {
                            if(request.getServletPath().contains("manage-user")) {
                                String createProject = request.getHeader("create-project");
                                if(createProject == null || createProject.isEmpty()) {
                                    throw new InvalidProjectAccessException("create-project header is not passed in the request");
                                }
                                if(! createProject.equalsIgnoreCase("true") &&
                                        ! createProject.equalsIgnoreCase("false")){
                                    throw new InvalidProjectAccessException("create-project header value should be true/TRUE/false/FALSE");
                                }
                                if(createProject.equalsIgnoreCase("true")){
                                    caseCreate = true;
                                    List<ProjectDataModel> allManagedProjects = projectServiceClient.
                                            getProjectsManaged(userDetailsDTO.getUserId(), "Bearer " + token);
                                    List<String> managedIds = allManagedProjects.stream().
                                            map(proj -> proj.getProjectId()).collect(Collectors.toList());
                                    if(! managedIds.isEmpty() && managedIds.containsAll(projectIdList)){
                                        rolesFinal.add(new SimpleGrantedAuthority(CollaborationRole.PROJECT_MANAGER.name()));
                                    }
                                    else if(! managedIds.containsAll(projectIdList)){
                                        throw new InvalidProjectAccessException("User has varying access privileges across given projects");
                                    }
                                }
                            }
                        }
                        if(! caseCreate){
                            if(filtered.isEmpty()){
                                throw new InvalidProjectAccessException("Project ids: " + projectIds + "is invalid");
                            }
                            if(projectIdList.size() == 1) {
                                if(projectIdsProcessed.contains(projectIdList.get(0)))
                                    rolesFinal.add(new SimpleGrantedAuthority(filtered.get(0).name()));
                                else
                                    throw new InvalidProjectAccessException("Project id " + filtered.get(0).name() + "is invalid");
                            }
                            else{
                                if(projectIdsProcessed.containsAll(projectIdList) && new HashSet<>(filtered).size() == 1)
                                    rolesFinal.add(new SimpleGrantedAuthority(filtered.get(0).name()));
                                else
                                    throw new InvalidProjectAccessException("User has varying access privileges across given projects");
                            }
                        }
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetailsDTO.getEmailId(),
                                    userDetailsDTO.getEncryptedPassword(), rolesFinal);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                }
                catch(Exception exception){
                    log.error("Error while logging in" + exception.getMessage());
                    throw new JWTException(exception.getMessage());
                }
            }
            else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
