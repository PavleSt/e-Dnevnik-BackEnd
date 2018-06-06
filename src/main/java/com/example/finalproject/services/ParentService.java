package com.example.finalproject.services;

import org.springframework.http.ResponseEntity;


import com.example.finalproject.entities.dto.CredentialsDTO;

public interface ParentService {

	ResponseEntity<?> addUserAndPass(Integer parentId, CredentialsDTO credentials);
}
