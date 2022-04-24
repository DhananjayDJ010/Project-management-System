package com.projectmanagementsystem.userservice.security;

import com.projectmanagementsystem.userservice.client.ProjectServiceClient;
import com.projectmanagementsystem.userservice.client.RegistrationServiceClient;
import com.projectmanagementsystem.userservice.model.CollaborationRole;
import com.projectmanagementsystem.userservice.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    private final RegistrationServiceClient registrationServiceClient;
    private final Environment environment;
    private final ProjectServiceClient projectServiceClient;

    @Autowired
    public UserSecurityConfig(RegistrationServiceClient registrationServiceClient,
                              Environment environment, ProjectServiceClient projectServiceClient) {
        this.registrationServiceClient = registrationServiceClient;
        this.environment = environment;
        this.projectServiceClient = projectServiceClient;
    }

   @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests().regexMatchers(".*/manager/manage-user.*")
                .hasAnyAuthority(CollaborationRole.PROJECT_MANAGER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*/notify.*")
                .hasAnyAuthority(UserRole.MANAGER.name(), UserRole.USER.name());
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests().regexMatchers(".*/manager/create-project.*", ".*/project/managed/.*")
                .hasAnyAuthority(UserRole.MANAGER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*/create/user-stories.*",
                ".*/add/sprint/.*")
                .hasAnyAuthority(CollaborationRole.PROJECT_MANAGER.name(), CollaborationRole.SCRUM_MASTER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*/subtask.*", ".*/update/user-story/.*", ".*/publish/.*")
                .hasAnyAuthority(CollaborationRole.PROJECT_MANAGER.name(), CollaborationRole.SCRUM_MASTER.name(),
                        CollaborationRole.MEMBER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*/allDetails.*")
                .hasAnyAuthority(UserRole.MANAGER.name(), UserRole.USER.name());
        httpSecurity.addFilterBefore(new AuthorizationFilter(environment, registrationServiceClient, projectServiceClient),
                UsernamePasswordAuthenticationFilter.class);
    }
}
