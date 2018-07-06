package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;


import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.ParentDTO;


public interface ParentService {

	ResponseEntity<?> addNewParent(ParentDTO newParent);
	ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal);
}
