package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.repositories.SubjectRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<SubjectEntity> gettAllSubjects () {
		return (List<SubjectEntity>) subjRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public SubjectEntity addNewSubject (@Valid @RequestBody SubjectEntity newSubject) {
		return subjRepo.save(newSubject);
	}
	
}
