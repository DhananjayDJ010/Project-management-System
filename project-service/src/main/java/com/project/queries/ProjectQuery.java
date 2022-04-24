package com.project.queries;

public class ProjectQuery {
	
	public ProjectQuery(){
		super();
	}
	
	public static final String query="select p.id as project_id,p.name as project_name,p.type as project_type,p.owner as project_owner,"+
" s.id as sprint_id,s.name as sprint_name ,s.duration as duration,s.start_date,s.end_date,"+
" u.id as user_story_id,u.name as user_story_name,u.assigned_user as user_story_assignedUser ,u.acceptance_criteria,u.story_points,u.estimated_efforts,u.consumed_efforts,u.remaining_efforts,"+
" u.status as user_story_status,u.priority,u.is_Backlog,t.id as sub_task_id,"+
" t.name as sub_task_name ,t.assigned_user as sub_task_assignedUser,t.estimated_efforts as sub_task_estimatedEfforts,"+
" t.consumed_efforts as sub_task_consumedEfforts,t.remaining_efforts as sub_task_remainingEfforts,t.status as sub_task_status from project_details p"+
" inner join sprint_details s on p.id=s.project_id inner join user_story_details u on s.id=u.sprint_id inner join"+
" sub_task_details t on u.id=t.user_story_id";

}
