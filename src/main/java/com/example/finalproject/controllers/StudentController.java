package com.example.finalproject.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.StudentDTO;
import com.example.finalproject.entities.dto.StudentUpdateDTO;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.services.StudentService;
import com.example.finalproject.utils.Encryption;
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
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private LectureRepository lectRepo;
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/active")
	public ResponseEntity<?> getAllStudentsActive() {
		if (studRepo.findAllByDeleted(false).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<StudentEntity>>((List<StudentEntity>) studRepo.findAllByDeleted(false), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/deleted")
	public ResponseEntity<?> getAllStudentsDeleted() {
		if (studRepo.findAllByDeleted(true).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<StudentEntity>>((List<StudentEntity>) studRepo.findAllByDeleted(true), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudent(@PathVariable Integer id) {
		if (!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<StudentEntity>(studRepo.findById(id).get(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/add-student")
	public ResponseEntity<?> addNewStudent(@Valid @RequestBody StudentDTO newStudent){
		return studServ.addNewStudent(newStudent);
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer studentId, @Valid @RequestBody StudentUpdateDTO newStudent) {
		return studServ.updateStudent(newStudent, studentId);
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
	public ResponseEntity<?> changeUserAndPass(@Valid @RequestBody CredentialsDTO credentials, Integer studentId) {
		return studServ.changeUserAndPass(credentials, studentId);
	}
	
	@Secured("ROLE_STUDENT")
	@PutMapping("/credentials/password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody CredentialsDTO credentials, Principal principal) {
		return studServ.changePassword(credentials, principal);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/delete-logical/{id}")
	public ResponseEntity<?> deleteStudentLogical(@PathVariable Integer id) {
		if(!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		StudentEntity student = studRepo.findById(id).get();
		student.setDeleted(true);
		student.setPassword(Encryption.getPassEncoded(student.getUsername()+"locked"));
		ParentEntity parent = student.getParent();
		parent.setDeleted(true);
		List<StudentEntity> students = studRepo.findAllByParent(parent);
		for (StudentEntity studentEntity : students) {
			if (studentEntity.getDeleted().equals(false)) {
				parent.setDeleted(false);
				break;
			}
		}
		if (parent.getDeleted().equals(true)) {
			parent.setPassword(Encryption.getPassEncoded(parent.getUsername()+"locked"));
		}
		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/undo-logical/{id}")
	public ResponseEntity<?> undoStudentLogical(@PathVariable Integer id) {
		if(!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		StudentEntity student = studRepo.findById(id).get();
		student.setDeleted(false);
		ParentEntity parent = student.getParent();
		parent.setDeleted(false);
		student.setPassword(Encryption.getPassEncoded(student.getUsername()+"unlocked"));
		parent.setPassword(Encryption.getPassEncoded(parent.getUsername()+"unlocked"));
		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.OK);
	}
/*	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete-permanent{id}")
	public ResponseEntity<?> deleteTeacherPhisically(@PathVariable Integer id) {
		if(!studRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		StudentEntity student = studRepo.findById(id).get();
		studRepo.deleteById(id);
		return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
	}
*/
	
	@Secured("ROLE_TEACHER")
	@GetMapping("/by-teacher")
	public ResponseEntity<?> getAllStudentsByTeacher(Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		List<LectureEntity> lectures = lectRepo.findAllByTeacher(teacher);
		List<StudentEntity> students = new ArrayList<>();
		for (LectureEntity lectureEntity : lectures) {
			students.addAll(studRepo.findByGrade(lectureEntity.getGrade()));
			
		}
		
		List<StudentEntity> studentsWithoutDuplciates = new ArrayList<>();
		for (StudentEntity studentEntity : students) {
		
			boolean found= false;
			for (StudentEntity studentEntityRes : studentsWithoutDuplciates) {
				if(studentEntityRes.getId().equals(studentEntity.getId())) {
					found=true;
					break;
							
				}
			}
			if(!found) {
				studentsWithoutDuplciates.add(studentEntity);
			}
		}
		if (studRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<StudentEntity>>(studentsWithoutDuplciates, HttpStatus.OK);
	}

}
