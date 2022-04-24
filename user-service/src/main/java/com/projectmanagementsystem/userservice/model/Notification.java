package com.projectmanagementsystem.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    private LocalDateTime createdAt;
    @JsonProperty
    private boolean isRead;
    private String message;
}
