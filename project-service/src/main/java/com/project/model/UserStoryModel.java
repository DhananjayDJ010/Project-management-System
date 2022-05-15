package com.project.model;

import com.project.dto.Priority;
import com.project.dto.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserStoryModel {

	private int id;
	private int sprintId;
	private int projectId;
	private String name;
	private String assignedUser;
	private String acceptanceCriteria;
	private int storyPoints;
	private int estimatedEfforts;
	private int consumedEfforts;
	private int remainingEfforts;
	private Status status;
	private boolean isBacklog;
	private int sprintId;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	public UserStoryModel( int id,int sprintId, int projectId,String name, String assignedUser,
			String acceptanceCriteria, int storyPoints, int estimatedEfforts,
			int consumedEfforts, int remainingEfforts, Status status,boolean isBacklog, int sprintId) {
		super();
		
		this.id=id;
		this.sprintId=sprintId;
		this.projectId=projectId;
		this.name = name;
		this.assignedUser = assignedUser;
		this.acceptanceCriteria = acceptanceCriteria;
		this.storyPoints = storyPoints;
		this.estimatedEfforts = estimatedEfforts;
		this.consumedEfforts = consumedEfforts;
		this.remainingEfforts = remainingEfforts;
		this.status = status;
		this.isBacklog=isBacklog;
		this.sprintId = sprintId;
	}

	public UserStoryModel() {
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

	public boolean isBacklog() {
		return isBacklog;
	}

	public void setBacklog(boolean isBacklog) {
		this.isBacklog = isBacklog;
	}

	public int getSprintId(){
		return sprintId;
	}

	public void setSprintId(int sprintId){
		this.sprintId = sprintId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "UserStoryModel{" +
				"id=" + id +
				", name='" + name + '\'' +
				", assignedUser='" + assignedUser + '\'' +
				", acceptanceCriteria='" + acceptanceCriteria + '\'' +
				", storyPoints=" + storyPoints +
				", estimatedEfforts=" + estimatedEfforts +
				", consumedEfforts=" + consumedEfforts +
				", remainingEfforts=" + remainingEfforts +
				", status=" + status +
				", isBacklog=" + isBacklog +
				", sprintId=" + sprintId +
				", priority=" + priority +
				'}';
	}



	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
