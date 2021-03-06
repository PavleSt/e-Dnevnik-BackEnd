package com.example.finalproject.controllers;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.MarkDTO;
import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.repositories.MarkRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.services.MarkService;
import com.example.finalproject.utils.RESTError;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.LectureRepository;

@RestController
@RequestMapping(value = "/api/v1/final-project/marks")
public class MarkController {

	@Autowired
	private MarkRepository markRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private LectureRepository lectRepo;
	@Autowired
	private GradeRepository gradRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private MarkService markServ;
	@Autowired
	private ParentRepository pareRepo;
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/")
	public ResponseEntity<?> getAllMarks() {
		if (markRepo.findAll() == null ) {
			return new ResponseEntity<RESTError>(new RESTError(1, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MarkEntity>>((List<MarkEntity>) markRepo.findAll(),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@GetMapping("/by-teacher")
	public ResponseEntity<?> getAllTeacherMarks(Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		List<LectureEntity> lectures = lectRepo.findAllByTeacher(teacher);
		List<MarkEntity> marks = new ArrayList<MarkEntity>();
		for (LectureEntity lectureEntity : lectures) {
			marks.addAll(markRepo.findAllByLecture(lectureEntity));	
		}
		if (marks.isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "List is empty"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<MarkEntity>>(marks,HttpStatus.OK);
	}
/*	
	@Secured("ROLE_TEACHER")
	@GetMapping("/by-subject")
	public ResponseEntity<?> getAllSubjectMarks(Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		List<LectureEntity> lectures = lectRepo.findAllBySubject(subject);	
		List<MarkEntity> marks = new ArrayList<MarkEntity>();
		for (LectureEntity lectureEntity : lectures) {
			marks.addAll(markRepo.findAllByLecture(lectureEntity));
			
			
		}
		//if (markRepo.findAllByLecture(lectures) == null) {
	//	return new ResponseEntity<RESTError>(new RESTError(1, "List is empty"), HttpStatus.NOT_FOUND);
		//}
		
		return new ResponseEntity<List<MarkEntity>>(marks,HttpStatus.OK);
	}*/	

	@Secured("ROLE_STUDENT")
	@GetMapping("/by-student")
	public ResponseEntity<?> getAllStudentMarks(Principal principal) {	
		StudentEntity student = studRepo.findByUsername(principal.getName());
		if (markRepo.findAllByStudent(student) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MarkEntity>>((List<MarkEntity>) markRepo.findAllByStudent(student),HttpStatus.OK);
	}
	
	@Secured("ROLE_PARENT")
	@GetMapping("/by-parent")
	public ResponseEntity<?> getAllParentMarks(Principal principal) {	
		ParentEntity parent = pareRepo.findByUsername(principal.getName());
		List<StudentEntity> students = studRepo.findAllByParent(parent);
		List<MarkEntity> marks = new ArrayList<MarkEntity>();
		for (StudentEntity studentEntity: students) {
			marks.addAll(markRepo.findAllByStudent(studentEntity));	
		}
		if (marks.isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "List is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MarkEntity>>(marks,HttpStatus.OK);
	}
	
	
	@Secured("ROLE_TEACHER")
	@PostMapping("/add-mark")
	public ResponseEntity<?> addNewMark(@Valid @RequestBody MarkDTO newMark, Principal principal) {
		return markServ.addNewMark(newMark, principal);
	}
	

	
}
