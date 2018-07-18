package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.Encryption;
import com.example.finalproject.utils.RESTError;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Override
	public ResponseEntity<?> addNewParent(ParentDTO newParent) {
		ParentEntity parent = new ParentEntity();
		
		parent.setFirstName(newParent.getFirstName());
		parent.setLastName(newParent.getLastName());
		parent.setDob(newParent.getDob());
		parent.setRole(roleRepo.findByName("ROLE_PARENT"));
		parent.setDeleted(false);
		
		if (!(pareRepo.findByEmail(newParent.getEmail()) == null) ||
				!(teacRepo.findByEmail(newParent.getEmail()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Email address already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			parent.setEmail(newParent.getEmail());
		}
		
		if (!(pareRepo.findByUsername(newParent.getUsername()) == null) || 
				!(teacRepo.findByUsername(newParent.getUsername()) == null) ||
				!(studRepo.findByUsername(newParent.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			parent.setUsername(newParent.getUsername());
		}
		
		if (!(newParent.getPassword().equals(newParent.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			parent.setPassword(Encryption.getPassEncoded(newParent.getPassword()));
		}
		
		return new ResponseEntity<ParentEntity>(pareRepo.save(parent),HttpStatus.CREATED);
	}
	
	
	@Override
	public ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal) {
		if (pareRepo.findByUsername(credentials.getUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		
		if (!(credentials.getUsername().equals(principal.getName()))) {
			return new ResponseEntity<RESTError>(new RESTError(10, "Wrong username entered"), HttpStatus.UNAUTHORIZED);
		}
		
		ParentEntity parent = pareRepo.findByUsername(credentials.getUsername());
		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), parent.getPassword()) == false) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password entered"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera slaganja passworda
		if (!(credentials.getPasswordNew().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		parent.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));

		return new ResponseEntity<ParentEntity>(pareRepo.save(parent), HttpStatus.OK);
	}

}
