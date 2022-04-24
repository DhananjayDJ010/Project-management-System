package com.project.dao;

import java.util.List;
import java.util.Map;

import com.project.model.ProjectDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.dto.ProjectDTO;
import com.project.model.ProjectDetailsModel;
import com.project.queries.ProjectQuery;

public interface ProjectRepository extends JpaRepository<ProjectDTO, String> {
	
	@Query(nativeQuery=true, value="select from project_details where id in(:projectIds)")
	public List<ProjectDTO> findProjectDetailsById(@Param("projectIds") List<String> projectIds);
	
	@Query(nativeQuery=true, value=ProjectQuery.query)
	public List<ProjectDetailsModel> getAllDetails();

	public List<ProjectDTO> findByManagerId(String managerId);

}
