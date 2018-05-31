package com.example.finalproject.services;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.finalproject.entities.dto.CredentialsDTO;

public interface TeacherSerice {

	ResponseEntity<?> addUserAndPass (Integer teacherId, CredentialsDTO credentials);
	
}
