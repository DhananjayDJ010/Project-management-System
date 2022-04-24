package com.project.model;

import com.project.dto.Status;

public class SubTaskModel {
	private int id;
	private String name;
	private int userStoryId;
	private String assignedUser;
	private int estimatedEfforts;
	private int consumedEfforts;
	private int remainingEfforts;
	private Status status;
	
	public SubTaskModel( int id,String name, int userStoryId,
			String assignedUser, int estimatedEfforts, int consumedEfforts,
			int remainingEfforts, Status status) {
		super();
		this.id=id;
		this.name = name;
		this.userStoryId = userStoryId;
		this.assignedUser = assignedUser;
		this.estimatedEfforts = estimatedEfforts;
		this.consumedEfforts = consumedEfforts;
		this.remainingEfforts = remainingEfforts;
		this.status = status;
	}

	public SubTaskModel() {
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

	public int getUserStoryId() {
		return userStoryId;
	}

	public void setUserStoryId(int userStoryId) {
		this.userStoryId = userStoryId;
	}

	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
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

	@Override
	public String toString() {
		return "SubTaskDTO [ name=" + name + ", userStoryId="
				+ userStoryId + ", assignedUser=" + assignedUser
				+ ", estimatedEfforts=" + estimatedEfforts
				+ ", consumedEfforts=" + consumedEfforts
				+ ", remainingEfforts=" + remainingEfforts + ", status="
				+ status + "]";
	}
	
	

}
