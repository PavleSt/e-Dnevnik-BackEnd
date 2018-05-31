package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

}
