package com.example.finalproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.repositories.MarkRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.LectureRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/marks")
public class MarkController {

	@Autowired
	private MarkRepository markRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private LectureRepository lectRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List <MarkEntity>  getAllMarks() {
		return (List<MarkEntity>) markRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public MarkEntity addNewMark(@Valid @RequestBody MarkEntity newMark, @RequestParam Integer studentId,
			@RequestParam Integer teachesId) {
		StudentEntity student = studRepo.findById(studentId).get();
		LectureEntity teaches = lectRepo.findById(teachesId).get();
		newMark.setStudent(student);
		newMark.setTeaches(teaches);
		return markRepo.save(newMark);
	}
}
