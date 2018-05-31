package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.repositories.GradeRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/grades")
public class GradeController {

	@Autowired
	private GradeRepository gradRepo;
	
	@GetMapping("/")
	public List<GradeEntity> getAllGrades () {
		return (List<GradeEntity>) gradRepo.findAll();
	}
	
	@PostMapping("/")
	public GradeEntity addNewGrade (@Valid @RequestBody GradeEntity newGrade) {
		return gradRepo.save(newGrade);
	}
	
	
	
}
