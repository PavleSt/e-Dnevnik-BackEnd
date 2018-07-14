package com.example.finalproject.repositories;

import java.security.Principal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {

	List<MarkEntity> findAllByStudent (StudentEntity student);
	
	List<MarkEntity> findAllByLecture (List<LectureEntity> lectures);
	List<MarkEntity> findAllByLecture (GradeEntity grade);
	List<MarkEntity> findAllByLecture (SubjectEntity subject);
}
