package com.project.model;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectModel {
     private String projectName;
     @Enumerated(EnumType.STRING)
     private ProjectType projectType;
     private String managerId;

}
