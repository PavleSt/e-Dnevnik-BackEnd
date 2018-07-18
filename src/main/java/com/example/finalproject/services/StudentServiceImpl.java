package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.StudentDTO;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.Encryption;
import com.example.finalproject.utils.RESTError;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private GradeRepository gradRepo;
	
	
	@Override
	public ResponseEntity<?> addNewStudent(StudentDTO newStudent) {
		StudentEntity student = new StudentEntity();
		
		student.setFirstName(newStudent.getFirstName());
		student.setLastName(newStudent.getLastName());
		student.setDob(newStudent.getDob());
		student.setRole(roleRepo.findByName("ROLE_STUDENT"));
		student.setDeleted(false);
		
		if (!pareRepo.existsById(newStudent.getParentId())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent entity not found"), HttpStatus.NOT_FOUND);
		} else {
			student.setParent(pareRepo.findById(newStudent.getParentId()).get());
		}
		if (!gradRepo.existsById(newStudent.getGradeId())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade entity not found"), HttpStatus.NOT_FOUND);
		} else {
			student.setGrade(gradRepo.findById(newStudent.getGradeId()).get());
		}
		
		if (!(studRepo.findByUsername(newStudent.getUsername()) == null) || 
				!(pareRepo.findByUsername(newStudent.getUsername()) == null) ||
				!(teacRepo.findByUsername(newStudent.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			student.setUsername(newStudent.getUsername());
		}
		
		if (!(newStudent.getPassword().equals(newStudent.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			student.setPassword(Encryption.getPassEncoded(newStudent.getPassword()));
		}
		
		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.CREATED);
	}
	
	
	
	@Override
	public ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Principal principal) {
		if (teacRepo.findByUsername(credentials.getUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		
		if (!(credentials.getUsername().equals(principal.getName()))) {
			return new ResponseEntity<RESTError>(new RESTError(10, "Wrong username"), HttpStatus.UNAUTHORIZED);
		}
		
		StudentEntity student = studRepo.findByUsername(credentials.getUsername());
		
		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), student.getPassword()) == false) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		/*if (!(teacher.getPassword().equals(credentials.getPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password"), HttpStatus.UNPROCESSABLE_ENTITY);
		}*/
		// provera za novi username
		if (!(teacRepo.findByUsername(credentials.getUsernameNew()) == null)
				|| !(pareRepo.findByUsername(credentials.getUsernameNew()) == null)
				|| !(studRepo.findByUsername(credentials.getUsernameNew()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera lsaganja passworda
		if (!(credentials.getPasswordNew().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		student.setUsername(credentials.getUsernameNew());
		student.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));

		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.OK);
		
	}



	@Override
	public ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal) {
		if (studRepo.findByUsername(credentials.getUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		
		if (!(credentials.getUsername().equals(principal.getName()))) {
			return new ResponseEntity<RESTError>(new RESTError(10, "Wrong username entered"), HttpStatus.UNAUTHORIZED);
		}
		
		StudentEntity student = studRepo.findByUsername(credentials.getUsername());
		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), student.getPassword()) == false) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password entered"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera slaganja passworda
		if (!(credentials.getPasswordNew().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		student.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));

		return new ResponseEntity<StudentEntity>(studRepo.save(student), HttpStatus.OK);
	}
	
}
	
	
		
		


