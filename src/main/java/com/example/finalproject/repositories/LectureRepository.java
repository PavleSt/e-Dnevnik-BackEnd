package com.example.finalproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;

public interface LectureRepository extends CrudRepository<LectureEntity, Integer> {

	LectureEntity findByTeacherAndSubjectAndGrade (TeacherEntity teacher, SubjectEntity subject, 
			GradeEntity grade);
	List<LectureEntity> findAllByTeacher (TeacherEntity teacher);
	List<LectureEntity> findAllBySubject (SubjectEntity subject);

}
