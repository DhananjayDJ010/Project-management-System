package com.project.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Sub_Task_Details")
public class SubTaskDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="name")
	private String name;
	/*@OneToMany(targetEntity=UserStoryDTO.class,cascade=CascadeType.ALL,fetch =FetchType.EAGER)
	@JoinColumn(name="sub_task_id",referencedColumnName="id")*/
	@Column(name="user_story_id",nullable=false)
	private int userStoryId;
	@Column(name="assignedUser")
	private String assignedUser;
	@Column(name="estimatedEfforts")
	private int estimatedEfforts;
	@Column(name="consumedEfforts")
	private int consumedEfforts;
	@Column(name="remainingEfforts")
	private int remainingEfforts;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status;
	
	public SubTaskDTO(int id, String name, int userStoryId,
			String assignedUser, int estimatedEfforts, int consumedEfforts,
			int remainingEfforts, Status status) {
		super();
		this.id = id;
		this.name = name;
		this.userStoryId = userStoryId;
		this.assignedUser = assignedUser;
		this.estimatedEfforts = estimatedEfforts;
		this.consumedEfforts = consumedEfforts;
		this.remainingEfforts = remainingEfforts;
		this.status = status;
	}

	public SubTaskDTO() {
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
		return "SubTaskDTO [id=" + id + ", name=" + name + ", userStoryId="
				+ userStoryId + ", assignedUser=" + assignedUser
				+ ", estimatedEfforts=" + estimatedEfforts
				+ ", consumedEfforts=" + consumedEfforts
				+ ", remainingEfforts=" + remainingEfforts + ", status="
				+ status + "]";
	}
	
	

}
