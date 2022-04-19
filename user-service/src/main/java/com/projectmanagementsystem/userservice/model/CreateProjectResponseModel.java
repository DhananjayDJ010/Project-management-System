package com.projectmanagementsystem.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProjectResponseModel {
    List<UserDetailsResponseModel> userDetailsResponseModels;
    ProjectDataModel projectData;
}
