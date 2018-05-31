package com.example.finalproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.UserEntity;
import com.example.finalproject.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/finalproject/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<UserEntity> gettAllUsers () {
		return (List<UserEntity>) userRepo.findAll();
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public UserEntity addUser (@RequestBody UserEntity newUser) {
		return userRepo.save(newUser);
	}

}
