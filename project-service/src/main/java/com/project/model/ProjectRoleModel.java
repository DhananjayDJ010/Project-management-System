package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectRoleModel {
    private String projectId;
    @Enumerated(EnumType.STRING)
    private CollaborationRole collaborationRole;
}
