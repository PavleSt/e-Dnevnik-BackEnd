package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/grades")
public class GradeController {

	@Autowired
	private GradeRepository gradRepo;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllGrades() {
		if (gradRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<GradeEntity>>((List<GradeEntity>) gradRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getGrade(@PathVariable Integer id) {
		if(!gradRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<GradeEntity>(gradRepo.findById(id).get(),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addNewGrade(@Valid @RequestBody GradeEntity newGrade) {
		GradeEntity grade = new GradeEntity();
		grade.setYear(newGrade.getYear());
		grade.setClassroom(newGrade.getClassroom());
		return new ResponseEntity<GradeEntity>(gradRepo.save(grade),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGrade(@PathVariable Integer id, @Valid @RequestBody GradeEntity updGrade) {
		if(!gradRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
		}
		GradeEntity grade = new GradeEntity();
		grade.setYear(updGrade.getYear());
		grade.setClassroom(updGrade.getClassroom());
		return new ResponseEntity<GradeEntity>(gradRepo.save(grade),HttpStatus.OK);
	}
	
	
}