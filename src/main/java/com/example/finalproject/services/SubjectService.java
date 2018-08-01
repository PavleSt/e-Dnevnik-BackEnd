package com.example.finalproject.services;

import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.dto.SubjectDTO;

public interface SubjectService {
	
	ResponseEntity<?> addNewSubject (SubjectDTO newSubject);
	ResponseEntity<?> updateSubject (Integer nocl, Integer subjectId);
	ResponseEntity<?> getAllSubjectTeachers(Integer subjectId);

}
