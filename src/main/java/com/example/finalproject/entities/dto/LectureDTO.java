package com.example.finalproject.entities.dto;

import javax.validation.constraints.NotNull;

public class LectureDTO {

	@NotNull(message = "Teacher must be provided!")
	private Integer teacherId;
	
	@NotNull(message = "Subject must be provided!")
	private Integer subjectId;
	
	@NotNull(message = "Grade must be provided!")
	private Integer gradeId;

	public LectureDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	
	
}
