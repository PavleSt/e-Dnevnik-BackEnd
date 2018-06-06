package com.example.finalproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.finalproject.entities.GradeEntity;
import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;

public interface LectureRepository extends CrudRepository<LectureEntity, Integer> {

	LectureEntity findByTeacherAndSubjectAndGrade (TeacherEntity teacherId, SubjectEntity subjectId, 
			GradeEntity gradeId);
}
