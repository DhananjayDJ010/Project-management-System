package com.projectmanagementsystem.userservice.security;

import com.projectmanagementsystem.userservice.client.ProjectServiceClient;
import com.projectmanagementsystem.userservice.client.RegistrationServiceClient;
import com.projectmanagementsystem.userservice.exception.InvalidProjectAccessException;
import com.projectmanagementsystem.userservice.exception.JWTException;
import com.projectmanagementsystem.userservice.exception.UserNotFoundException;
import com.projectmanagementsystem.userservice.model.CollaborationRole;
import com.projectmanagementsystem.userservice.model.ProjectDataModel;
import com.projectmanagementsystem.userservice.model.UserDetailsDTO;
import com.projectmanagementsystem.userservice.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
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
    private final RegistrationServiceClient registrationServiceClient;
    private final ProjectServiceClient projectServiceClient;
    private final RestTemplate restTemplate;

    public AuthorizationFilter(Environment environment, RegistrationServiceClient registrationServiceClient,
                               ProjectServiceClient projectServiceClient, RestTemplate restTemplate){
        this.environment = environment;
        this.registrationServiceClient = registrationServiceClient;
        this.projectServiceClient = projectServiceClient;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        List<SimpleGrantedAuthority> rolesFinal = new ArrayList<>();
        boolean caseCtreate = false;
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
                log.info("Token from request: " + token);
                UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
                try {
//                    userDetailsDTO = registrationServiceClient
//                            .getUserDetailsByEmailId(userName, "Bearer " + token);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
//                    ResponseEntity<UserDetailsDTO> getResponse = restTemplate.getForEntity("https://mwwtugllg3.execute-api.ap-south-1.amazonaws.com/dev/api/v1.0/project-tracker/user/get-details/" + userName,
//                            UserDetailsDTO.class, httpEntity);
                    ResponseEntity<UserDetailsDTO> getResponse = restTemplate.exchange("https://u0bqod1gs3.execute-api.ap-south-1.amazonaws.com/dev/api/v1.0/project-tracker/user/get-details/" + userName,
                            HttpMethod.GET,httpEntity,UserDetailsDTO.class);
                    userDetailsDTO = getResponse.getBody();
                }
                catch(Exception e){
                    e.printStackTrace();
                    logger.info("Exception message feign : " + e.getMessage());
                }

                if(! userName.equals(userDetailsDTO.getEmailId()))
                    throw new UserNotFoundException("Authentication failed: Invalid user");

                if(! request.getRequestURI().contains("get-all-users") && ! request.getRequestURI().contains("managed") && ! request.getRequestURI().contains("allDetails") &&
                        ! request.getRequestURI().contains("create-project") && ! request.getRequestURI().contains("notify")){
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
                        if(request.getRequestURI().contains("manage-user")) {
                            String createProject = request.getHeader("create-project");
                            if(createProject == null || createProject.isEmpty()) {
                                throw new InvalidProjectAccessException("create-project header is not passed in the request");
                            }
                            if(! createProject.equalsIgnoreCase("true") &&
                                    ! createProject.equalsIgnoreCase("false")){
                                throw new InvalidProjectAccessException("create-project header value should be true/TRUE/false/FALSE");
                            }
                            if(createProject.equalsIgnoreCase("true")){
                                caseCtreate = true;
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
                    if(! caseCtreate){
                        if(projectIdList.size() == 1) {
                            if(filtered.isEmpty()){
                                throw new InvalidProjectAccessException("Project ids: " + projectIds + "is invalid");
                            }
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
                        new UsernamePasswordAuthenticationToken(userDetailsDTO.getUserId(),
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
