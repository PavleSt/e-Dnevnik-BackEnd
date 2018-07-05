package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
	
	TeacherEntity findByUsername (String username);
	TeacherEntity findByPassword (String password);
	TeacherEntity findByEmail (String email);
	TeacherEntity existsByUsername (String username);

}
