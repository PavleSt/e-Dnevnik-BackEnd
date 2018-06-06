package com.example.finalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.RESTError;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private ParentRepository pareRepo;
	
	@Override
	public ResponseEntity<?> addUserAndPass(Integer studentId, CredentialsDTO credentials) {
		if(!studRepo.existsById(studentId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		if (!(studRepo.findByUsername(credentials.getUsername()) == null) || 
				!(pareRepo.findByUsername(credentials.getUsername()) == null) ||
				!(teacRepo.findByUsername(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		
		if (!(studRepo.findByPassword(credentials.getPassword()) == null) ||
				!(pareRepo.findByPassword(credentials.getUsername()) == null) ||
				!(teacRepo.findByPassword(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		StudentEntity student = studRepo.findById(studentId).get();
		student.setUsername(credentials.getUsername());
		student.setPassword(credentials.getPassword());
		
		return new ResponseEntity<StudentEntity>(studRepo.save(student),HttpStatus.OK);
	}
		
		

}
