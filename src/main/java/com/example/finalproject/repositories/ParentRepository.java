package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {

	ParentEntity findByUsername (String username);
	ParentEntity findByPassword (String password);
}
