package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.utils.RESTError;

@RestController
@RequestMapping(value = "/api/v1/finalproject/parents")
public class ParentController {
	
	@Autowired
	private ParentRepository pareRepo;
	
	@GetMapping("/")
	public List <ParentEntity>  getAllParents() {
		return (List<ParentEntity>) pareRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getParent(@PathVariable Integer id) {
		if(pareRepo.existsById(id)) {
			return new ResponseEntity<ParentEntity>(pareRepo.findById(id).get(),HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDTO newParent) {
		ParentEntity parent = new ParentEntity();
		parent.setFirstName(newParent.getFirstName());
		parent.setLastName(newParent.getLastName());
		parent.setDob(newParent.getDob());
		parent.setEmail(newParent.getEmail());
		return new ResponseEntity<ParentEntity>(pareRepo.save(parent),HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateParent(@PathVariable Integer id, @Valid @RequestBody ParentDTO newParent) {
		ParentEntity parent = pareRepo.findById(id).get();
		if(!pareRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		parent.setFirstName(newParent.getFirstName());
		parent.setLastName(newParent.getLastName());
		parent.setDob(newParent.getDob());
		parent.setEmail(newParent.getEmail());
		return new ResponseEntity<ParentEntity>(pareRepo.save(parent),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer id) {
		ParentEntity parent = pareRepo.findById(id).get();
		if(!pareRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		pareRepo.deleteById(id);
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}
}
