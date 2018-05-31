package com.example.finalproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.repositories.LectureRepository;


@RestController
@RequestMapping(value = "/api/v1/finalproject/teaches")
public class LectureController {
	
	@Autowired
	private LectureRepository lectRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private GradeRepository gradRepo;

	@GetMapping("/")
	public List<LectureEntity> getAllTeaches () {
		return (List<LectureEntity>) lectRepo.findAll();
	}
	
	@PostMapping("/")
	public LectureEntity addNewTeaches (@RequestParam Integer teacherId, @RequestParam Integer subjectId, 
			@RequestParam Integer gradeId) {
		TeacherEntity teacher = teacRepo.findById(teacherId).get();
		SubjectEntity subject = subjRepo.findById(subjectId).get();
		GradeEntity grade = gradRepo.findById(gradeId).get();
		LectureEntity teaches = new LectureEntity();
		teaches.setTeacher(teacher);
		teaches.setSubject(subject);
		teaches.setGrade(grade);
		return lectRepo.save(teaches);
	}
	
	
}
