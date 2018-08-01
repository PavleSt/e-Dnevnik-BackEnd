package com.example.finalproject.services;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.MarkDTO;
import com.example.finalproject.entities.enums.EMarkType;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.MarkRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.security.Views.Teacher;
import com.example.finalproject.utils.RESTError;

@Service
public class MarkServiceImpl implements MarkService {
	
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
	private EmailService emaiServ;
/*
	@Override
	public ResponseEntity<?> addNewMark(MarkDTO newMark, Principal principal) {
		
		MarkEntity mark = new MarkEntity();
		
		if (gradRepo.findByYearAndClassroom(newMark.getGradeYear(), newMark.getGradeClassrom()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
		}
		GradeEntity grade = gradRepo.findByYearAndClassroom(newMark.getGradeYear(), newMark.getGradeClassrom());
		
		if (studRepo.findByFirstNameAndLastNameAndGrade(newMark.getStudentFirstName(), 
				newMark.getStudentLastName(), grade) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);	
		}
		StudentEntity student = studRepo.findByFirstNameAndLastNameAndGrade(newMark.getStudentFirstName(), 
				newMark.getStudentLastName(), grade);
		
		if (teacRepo.findByUsername(newMark.getTeacherUsername()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);	
		}
		TeacherEntity teacher = teacRepo.findByUsername(newMark.getTeacherUsername());
		
		if (!(principal.getName().equals(teacher.getUsername()))) {
			return new ResponseEntity<RESTError>(new RESTError(20, "Denied! Wrong teacher logged in! "), 
					HttpStatus.UNAUTHORIZED);	
		}
		
		if (subjRepo.findBySubjectName(newMark.getSubjectName()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not found"), HttpStatus.NOT_FOUND);	
		}
		SubjectEntity subject = subjRepo.findBySubjectName(newMark.getSubjectName());
		
		if (lectRepo.findByTeacherAndSubjectAndGrade(teacher, subject, grade) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Lecture not found"), HttpStatus.NOT_FOUND);
		}
		LectureEntity lecture = lectRepo.findByTeacherAndSubjectAndGrade(teacher, subject, grade);
		
		if (!(lecture.getGrade().equals(student.getGrade()))) {
			return new ResponseEntity<RESTError>(new RESTError(6, "Teacher can only mark a student which is taking his course"
					+ "!"),
					HttpStatus.BAD_REQUEST);
		}
		mark.setLecture(lecture);
		mark.setMarkNo(newMark.getMarkNo());
		mark.setMarkType(newMark.getMarkType());
		mark.setEvaluationDate(ZonedDateTime.now());
		mark.setStudent(student);
		
		try {
			emaiServ.sendTemplateMessagesVoucher(student, teacher, subject, mark);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MarkEntity>(markRepo.save(mark),HttpStatus.CREATED);
	}	
*/


	@Override
	public ResponseEntity<?> addNewMark(MarkDTO newMark, Principal principal) {
		
		MarkEntity mark = new MarkEntity();
		
		if (teacRepo.findByUsername(principal.getName()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);	
		}
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		
		if (lectRepo.findById(newMark.getLectureId()) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Lecture not found"), HttpStatus.NOT_FOUND);
		}
		LectureEntity lecture = lectRepo.findById(newMark.getLectureId()).get();
		
		if(!lecture.getTeacher().getId().equals(teacher.getId())) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Lecture doesn't belog to teacher"), HttpStatus.NOT_FOUND);
		}
		
		GradeEntity grade = gradRepo.findById(lecture.getGrade().getId()).get();
		SubjectEntity subject = subjRepo.findById(lecture.getSubject().getId()).get();
		
		StudentEntity student = studRepo.findById(newMark.getStudentId()).get();
		List<MarkEntity> marks = markRepo.findAllByStudent(student);
		for (MarkEntity markEntity : marks) {
			if (markEntity.getMarkType().equals(EMarkType.FINAL_MARK)) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Mark is alredy final"), HttpStatus.BAD_REQUEST);			
			}
			
		}
		
		if (!(lecture.getGrade().equals(student.getGrade()))) {
			return new ResponseEntity<RESTError>(new RESTError(6, "Teacher can only mark a student which is taking his course"
					+ "!"),
					HttpStatus.BAD_REQUEST);
		}
		mark.setLecture(lecture);
		mark.setMarkNo(newMark.getMarkNo());
		mark.setMarkType(newMark.getMarkType());
		mark.setEvaluationDate(ZonedDateTime.now());
		mark.setStudent(student);
		
		try {
			emaiServ.sendTemplateMessagesVoucher(student, teacher, subject, mark);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MarkEntity>(markRepo.save(mark),HttpStatus.CREATED);
	}
}
	


