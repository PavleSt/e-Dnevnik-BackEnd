package com.example.finalproject.services;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.example.finalproject.entities.dto.MarkDTO;

public interface MarkService {
	
	ResponseEntity<?> addNewMark(MarkDTO newMark, Principal principal);

}
