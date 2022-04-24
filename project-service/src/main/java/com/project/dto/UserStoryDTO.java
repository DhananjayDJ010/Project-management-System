 package com.project.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="User_Story_Details")
public class UserStoryDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="sprint_id")
	private int sprintId;
	@Column(name="assigned_user")
	private String assignedUser;
	@Column(name="acceptance_criteria")
	private String acceptanceCriteria;
	@Column(name="story_points")
	private int storyPoints;
	@Column(name="estimated_efforts")
	private int estimatedEfforts;
	@Column(name="consumed_efforts")
	private int consumedEfforts;
	@Column(name="remaining_efforts")
	private int remainingEfforts;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status;
	@Enumerated(EnumType.STRING)
	@Column(name="priority")
	private Priority priority;
	@Column(name="isBacklog")
	private boolean isBacklog;
	
	public UserStoryDTO(int id, String name,int sprintId, String assignedUser,
			String acceptanceCriteria, int storyPoints, int estimatedEfforts,
			int consumedEfforts, int remainingEfforts, Status status,boolean isBacklog) {
		super();
		this.id = id;
		this.name = name;
		this.sprintId=sprintId;
		this.assignedUser = assignedUser;
		this.acceptanceCriteria = acceptanceCriteria;
		this.storyPoints = storyPoints;
		this.estimatedEfforts = estimatedEfforts;
		this.consumedEfforts = consumedEfforts;
		this.remainingEfforts = remainingEfforts;
		this.status = status;
		this.isBacklog=isBacklog;
	}

	public UserStoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public int getStoryPoints() {
		return storyPoints;
	}

	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}

	public int getEstimatedEfforts() {
		return estimatedEfforts;
	}

	public void setEstimatedEfforts(int estimatedEfforts) {
		this.estimatedEfforts = estimatedEfforts;
	}

	public int getConsumedEfforts() {
		return consumedEfforts;
	}

	public void setConsumedEfforts(int consumedEfforts) {
		this.consumedEfforts = consumedEfforts;
	}

	public int getRemainingEfforts() {
		return remainingEfforts;
	}

	public void setRemainingEfforts(int remainingEfforts) {
		this.remainingEfforts = remainingEfforts;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public boolean isBacklog() {
		return isBacklog;
	}

	public void setBacklog(boolean isBacklog) {
		this.isBacklog = isBacklog;
	}
	
	

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}
	
	

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "UserStoryDTO [id=" + id + ", name=" + name + ", sprintId="
				+ sprintId + ", assignedUser=" + assignedUser
				+ ", acceptanceCriteria=" + acceptanceCriteria
				+ ", storyPoints=" + storyPoints + ", estimatedEfforts="
				+ estimatedEfforts + ", consumedEfforts=" + consumedEfforts
				+ ", remainingEfforts=" + remainingEfforts + ", status="
				+ status + ", priority=" + priority + ", isBacklog="
				+ isBacklog + "]";
	}

	

	

	
	
	

}
