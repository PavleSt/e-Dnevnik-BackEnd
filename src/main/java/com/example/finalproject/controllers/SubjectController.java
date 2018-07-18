package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjRepo;
	
	@GetMapping("/")
	public ResponseEntity<?> gettAllSubjects() {
		if(subjRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<SubjectEntity>>((List<SubjectEntity>) subjRepo.findAll(), HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getSubject(@PathVariable Integer id) {
		if(!subjRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubjectEntity>(subjRepo.findById(id).get(),HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectEntity newSubject) {
		SubjectEntity subject = new SubjectEntity();
		if (subjRepo.findBySubjectName(newSubject.getSubjectName()) != null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject already exists!"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		subject.setSubjectName(newSubject.getSubjectName());
		subject.setWeeklyLectures(newSubject.getWeeklyLectures());	
		return new ResponseEntity<SubjectEntity>(subjRepo.save(subject),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSubject(@PathVariable Integer id, @Valid @RequestBody SubjectEntity updSubject) {
		if(!subjRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjRepo.findById(id).get();
		subject.setSubjectName(updSubject.getSubjectName());
		subject.setWeeklyLectures(updSubject.getWeeklyLectures());	
		return new ResponseEntity<SubjectEntity>(subjRepo.save(subject),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
		if(!subjRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjRepo.findById(id).get();
		subjRepo.deleteById(id);
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}
}
