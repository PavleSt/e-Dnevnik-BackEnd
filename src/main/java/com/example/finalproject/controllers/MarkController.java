package com.example.finalproject.controllers;

import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.repositories.MarkRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
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
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List <MarkEntity>  getAllMarks() {
		return (List<MarkEntity>) markRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addNewMark(@Valid @RequestBody MarkEntity newMark, @RequestParam Integer studentId,
			@RequestParam Integer teacherId, @RequestParam Integer subjectId) {
		MarkEntity mark = new MarkEntity();
		StudentEntity student = studRepo.findById(studentId).get();
		GradeEntity grade = student.getGrade();
		TeacherEntity teacher = teacRepo.findById(teacherId).get();
		SubjectEntity subject = subjRepo.findById(subjectId).get();
		if (lectRepo.findByTeacherAndSubjectAndGrade(teacher, subject, grade) != null) {
			LectureEntity lecture = lectRepo.findByTeacherAndSubjectAndGrade(teacher, subject, grade);
			mark.setMarkNo(newMark.getMarkNo());
			mark.setMarkType(newMark.getMarkType());
			mark.setEvaluationDate(ZonedDateTime.now());
			mark.setStudent(student);
			mark.setLecture(lecture);
			return new ResponseEntity<MarkEntity>(markRepo.save(mark),HttpStatus.CREATED);
		}
		return new ResponseEntity<RESTError>(new RESTError(6, "Teacher can only mark a student which is taking his course"
				+ "!"),
				HttpStatus.BAD_REQUEST);
	}
}
