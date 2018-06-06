package com.example.finalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.RESTError;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private StudentRepository studRepo;
	
	@Override
	public ResponseEntity<?> addUserAndPass(Integer parentId, CredentialsDTO credentials) {
		if(!pareRepo.existsById(parentId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		if (!(pareRepo.findByUsername(credentials.getUsername()) == null) || 
				!(teacRepo.findByUsername(credentials.getUsername()) == null) ||
				!(studRepo.findByUsername(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		
		if (!(pareRepo.findByPassword(credentials.getPassword()) == null) ||
				!(teacRepo.findByPassword(credentials.getUsername()) == null) ||
				!(studRepo.findByPassword(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		ParentEntity parent = pareRepo.findById(parentId).get();
		parent.setUsername(credentials.getUsername());
		parent.setPassword(credentials.getPassword());
		
		return new ResponseEntity<ParentEntity>(pareRepo.save(parent),HttpStatus.OK);
	}
}
