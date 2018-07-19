package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.StudentDTO;
import com.example.finalproject.entities.dto.StudentUpdateDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;

public interface StudentService {

	ResponseEntity<?> addNewStudent(StudentDTO newStudent);
	ResponseEntity<?> updateStudent(StudentUpdateDTO newStudent, Integer studentId);
	ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Principal principal);
	ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal);
}
