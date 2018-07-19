package com.example.finalproject.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.TeacherEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {

	ParentEntity findByUsername (String username);
	ParentEntity findByEmail (String email);
	List<ParentEntity> findAllByDeleted(Boolean deleted);
}
