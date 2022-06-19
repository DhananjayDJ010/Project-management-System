package com.projectmanagementsystem.registrationservice.security;

import com.projectmanagementsystem.registrationservice.client.ProjectServiceClient;
import com.projectmanagementsystem.registrationservice.model.CollaborationRole;
import com.projectmanagementsystem.registrationservice.model.UserRole;
import com.projectmanagementsystem.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
public class RegistrationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;
    private final ProjectServiceClient projectServiceClient;
    private final RestTemplate restTemplate;

    @Autowired
    public RegistrationSecurityConfig(RegistrationService registrationService, BCryptPasswordEncoder bCryptPasswordEncoder,
                                      Environment environment, ProjectServiceClient projectServiceClient, RestTemplate restTemplate) {
        this.registrationService = registrationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
        this.projectServiceClient = projectServiceClient;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests().regexMatchers(".*manager/.*")
                .hasAnyAuthority(CollaborationRole.PROJECT_MANAGER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*get-all-users.*")
                .hasAnyAuthority(UserRole.MANAGER.name(), UserRole.USER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*get-users.*")
                .hasAnyAuthority(UserRole.MANAGER.name(), CollaborationRole.PROJECT_MANAGER.name(),
                        CollaborationRole.MEMBER.name(), CollaborationRole.SCRUM_MASTER.name());
        httpSecurity.authorizeRequests().regexMatchers(".*get-details.*")
                .hasAnyAuthority(UserRole.USER.name(), UserRole.MANAGER.name());
        httpSecurity.authorizeRequests().regexMatchers("/", ".*user/.*").permitAll();
        httpSecurity.addFilter(getAuthenticationFilter());
        httpSecurity.addFilterBefore(new AuthorizationFilter(environment, registrationService, projectServiceClient, restTemplate),
                RegistrationAuthFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(registrationService).passwordEncoder(bCryptPasswordEncoder);
    }

    private RegistrationAuthFilter getAuthenticationFilter() throws Exception {
        RegistrationAuthFilter registrationAuthFilter =
                new RegistrationAuthFilter(registrationService, environment, authenticationManager());
        registrationAuthFilter.setFilterProcessesUrl("/api/v1.0/project-tracker/user/login");
        return registrationAuthFilter;
    }
}
