package com.example.finalproject.services;

import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.dto.LectureDTO;

public interface LectureService {

	ResponseEntity<?> addNewLecture(LectureDTO newLecture);
	
}
