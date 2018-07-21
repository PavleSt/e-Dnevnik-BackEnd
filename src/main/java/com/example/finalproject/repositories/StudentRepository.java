package com.example.finalproject.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

	StudentEntity findByUsername (String username);
	List<StudentEntity> findAllByDeleted(Boolean deleted);
	StudentEntity findByFirstNameAndLastNameAndGrade (String firstName, String lastName, GradeEntity grade);
	List<StudentEntity> findByGrade (GradeEntity grade);
}
