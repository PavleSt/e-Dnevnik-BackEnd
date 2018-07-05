package com.example.finalproject.services;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;

public interface TeacherSerice {

	ResponseEntity<?> addNewTeacher(TeacherDTO newTeacher);
	ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials);
	
}