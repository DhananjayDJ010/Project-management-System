package com.project.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Sprint_Details")
public class SprintDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="project_id")
	private int projectId;
	@Column(name="duration")
	private String duration;
	@Column(name="start_date")
	private Date startDate;
	@Column(name="end_date")
	private Date endDate;
	@Column(name="isActive")
	private boolean isSprintActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public boolean isSprintActive() {
		return isSprintActive;
	}
	public void setSprintActive(boolean isSprintActive) {
		this.isSprintActive = isSprintActive;
	}
	
	@Override
	public String toString() {
		return "SprintDTO [id=" + id + ", name=" + name + ", projectId="
				+ projectId + ", duration=" + duration + ", startDate="
				+ startDate + ", endDate=" + endDate + ", isSprintActive=" + isSprintActive
				+ "]";
	}
	
	
	
	
	
	
	
	
}
