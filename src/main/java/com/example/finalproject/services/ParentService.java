package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;


import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;
import com.example.finalproject.entities.dto.ParentUpdateDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;


public interface ParentService {

	ResponseEntity<?> addNewParent(ParentDTO newParent);
	ResponseEntity<?> updateParent(ParentUpdateDTO newParent, Integer parentId);
	ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Integer parentId);
	ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal);
}
