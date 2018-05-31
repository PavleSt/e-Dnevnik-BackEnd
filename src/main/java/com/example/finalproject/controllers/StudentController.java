package com.example.finalproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.StudentRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/students")
public class StudentController {
	
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private GradeRepository gradRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List <StudentEntity>  getAllStudents() {
		return (List<StudentEntity>) studRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public StudentEntity addNewStudent(@RequestBody StudentEntity newStudent, @RequestParam Integer parentId, 
			@RequestParam Integer gradeId) {
		ParentEntity parent = pareRepo.findById(parentId).get();
		GradeEntity grade = gradRepo.findById(gradeId).get();
		newStudent.setParent(parent);
		newStudent.setGrade(grade);
		return studRepo.save(newStudent);
	}
	
	
	
	
	
	
}

