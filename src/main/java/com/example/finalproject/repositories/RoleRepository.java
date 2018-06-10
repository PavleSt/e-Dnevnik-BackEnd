package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

	RoleEntity findByName(String name);
}
