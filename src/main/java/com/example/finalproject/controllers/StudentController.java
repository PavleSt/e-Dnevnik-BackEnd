package com.example.finalproject.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.StudentDTO;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.services.StudentService;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/students")
public class StudentController {

	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private StudentService studServ;
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private GradeRepository gradRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/")
	public ResponseEntity<?> getAllStudents() {
		if (studRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<StudentEntity>>((List<StudentEntity>) studRepo.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudent(@PathVariable Integer id) {
		if (studRepo.existsById(id)) {
			return new ResponseEntity<StudentEntity>(studRepo.findById(id).get(), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public ResponseEntity<?> addNewStudent(@Valid @RequestBody StudentDTO newStudent){
		return studServ.addNewStudent(newStudent);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO newStudent,
			@RequestParam Integer parentId, @RequestParam Integer gradeId) {
		StudentEntity student = studRepo.findById(id).get();
		if (!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		student.setFirstName(newStudent.getFirstName());
		student.setLastName(newStudent.getLastName());
		student.setDob(newStudent.getDob());
		if (!pareRepo.existsById(parentId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		} else {
			student.setParent(pareRepo.findById(parentId).get());
		}
		if (!gradRepo.existsById(gradeId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		} else {
			student.setGrade(gradRepo.findById(gradeId).get());
		}
		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		StudentEntity student = studRepo.findById(id).get();
		if (!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		pareRepo.deleteById(id);
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/credentials")
	public ResponseEntity<?> changeUserAndPass(@Valid @RequestBody CredentialsDTO credentials, Principal principal) {
		return studServ.changeUserAndPass(credentials, principal);
	}
	
	@Secured("ROLE_STUDENT")
	@PutMapping("/credentials/password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody CredentialsDTO credentials, Principal principal) {
		return studServ.changePassword(credentials, principal);
	}
	

}
