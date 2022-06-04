package com.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.dto.SubTaskDTO;
@Repository
public interface SubTaskRepository extends JpaRepository<SubTaskDTO, Integer> {
	
	@Query(nativeQuery=true, value ="select id, name from sub_task_details where id=:id")
	public List<Map<String,Object>>  searchById (@Param("id") int id);
	
	@Query(nativeQuery=true, value ="select id, name from sub_task_details where name=:name")
	public List<Map<String,Object>>  searchByName (@Param("name") String name);

	public List<SubTaskDTO> findByUserStoryId(int userStoryId);

}
