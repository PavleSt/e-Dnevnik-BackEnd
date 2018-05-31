package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.repositories.ParentRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/parents")
public class ParentController {
	
	@Autowired
	private ParentRepository pareRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List <ParentEntity>  getAllParents() {
		return (List<ParentEntity>) pareRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ParentEntity addNewParent(@Valid @RequestBody ParentEntity newParent) {
		return pareRepo.save(newParent);
	}

}
