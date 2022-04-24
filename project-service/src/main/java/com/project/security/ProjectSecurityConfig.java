package com.project.security;

import com.project.client.RegistrationServiceClient;
import com.project.model.CollaborationRole;
import com.project.model.UserRole;
import com.project.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    private final RegistrationServiceClient registrationServiceClient;
    private final Environment environment;
    private final ProjectService projectService;

    @Autowired
    public ProjectSecurityConfig(RegistrationServiceClient registrationServiceClient,
                                 Environment environment, ProjectService projectService) {
        this.registrationServiceClient = registrationServiceClient;
        this.environment = environment;
        this.projectService = projectService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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

        httpSecurity.addFilterBefore(new AuthorizationFilter(environment, registrationServiceClient, projectService),
                UsernamePasswordAuthenticationFilter.class);
    }
}
