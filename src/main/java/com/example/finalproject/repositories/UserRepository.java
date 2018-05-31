package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
