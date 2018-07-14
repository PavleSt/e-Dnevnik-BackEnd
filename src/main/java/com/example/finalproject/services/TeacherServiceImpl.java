package com.example.finalproject.services;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.Encryption;
import com.example.finalproject.utils.RESTError;

@Service
public class TeacherServiceImpl implements TeacherSerice {

	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private RoleRepository roleRepo;


	@Override
	public ResponseEntity<?> addNewTeacher(TeacherDTO newTeacher) {
		TeacherEntity teacher = new TeacherEntity();
		
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setDob(newTeacher.getDob());
		teacher.setRole(roleRepo.findByName("ROLE_TEACHER"));
		teacher.setDeleted(false);
		
		if (!(teacRepo.findByEmail(newTeacher.getEmail()) == null) ||
				!(pareRepo.findByEmail(newTeacher.getEmail()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Email address already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			teacher.setEmail(newTeacher.getEmail());
		}
		
		if (!(teacRepo.findByUsername(newTeacher.getUsername()) == null) || 
				!(pareRepo.findByUsername(newTeacher.getUsername()) == null) ||
				!(studRepo.findByUsername(newTeacher.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			teacher.setUsername(newTeacher.getUsername());
		}
		
		if (!(newTeacher.getPassword().equals(newTeacher.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
		}
		
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Principal principal) {
		if (teacRepo.findByUsername(credentials.getUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		
		if (!(credentials.getUsername().equals(principal.getName()))) {
			return new ResponseEntity<RESTError>(new RESTError(10, "Wrong username"), HttpStatus.UNAUTHORIZED);
		}
		
		TeacherEntity teacher = teacRepo.findByUsername(credentials.getUsername());
		
		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), teacher.getPassword()) == false) {
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

		teacher.setUsername(credentials.getUsernameNew());
		teacher.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));

		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal) {
		if (teacRepo.findByUsername(credentials.getUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		
		if (!(credentials.getUsername().equals(principal.getName()))) {
			return new ResponseEntity<RESTError>(new RESTError(10, "Wrong username entered"), HttpStatus.UNAUTHORIZED);
		}
		
		TeacherEntity teacher = teacRepo.findByUsername(credentials.getUsername());
		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), teacher.getPassword()) == false) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password entered"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera slaganja passworda
		if (!(credentials.getPasswordNew().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		teacher.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));

		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	}
