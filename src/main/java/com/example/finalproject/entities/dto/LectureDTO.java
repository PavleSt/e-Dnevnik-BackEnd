package com.example.finalproject.entities.dto;

public class LectureDTO {

	private Integer teacherId;
	
	private Integer subjectId;
	
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
