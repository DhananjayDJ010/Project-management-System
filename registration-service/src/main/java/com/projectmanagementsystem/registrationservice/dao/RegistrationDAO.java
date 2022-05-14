package com.projectmanagementsystem.registrationservice.dao;

import com.projectmanagementsystem.registrationservice.entity.ProjectUser;
import com.projectmanagementsystem.registrationservice.entity.ProjectUserKey;
import com.projectmanagementsystem.registrationservice.model.ProjectAccessRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationDAO extends CrudRepository<ProjectUser, ProjectUserKey> {
    ProjectUser findByEmailId(String emailId);

    @Query(nativeQuery=true, value ="select  project_id from project_role where user_id=:userId")
    public List<String> getUser(@Param("userId") String userId);
}
