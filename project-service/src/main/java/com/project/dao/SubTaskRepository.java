package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dto.SubTaskDTO;
@Repository
public interface SubTaskRepository extends JpaRepository<SubTaskDTO, Integer> {

}
