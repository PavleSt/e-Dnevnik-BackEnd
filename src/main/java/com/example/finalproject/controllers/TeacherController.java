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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.services.TeacherSerice;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/teachers")
public class TeacherController {

	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private TeacherSerice teacServ;
	
	@GetMapping("/")
	public ResponseEntity<?> gettAllTeachers() {
		if(teacRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity((List<TeacherEntity>) teacRepo.findAll(), HttpStatus.OK);
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTeacher(@PathVariable Integer id) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<TeacherEntity>(teacRepo.findById(id).get(),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addNewTeacher (@Valid @RequestBody TeacherDTO newTeacher) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setDob(newTeacher.getDob());
		teacher.setEmail(newTeacher.getEmail());
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTeacher(@PathVariable Integer id, @Valid @RequestBody TeacherDTO newTeacher) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(id).get();
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setDob(newTeacher.getDob());
		teacher.setEmail(newTeacher.getEmail());
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer id) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(id).get();
		teacRepo.deleteById(id);
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}
	
	@PutMapping("/credentials/{teacherId}")
	public ResponseEntity<?> addUserAndPass(@PathVariable Integer teacherId, 
			@Valid @RequestBody CredentialsDTO credentials) {
		return teacServ.addUserAndPass(teacherId, credentials);
	}
	
}
