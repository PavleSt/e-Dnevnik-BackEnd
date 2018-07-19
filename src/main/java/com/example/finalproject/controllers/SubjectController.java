package com.example.finalproject.controllers;

import java.util.ArrayList;
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

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.SubjectDTO;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.services.SubjectService;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private SubjectService subjServ;
	@Autowired
	private LectureRepository lectRepo;
	
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
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectDTO newSubject) {
		return subjServ.addNewSubject(newSubject);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSubject(@PathVariable Integer subjectId, @Valid @RequestBody SubjectDTO newSubject) {
		return subjServ.updateSubject(newSubject, subjectId);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/teachers/{subjectId}")
	public ResponseEntity<?> returnAllSubjectTeachers(@PathVariable Integer subjectId) {
			return subjServ.getAllSubjectTeachers(subjectId);
	}
/*	
	//str.substring(0, str.length() - 2);
	@Secured("ROLE_ADMIN")
	@GetMapping("/teachers/subject-root/{subjecName}")
	public ResponseEntity<?> returnAllSubjectTeachers2(@PathVariable String subjectName) {
		if(subjRepo.findBySubjectNameContaining(subjectName)==null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		List<SubjectEntity> subjects = ((List<SubjectEntity>) subjRepo.findBySubjectNameContaining(subjectName));	
		if (lectRepo.findAllBySubject(subjects) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject is not being teached at any lectures"), HttpStatus.NOT_FOUND);
		}
		List<LectureEntity> lectures = lectRepo.findAllBySubject(subjects);
		List<TeacherEntity> teachers = new ArrayList<TeacherEntity>();
		for (LectureEntity lect : lectures) {
			teachers.add(lect.getTeacher());
		}
		return new ResponseEntity<List<TeacherEntity>>(teachers, HttpStatus.OK);
	}
*/	
}
