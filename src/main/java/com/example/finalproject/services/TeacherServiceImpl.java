package com.example.finalproject.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.RESTError;

@Service
public class TeacherServiceImpl implements TeacherSerice {

	@Autowired
	TeacherRepository teacRepo;
	
	@Override
	public ResponseEntity<?> addUserAndPass(Integer teacherId, CredentialsDTO credentials) {
		if(!teacRepo.existsById(teacherId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		if (!(teacRepo.findByUsername(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		if (!(teacRepo.findByPassword(credentials.getPassword()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		TeacherEntity teacher = teacRepo.findById(teacherId).get();
		teacher.setUsername(credentials.getUsername());
		teacher.setPassword(credentials.getPassword());
		
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.OK);
	}

	
}
