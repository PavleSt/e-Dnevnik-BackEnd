package com.example.finalproject.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.security.Principal;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.services.FileHandler;
import com.example.finalproject.services.TeacherService;
import com.example.finalproject.utils.Encryption;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/teachers")
public class TeacherController {

	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private TeacherService teacServ;
	@Autowired
	private LectureRepository lectRepo;
	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private FileHandler fileHand;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/active")
	public ResponseEntity<?> gettAllTeachersActive() {	
		if(teacRepo.findAllByDeleted(false).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<TeacherEntity>>((List<TeacherEntity>) teacRepo.findAllByDeleted(false), HttpStatus.OK);
	} 
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/deleted")
	public ResponseEntity<?> gettAllTeachersDeleted() {
		if(teacRepo.findAllByDeleted(true).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<TeacherEntity>>((List<TeacherEntity>) teacRepo.findAllByDeleted(true), HttpStatus.OK);
	} 
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<?> getTeacher(@PathVariable Integer id) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<TeacherEntity>(teacRepo.findById(id).get(),HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/add-teacher")
	public ResponseEntity<?> addNewTeacher(@Valid @RequestBody TeacherDTO newTeacher) {
		logger.info("Teacher added successfully!");
		return teacServ.addNewTeacher(newTeacher);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/{teacherId}")
	public ResponseEntity<?> updateTeacher(@PathVariable Integer teacherId, @Valid @RequestBody TeacherUpdateDTO newTeacher) {
		logger.info("Teacher updated successfully!");
		return teacServ.updateTeacher(newTeacher, teacherId);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/credentials/{teacherId}")
	public ResponseEntity<?> changeUserAndPass(@Valid @RequestBody CredentialsDTO credentials, @PathVariable Integer teacherId) {
		return teacServ.changeUserAndPass(credentials, teacherId);
	}
	
	@Secured("ROLE_TEACHER")
	@PutMapping("/credentials/password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody CredentialsDTO credentials, Principal principal) {
		return teacServ.changePassword(credentials, principal);
	}
	
	//@Secured("ROLE_TEACHER")
	@GetMapping("/subjects/{teacherId}")
	public ResponseEntity<?> returnAllTeacherSubjects(@PathVariable Integer teacherId) {
			return teacServ.getAllTeacherSubjects(teacherId);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/delete-logical/{id}")
	public ResponseEntity<?> deleteTeacherLogical(@PathVariable Integer id, @RequestParam Integer newId) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(id).get();
		TeacherEntity teacherNew = teacRepo.findById(newId).get();
		teacher.setDeleted(true);
		teacher.setPassword(Encryption.getPassEncoded(teacher.getUsername()+"locked"));
		List<LectureEntity> lectures = lectRepo.findAllByTeacher(teacher);
		for (LectureEntity lectureEntity : lectures) {
			lectureEntity.setTeacher(teacherNew);
		}
		logger.info("Teacher deleted successfully!");
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/undo-logical/{id}")
	public ResponseEntity<?> undoTeacherLogical(@PathVariable Integer id) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(id).get();
		teacher.setDeleted(false);
		teacher.setPassword(Encryption.getPassEncoded(teacher.getUsername()+"unlocked"));
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete-permanent/{id}")
	public ResponseEntity<?> deleteTeacherPhisically(@PathVariable Integer id) {
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(id).get();
		teacRepo.deleteById(id);
		logger.info("Teacher permanently deleted!");
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}
	
	
/*
	@Secured("ROLE_TEACHER")
	@GetMapping("/just-checkig-principal")
	public TeacherEntity checkPrincipal(Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		return teacher;
	}
*/	
}
