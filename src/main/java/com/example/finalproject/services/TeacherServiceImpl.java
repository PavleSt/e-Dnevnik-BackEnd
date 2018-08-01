package com.example.finalproject.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.CredentialsDTO;
import com.example.finalproject.entities.dto.TeacherDTO;
import com.example.finalproject.entities.dto.TeacherUpdateDTO;
import com.example.finalproject.repositories.LectureRepository;
import com.example.finalproject.repositories.ParentRepository;
import com.example.finalproject.repositories.RoleRepository;
import com.example.finalproject.repositories.StudentRepository;
import com.example.finalproject.repositories.TeacherRepository;
import com.example.finalproject.utils.Encryption;
import com.example.finalproject.utils.RESTError;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacRepo;
	@Autowired
	private ParentRepository pareRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private LectureRepository lectRepo;


	@Override
	public ResponseEntity<?> addNewTeacher(TeacherDTO newTeacher) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setDob(newTeacher.getDob());
		teacher.setRole(roleRepo.findByName("ROLE_TEACHER"));
		teacher.setDeleted(false);
		if (!(teacRepo.findByEmail(newTeacher.getEmail()) == null) ||
				!(pareRepo.findByEmail(newTeacher.getEmail()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Email address already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			teacher.setEmail(newTeacher.getEmail());
		}
		if (!(teacRepo.findByUsername(newTeacher.getUsername()) == null) || 
				!(pareRepo.findByUsername(newTeacher.getUsername()) == null) ||
				!(studRepo.findByUsername(newTeacher.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
		}
		else {
			teacher.setUsername(newTeacher.getUsername());
		}
		if (!(newTeacher.getPassword().equals(newTeacher.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
		}
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> updateTeacher(TeacherUpdateDTO newTeacher, Integer teacherId) {
		if(!teacRepo.existsById(teacherId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(teacherId).get();
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setDob(newTeacher.getDob());
		if (!(teacher.getEmail().equals(newTeacher.getEmail()))) {
			if (!(teacRepo.findByEmail(newTeacher.getEmail()) == null) ||
				!(pareRepo.findByEmail(newTeacher.getEmail()) == null)) {
				return new ResponseEntity<RESTError>(new RESTError(2, "Email address already exists"), HttpStatus.UNPROCESSABLE_ENTITY);	
			}
		}
		teacher.setEmail(newTeacher.getEmail());
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher),HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changePassword(CredentialsDTO credentials, Principal principal) {
		TeacherEntity teacher = teacRepo.findByUsername(principal.getName());
		
		if (teacher.getUsername().equals(credentials.getUsername())) {
			teacher.setUsername(credentials.getUsername());
		} else {
			return new ResponseEntity<RESTError>(new RESTError(7, "Incorect username"), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		// provera da li se slazu sadasnji username i pass
		if (Encryption.gettPassDecoded(credentials.getPassword(), teacher.getPassword()) == false) {
			return new ResponseEntity<RESTError>(new RESTError(7, "Wrong password entered"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera slaganja passworda
		if (!(credentials.getPasswordNew().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		teacher.setPassword(Encryption.getPassEncoded(credentials.getPasswordNew()));
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changeUserAndPass(CredentialsDTO credentials, Integer teacherId) {
		if (teacRepo.findById(teacherId).get() == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(teacherId).get();
		// provera za novi username
		if (!(teacRepo.findByUsername(credentials.getUsername()) == null)
				|| !(pareRepo.findByUsername(credentials.getUsername()) == null)
				|| !(studRepo.findByUsername(credentials.getUsername()) == null)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		// provera lsaganja passworda
		if (!(credentials.getPassword().equals(credentials.getConfirmPassword()))) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Password does not match"),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		teacher.setUsername(credentials.getUsername());
		teacher.setPassword(Encryption.getPassEncoded(credentials.getPassword()));
		return new ResponseEntity<TeacherEntity>(teacRepo.save(teacher), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllTeacherSubjects(Integer teacherId) {
		if(!teacRepo.existsById(teacherId)) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.NOT_FOUND);
		}
		TeacherEntity teacher = teacRepo.findById(teacherId).get();	
		if (lectRepo.findAllByTeacher(teacher) == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher is not assigned to any lectures"), HttpStatus.NOT_FOUND);
		}
		List<LectureEntity> lectures = lectRepo.findAllByTeacher(teacher);
		List<SubjectEntity> subjects = new ArrayList<SubjectEntity>();
		for (LectureEntity lect : lectures) {
			subjects.add(lect.getSubject());
		}
		return new ResponseEntity<List<SubjectEntity>>(subjects, HttpStatus.OK);	
	}

}
