package com.projectmanagementsystem.registrationservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectmanagementsystem.registrationservice.dto.UserDetailsDTO;
import com.projectmanagementsystem.registrationservice.model.RegistrationRequestModel;
import com.projectmanagementsystem.registrationservice.service.RegistrationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class RegistrationAuthFilter extends UsernamePasswordAuthenticationFilter {
    private RegistrationService registrationService;
    private Environment environment;

    public RegistrationAuthFilter(RegistrationService registrationService, Environment environment, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            RegistrationRequestModel registrationRequestModel = new ObjectMapper().readValue(request.getInputStream(),
                    RegistrationRequestModel.class);
            UserDetailsDTO userDetailsDTO = registrationService.getUserDetailsByEmailId(registrationRequestModel.getEmailId());
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registrationRequestModel.getEmailId(),
                            registrationRequestModel.getPassword(),
                            Arrays.asList(new SimpleGrantedAuthority(userDetailsDTO.getUserRole().name()))
                    )
            );
        } catch(IOException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String userName = ((User) authentication.getPrincipal()).getUsername();
        UserDetailsDTO userDetailsDTO = registrationService.getUserDetailsByEmailId(userName);
        String token = Jwts.builder()
                .setSubject(userDetailsDTO.getEmailId())
                .claim("roles", Arrays.asList(new SimpleGrantedAuthority(userDetailsDTO.getUserRole().name())))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        userDetailsDTO.setAccessToken(token);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(userDetailsDTO));
    }
}
