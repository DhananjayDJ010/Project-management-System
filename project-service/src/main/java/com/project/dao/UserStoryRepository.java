package com.project.dao;

import java.util.List;
import java.util.Map;

import com.project.model.UserStoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.dto.Status;
import com.project.dto.UserStoryDTO;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryDTO,Integer>{
	
  @Modifying
  @Query(nativeQuery=true , value="update user_story_details   set status=:status , is_Backlog=false , sprint_id =:sprintId where id in (:ids) returning id")
	public List<Integer> setStatusById(@Param("status") String status, @Param("ids")List<Integer>ids, @Param("sprintId") int sprintId);
  
  @Query(nativeQuery=true, value ="select id, name from user_story_details where id=:id")
	public List<Map<String,Object>>  searchById (@Param("id") int id);
	
	@Query(nativeQuery=true, value ="select id, name from user_story_details where name=:name")
	public List<Map<String,Object>>  searchByName (@Param("name") String name);


	public List<UserStoryDTO> findBySprintId(int sprintId);

	@Query(nativeQuery=true, value ="select * from user_story_details where project_id=:project_id and is_backlog = true")
	public List<UserStoryDTO> findUserStoryInBacklog(@Param("project_id")String projectId);

}
