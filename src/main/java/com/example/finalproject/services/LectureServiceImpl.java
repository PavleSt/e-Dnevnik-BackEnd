package com.example.finalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.LectureDTO;
import com.example.finalproject.repositories.GradeRepository;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.RESTError;

@Service
public class LectureServiceImpl implements LectureService {
	
	@Autowired
	private LectureRepository lectRepo;
	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private GradeRepository gradRepo;

	@Override
	public ResponseEntity<?> addNewLecture(LectureDTO newLecture) {
		if (!(teacRepo.existsById(newLecture.getTeacherId()))) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(newLecture.getTeacherId()).get();
		
		if (!(subjRepo.existsById(newLecture.getSubjectId()))) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjRepo.findById(newLecture.getSubjectId()).get();
		
		if (!(gradRepo.existsById(newLecture.getGradeId()))) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
		}
		GradeEntity grade = gradRepo.findById(newLecture.getGradeId()).get();
		
		if (!(subject.getSubjectName().contains(grade.getYear().toString()))) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Subject is not fit for that grade!"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (lectRepo.findByTeacherAndSubjectAndGrade(teacRepo.findById(newLecture.getTeacherId()).get(), 
				subjRepo.findById(newLecture.getSubjectId()).get(), 
				gradRepo.findById(newLecture.getGradeId()).get()) != null) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Lecture already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		LectureEntity lecture = new LectureEntity();
		lecture.setTeacher(teacher);
		lecture.setSubject(subject);
		lecture.setGrade(grade);
		return new ResponseEntity<LectureEntity>(lectRepo.save(lecture),HttpStatus.CREATED);
	}

	
	
	
	
	
	
}
