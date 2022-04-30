package com.project.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SprintModel {
    private String name;
    private String projectId;
    private String duration;
    private Date startDate;
    private Date endDate;
    private boolean isSprintActive;

}
