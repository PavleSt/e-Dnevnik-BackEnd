package com.example.finalproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
	
	TeacherEntity findByUsername (String username);
	TeacherEntity findByPassword (String password);
	TeacherEntity findByEmail (String email);
	//TeacherEntity existsByUsername (String username);
	List<TeacherEntity> findAllByDeleted (Boolean deleted);
	


}
