package com.project.queries;

public class ProjectQuery {

	public ProjectQuery(){
		super();
	}

	public static final String query="select new com.project.model.ProjectDetailsModel( p.projectId as project_id,p.projectName as project_name,p.projectType as project_type,p.managerId as manager_id, "+
			"    s.id as sprint_id,s.name as sprint_name ,s.duration as duration,s.startDate,s.endDate as end_date, s.isSprintActive , "+
			"   u.id as user_story_id,u.name as user_story_name,u.assignedUser as user_story_assignedUser ,u.storyPoints as story_points,u.acceptanceCriteria as acceptance_criteria,u.estimatedEfforts as estimated_efforts,"+
			"   u.consumedEfforts as consumed_efforts,u.remainingEfforts as remaining_efforts, "+
			"    u.status as user_story_status,u.priority,u.isBacklog as is_backlog,t.id as sub_task_id, "+
			"   t.name as sub_task_name ,t.assignedUser as sub_task_assignedUser,t.estimatedEfforts as sub_task_estimatedEfforts, "+
			"  t.consumedEfforts as sub_task_consumedEfforts,t.remainingEfforts as sub_task_remainingEfforts,t.status as sub_task_status) from ProjectDTO p "+
			"  left join SprintDTO s on p.projectId=s.projectId left join UserStoryDTO u on s.id=u.sprintId left join "+
			"  SubTaskDTO t on u.id=t.userStoryId "+
			"  where p.projectId in (:projectIds)";

}
