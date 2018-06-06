package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

	StudentEntity findByUsername (String username);
	StudentEntity findByPassword (String password);
	StudentEntity findByGrade (GradeEntity grade);
}
