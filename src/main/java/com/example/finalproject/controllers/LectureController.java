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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.LectureDTO;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.services.LectureService;
import com.example.finalproject.utils.RESTError;
import com.example.finalproject.repositories.LectureRepository;


@RestController
@RequestMapping(value = "/api/v1/final-project/lectures")
public class LectureController {
	
	@Autowired
	private LectureRepository lectRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private GradeRepository gradRepo;
	@Autowired
	private LectureService lectServ;

	
	@GetMapping("/")
	public List<LectureEntity> getAllLectures() {
		return (List<LectureEntity>) lectRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getLecture(@PathVariable Integer id) {
		if(lectRepo.existsById(id)) {
			return new ResponseEntity<LectureEntity>(lectRepo.findById(id).get(),HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);	
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public ResponseEntity<?> addNewLecture(@Valid @RequestBody LectureDTO newLecture) {
		return lectServ.addNewLecture(newLecture);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateLecture(@PathVariable Integer id, @Valid @RequestBody LectureDTO newLecture) {
		LectureEntity lecture = lectRepo.findById(id).get();
		if(!lectRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		lecture.setTeacher(teacRepo.findById(newLecture.getTeacherId()).get());
		lecture.setSubject(subjRepo.findById(newLecture.getSubjectId()).get());
		lecture.setGrade(gradRepo.findById(newLecture.getGradeId()).get());
		return new ResponseEntity<LectureEntity>(lectRepo.save(lecture),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLecture(@PathVariable Integer id) {
		LectureEntity lecture = lectRepo.findById(id).get();
		if(!teacRepo.existsById(id)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		lectRepo.deleteById(id);
		return new ResponseEntity<LectureEntity>(lecture, HttpStatus.OK);
	}
/*	
	@GetMapping("/test")
	public LectureEntity testTest(@RequestParam Integer ) {
		LectureEntity lecture = lectRepo.findByTeacherAndSubjectAndGrade(newLecture.getTeacherId(), 
				newLecture.getSubjectId(), newLecture.getGradeId());
		return lecture;
	} */
	
	@Secured("ROLE_TEACHER")
	@GetMapping("/by-teacher")
	public List<LectureEntity> getAllLecturesByTeacher(Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		return  lectRepo.findAllByTeacher(teacher);
	}
}
