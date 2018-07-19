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
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.ParentUpdateDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.services.ParentService;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/final-project/parents")
public class ParentController {
	
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private ParentService pareServ;

	
	@Secured("ROLE_ADMIN")
	@GetMapping("/active")
	public ResponseEntity<?> getAllParentsActive() {
		if(pareRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ParentEntity>>((List<ParentEntity>) pareRepo.findAllByDeleted(false), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/deleted")
	public ResponseEntity<?> getAllParentsDeleted() {
		if(pareRepo.count() == 0) {
			return new ResponseEntity<RESTError>(new RESTError(4, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ParentEntity>>((List<ParentEntity>) pareRepo.findAllByDeleted(true), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<?> getParent(@PathVariable Integer id) {
		if(!pareRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<ParentEntity>(pareRepo.findById(id).get(),HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDTO newParent) {
		return pareServ.addNewParent(newParent);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/{parentId}")
	public ResponseEntity<?> updateTeacher(@PathVariable Integer parentId, @Valid @RequestBody ParentUpdateDTO newParent) {
		return pareServ.updateParent(newParent, parentId);
	}
	/*
	@Secured("ROLE_ADMIN")
	@PutMapping("/credentials")
	public ResponseEntity<?> changeUserAndPass(@Valid @RequestBody CredentialsDTO credentials, @PathVariable Integer parentId) {
		return pareServ.changeUserAndPass(credentials, parentId);
	}
	*/
	@Secured("PARENT_ROLE")
	@PutMapping("/credentials/password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody CredentialsDTO credentials, Principal principal) {
		return pareServ.changePassword(credentials, principal);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/delete-logical/{id}")
	public ResponseEntity<?> deleteParentLogical(@PathVariable Integer id) {
		if(!pareRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		ParentEntity parent = pareRepo.findById(id).get();
		parent.setDeleted(true);
		return new ResponseEntity<ParentEntity>(pareRepo.save(parent), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete-permanent{id}")
	public ResponseEntity<?> deleteParentPhisically(@PathVariable Integer id) {
		if(!pareRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		ParentEntity parent = pareRepo.findById(id).get();
		pareRepo.deleteById(id);
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}
	

}
