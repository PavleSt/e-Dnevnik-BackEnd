package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;

public interface TeacherService {

	ResponseEntity<?> addNewTeacher(TeacherDTO newTeacher);
	ResponseEntity<?> updateTeacher(TeacherUpdateDTO newTeacher, Integer teacherId);
	ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Integer teacherId);
	ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal);
	ResponseEntity<?> getAllTeacherSubjects(Integer teacherId);
	
	
}