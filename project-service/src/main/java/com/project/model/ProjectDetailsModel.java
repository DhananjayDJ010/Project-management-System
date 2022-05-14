package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.dto.Priority;
import com.project.dto.Status;

import java.util.Date;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetailsModel {
	private String project_id;
	private String project_name;
	private ProjectType project_type;
	private String manager_id;
	private Integer sprint_id;
	private String sprint_name;
	private String duration;
	private Date startDate;
	private Date end_date;
	private Boolean isSprintActive;
	private Integer user_story_id;
	private String user_story_name;
	private String user_story_assignedUser;
	private Integer story_points;
	private String acceptance_criteria;
	private Integer estimated_efforts;
	private Integer consumed_efforts;
	private Integer remaining_efforts;
	private Status user_story_status;
	private Priority priority;
	private Boolean isBacklog;
	private Integer sub_task_id;
	private String sub_task_name;
	private String sub_task_assignedUser;
	private Integer sub_task_estimatedEfforts;
	private Integer sub_task_consumedEfforts;
	private Integer sub_task_remainingEfforts;
	private Status sub_task_status;

	public String getProject_id() {
		return project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public ProjectType getProject_type() {
		return project_type;
	}

	public String getManager_id() {
		return manager_id;
	}

	public Integer getSprint_id() {
		return sprint_id;
	}

	public String getSprint_name() {
		return sprint_name;
	}

	public String getDuration() {
		return duration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public Boolean getSprintActive() {
		return isSprintActive;
	}

	public Integer getUser_story_id() {
		return user_story_id;
	}

	public String getUser_story_name() {
		return user_story_name;
	}

	public String getUser_story_assignedUser() {
		return user_story_assignedUser;
	}

	public Integer getStory_points() {
		return story_points;
	}

	public String getAcceptance_criteria() {
		return acceptance_criteria;
	}

	public Integer getEstimated_efforts() {
		return estimated_efforts;
	}

	public Integer getConsumed_efforts() {
		return consumed_efforts;
	}

	public Integer getRemaining_efforts() {
		return remaining_efforts;
	}

	public Status getUser_story_status() {
		return user_story_status;
	}

	public Priority getPriority() {
		return priority;
	}

	public Boolean getBacklog() {
		return isBacklog;
	}

	public Integer getSub_task_id() {
		return sub_task_id;
	}

	public String getSub_task_name() {
		return sub_task_name;
	}

	public String getSub_task_assignedUser() {
		return sub_task_assignedUser;
	}

	public Integer getSub_task_estimatedEfforts() {
		return sub_task_estimatedEfforts;
	}

	public Integer getSub_task_consumedEfforts() {
		return sub_task_consumedEfforts;
	}

	public Integer getSub_task_remainingEfforts() {
		return sub_task_remainingEfforts;
	}

	public Status getSub_task_status() {
		return sub_task_status;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public void setProject_type(ProjectType project_type) {
		this.project_type = project_type;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public void setSprint_id(Integer sprint_id) {
		this.sprint_id = sprint_id;
	}

	public void setSprint_name(String sprint_name) {
		this.sprint_name = sprint_name;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public void setSprintActive(Boolean sprintActive) {
		isSprintActive = sprintActive;
	}

	public void setUser_story_id(Integer user_story_id) {
		this.user_story_id = user_story_id;
	}

	public void setUser_story_name(String user_story_name) {
		this.user_story_name = user_story_name;
	}

	public void setUser_story_assignedUser(String user_story_assignedUser) {
		this.user_story_assignedUser = user_story_assignedUser;
	}

	public void setStory_points(Integer story_points) {
		this.story_points = story_points;
	}

	public void setAcceptance_criteria(String acceptance_criteria) {
		this.acceptance_criteria = acceptance_criteria;
	}

	public void setEstimated_efforts(Integer estimated_efforts) {
		this.estimated_efforts = estimated_efforts;
	}

	public void setConsumed_efforts(Integer consumed_efforts) {
		this.consumed_efforts = consumed_efforts;
	}

	public void setRemaining_efforts(Integer remaining_efforts) {
		this.remaining_efforts = remaining_efforts;
	}

	public void setUser_story_status(Status user_story_status) {
		this.user_story_status = user_story_status;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setBacklog(Boolean backlog) {
		isBacklog = backlog;
	}

	public void setSub_task_id(Integer sub_task_id) {
		this.sub_task_id = sub_task_id;
	}

	public void setSub_task_name(String sub_task_name) {
		this.sub_task_name = sub_task_name;
	}

	public void setSub_task_assignedUser(String sub_task_assignedUser) {
		this.sub_task_assignedUser = sub_task_assignedUser;
	}

	public void setSub_task_estimatedEfforts(Integer sub_task_estimatedEfforts) {
		this.sub_task_estimatedEfforts = sub_task_estimatedEfforts;
	}

	public void setSub_task_consumedEfforts(Integer sub_task_consumedEfforts) {
		this.sub_task_consumedEfforts = sub_task_consumedEfforts;
	}

	public void setSub_task_remainingEfforts(Integer sub_task_remainingEfforts) {
		this.sub_task_remainingEfforts = sub_task_remainingEfforts;
	}

	public void setSub_task_status(Status sub_task_status) {
		this.sub_task_status = sub_task_status;
	}

	public ProjectDetailsModel(){}

	public ProjectDetailsModel(String project_id, String project_name, ProjectType project_type, String manager_id, Integer sprint_id, String sprint_name, String duration, Date startDate, Date end_date, Boolean isSprintActive, Integer user_story_id, String user_story_name, String user_story_assignedUser, Integer story_points, String acceptance_criteria, Integer estimated_efforts, Integer consumed_efforts, Integer remaining_efforts, Status user_story_status, Priority priority, Boolean isBacklog, Integer sub_task_id, String sub_task_name, String sub_task_assignedUser, Integer sub_task_estimatedEfforts, Integer sub_task_consumedEfforts, Integer sub_task_remainingEfforts, Status sub_task_status) {
		this.project_id = project_id;
		this.project_name = project_name;
		this.project_type = project_type;
		this.manager_id = manager_id;
		this.sprint_id = sprint_id;
		this.sprint_name = sprint_name;
		this.duration = duration;
		this.startDate = startDate;
		this.end_date = end_date;
		this.isSprintActive = isSprintActive;
		this.user_story_id = user_story_id;
		this.user_story_name = user_story_name;
		this.user_story_assignedUser = user_story_assignedUser;
		this.story_points = story_points;
		this.acceptance_criteria = acceptance_criteria;
		this.estimated_efforts = estimated_efforts;
		this.consumed_efforts = consumed_efforts;
		this.remaining_efforts = remaining_efforts;
		this.user_story_status = user_story_status;
		this.priority = priority;
		this.isBacklog = isBacklog;
		this.sub_task_id = sub_task_id;
		this.sub_task_name = sub_task_name;
		this.sub_task_assignedUser = sub_task_assignedUser;
		this.sub_task_estimatedEfforts = sub_task_estimatedEfforts;
		this.sub_task_consumedEfforts = sub_task_consumedEfforts;
		this.sub_task_remainingEfforts = sub_task_remainingEfforts;
		this.sub_task_status = sub_task_status;
	}
}
