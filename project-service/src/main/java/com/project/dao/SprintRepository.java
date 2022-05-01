package com.project.dao;

import java.util.List;
import java.util.Map;

import com.project.dto.SprintDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SprintRepository extends JpaRepository<SprintDTO,Integer> {
	
	@Query(nativeQuery=true, value ="select id, name from sprint_details where id=:id")
	public List<Map<String,Object>>  searchById (@Param("id") int id);
	
	@Query(nativeQuery=true, value ="select id, name from sprint_details where name=:name")
	public List<Map<String,Object>>  searchByName (@Param("name") String name);
}
