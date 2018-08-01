package com.example.finalproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.SubjectDTO;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

	SubjectEntity findBySubjectName (String subjectName);
	List<SubjectEntity> findAllById(SubjectEntity subject);
	
	

}
