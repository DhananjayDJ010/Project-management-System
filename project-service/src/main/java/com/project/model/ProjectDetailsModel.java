package com.project.model;

public class ProjectDetailsModel {
	 private int project_id;
	 private String project_name;
	 private String project_type;
	 private String project_owner;
	 private int sprint_id;
	 private String sprint_name;
	 private String duration;
	 private String startDate;
	 private String end_date;
	 private boolean isSprintActive;
	 private int user_story_id;
	 private String user_story_name;
	 private String user_story_assignedUser;
	 private String acceptance_criteria;
	 private int estimated_efforts;
	 private int consumed_efforts;
	 private int remaining_efforts;
	 private String user_story_status;
	 private String priority;
	 private boolean isBacklog;
	 private int sub_task_id;
	 private String sub_task_name;
	 private String sub_task_assignedUser;
	 private int sub_task_estimatedEfforts;
	 private int sub_task_consumedEfforts;
	 private int sub_task_remainingEfforts;
	 private String sub_task_status ;
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getProject_owner() {
		return project_owner;
	}
	public void setProject_owner(String project_owner) {
		this.project_owner = project_owner;
	}
	public int getSprint_id() {
		return sprint_id;
	}
	public void setSprint_id(int sprint_id) {
		this.sprint_id = sprint_id;
	}
	public String getSprint_name() {
		return sprint_name;
	}
	public void setSprint_name(String sprint_name) {
		this.sprint_name = sprint_name;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getUser_story_id() {
		return user_story_id;
	}
	public void setUser_story_id(int user_story_id) {
		this.user_story_id = user_story_id;
	}
	public String getUser_story_name() {
		return user_story_name;
	}
	public void setUser_story_name(String user_story_name) {
		this.user_story_name = user_story_name;
	}
	public String getUser_story_assignedUser() {
		return user_story_assignedUser;
	}
	public void setUser_story_assignedUser(String user_story_assignedUser) {
		this.user_story_assignedUser = user_story_assignedUser;
	}
	public String getAcceptance_criteria() {
		return acceptance_criteria;
	}
	public void setAcceptance_criteria(String acceptance_criteria) {
		this.acceptance_criteria = acceptance_criteria;
	}
	public int getEstimated_efforts() {
		return estimated_efforts;
	}
	public void setEstimated_efforts(int estimated_efforts) {
		this.estimated_efforts = estimated_efforts;
	}
	public int getConsumed_efforts() {
		return consumed_efforts;
	}
	public void setConsumed_efforts(int consumed_efforts) {
		this.consumed_efforts = consumed_efforts;
	}
	public int getRemaining_efforts() {
		return remaining_efforts;
	}
	public void setRemaining_efforts(int remaining_efforts) {
		this.remaining_efforts = remaining_efforts;
	}
	public String getUser_story_status() {
		return user_story_status;
	}
	public void setUser_story_status(String user_story_status) {
		this.user_story_status = user_story_status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public boolean isBacklog() {
		return isBacklog;
	}
	public void setBacklog(boolean isBacklog) {
		this.isBacklog = isBacklog;
	}
	public int getSub_task_id() {
		return sub_task_id;
	}
	public void setSub_task_id(int sub_task_id) {
		this.sub_task_id = sub_task_id;
	}
	public String getSub_task_name() {
		return sub_task_name;
	}
	public void setSub_task_name(String sub_task_name) {
		this.sub_task_name = sub_task_name;
	}
	public String getSub_task_assignedUser() {
		return sub_task_assignedUser;
	}
	public void setSub_task_assignedUser(String sub_task_assignedUser) {
		this.sub_task_assignedUser = sub_task_assignedUser;
	}
	public int getSub_task_estimatedEfforts() {
		return sub_task_estimatedEfforts;
	}
	public void setSub_task_estimatedEfforts(int sub_task_estimatedEfforts) {
		this.sub_task_estimatedEfforts = sub_task_estimatedEfforts;
	}
	public int getSub_task_consumedEfforts() {
		return sub_task_consumedEfforts;
	}
	public void setSub_task_consumedEfforts(int sub_task_consumedEfforts) {
		this.sub_task_consumedEfforts = sub_task_consumedEfforts;
	}
	public int getSub_task_remainingEfforts() {
		return sub_task_remainingEfforts;
	}
	public void setSub_task_remainingEfforts(int sub_task_remainingEfforts) {
		this.sub_task_remainingEfforts = sub_task_remainingEfforts;
	}
	public String getSub_task_status() {
		return sub_task_status;
	}
	public void setSub_task_status(String sub_task_status) {
		this.sub_task_status = sub_task_status;
	}
	
	public boolean isSprintActive() {
		return isSprintActive;
	}
	public void setSprintActive(boolean isSprintActive) {
		this.isSprintActive = isSprintActive;
	}
	@Override
	public String toString() {
		return "ProjectDetailsModel [project_id=" + project_id
				+ ", project_name=" + project_name + ", project_type="
				+ project_type + ", project_owner=" + project_owner
				+ ", sprint_id=" + sprint_id + ", sprint_name=" + sprint_name
				+ ", duration=" + duration + ", startDate=" + startDate
				+ ", end_date=" + end_date + ", user_story_id=" + user_story_id
				+ ", user_story_name=" + user_story_name
				+ ", user_story_assignedUser=" + user_story_assignedUser
				+ ", acceptance_criteria=" + acceptance_criteria
				+ ", estimated_efforts=" + estimated_efforts
				+ ", consumed_efforts=" + consumed_efforts
				+ ", remaining_efforts=" + remaining_efforts
				+ ", user_story_status=" + user_story_status + ", priority="
				+ priority + ", isBacklog=" + isBacklog + ", sub_task_id="
				+ sub_task_id + ", sub_task_name=" + sub_task_name
				+ ", sub_task_assignedUser=" + sub_task_assignedUser
				+ ", sub_task_estimatedEfforts=" + sub_task_estimatedEfforts
				+ ", sub_task_consumedEfforts=" + sub_task_consumedEfforts
				+ ", sub_task_remainingEfforts=" + sub_task_remainingEfforts
				+ ", sub_task_status=" + sub_task_status + "]";
	}
	 
	 
	
	 

}
