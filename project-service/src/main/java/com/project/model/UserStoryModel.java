package com.project.model;

import com.project.dto.Status;

public class UserStoryModel {

	private int id;
	private String name;
	private String assignedUser;
	private String acceptanceCriteria;
	private int storyPoints;
	private int estimatedEfforts;
	private int consumedEfforts;
	private int remainingEfforts;
	private Status status;
	private boolean isBacklog;
	
	public UserStoryModel( int id,String name, String assignedUser,
			String acceptanceCriteria, int storyPoints, int estimatedEfforts,
			int consumedEfforts, int remainingEfforts, Status status,boolean isBacklog) {
		super();
		
		this.id=id;
		this.name = name;
		this.assignedUser = assignedUser;
		this.acceptanceCriteria = acceptanceCriteria;
		this.storyPoints = storyPoints;
		this.estimatedEfforts = estimatedEfforts;
		this.consumedEfforts = consumedEfforts;
		this.remainingEfforts = remainingEfforts;
		this.status = status;
		this.isBacklog=isBacklog;
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
	
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserStoryDTO [ name=" + name + ", assignedUser="
				+ assignedUser + ", acceptanceCriteria=" + acceptanceCriteria
				+ ", storyPoints=" + storyPoints + ", estimatedEfforts="
				+ estimatedEfforts + ", consumedEfforts=" + consumedEfforts
				+ ", remainingEfforts=" + remainingEfforts + ", status="
				+ status + ", isBacklog=" + isBacklog + "]";
	}
	
	
}
