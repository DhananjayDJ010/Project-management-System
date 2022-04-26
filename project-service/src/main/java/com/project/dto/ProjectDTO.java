package com.project.dto;

import com.project.model.ProjectType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

import javax.persistence.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="Project_Details")
public class ProjectDTO {
	@Id
    @Column(name = "project_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
     private String projectId;
     private String projectName;
    @Enumerated(EnumType.STRING)
     private ProjectType projectType;
     private String managerId;
}
