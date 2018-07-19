package com.example.finalproject.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.SubjectDTO;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.SubjectRepository;
import com.example.finalproject.utils.RESTError;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjRepo;
	@Autowired
	private LectureRepository lectRepo;
	
	@Override
	public ResponseEntity<?> addNewSubject(SubjectDTO newSubject) {
		SubjectEntity subject = new SubjectEntity();
		if (subjRepo.findBySubjectName(newSubject.getSubjectName()) != null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject already exists!"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		subject.setSubjectName(newSubject.getSubjectName());
		subject.setWeeklyLectures(newSubject.getWeeklyLectures());	
		return new ResponseEntity<SubjectEntity>(subjRepo.save(subject),HttpStatus.CREATED);
	}

	public ResponseEntity<?> updateSubject(SubjectDTO newSubject, Integer subjectId) {
		if(!subjRepo.existsById(subjectId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjRepo.findById(subjectId).get();
		subject.setSubjectName(subject.getSubjectName());
		subject.setWeeklyLectures(newSubject.getWeeklyLectures());	
		return new ResponseEntity<SubjectEntity>(subjRepo.save(subject),HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getAllSubjectTeachers(Integer subjectId) {
		if(!subjRepo.existsById(subjectId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		SubjectEntity subject = subjRepo.findById(subjectId).get();	
		if (lectRepo.findAllBySubject(subject) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject is not being teached at any lectures"), HttpStatus.NOT_FOUND);
		}
		List<LectureEntity> lectures = lectRepo.findAllBySubject(subject);
		List<TeacherEntity> teachers = new ArrayList<TeacherEntity>();
		for (LectureEntity lect : lectures) {
			teachers.add(lect.getTeacher());
		}
		return new ResponseEntity<List<TeacherEntity>>(teachers, HttpStatus.OK);	
	}
	
	
	
}
