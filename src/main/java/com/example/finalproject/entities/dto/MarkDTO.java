package com.example.finalproject.entities.dto;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.finalproject.entities.enums.EMarkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkDTO {

	@JsonProperty("mark")
	@NotNull(message = "Mark must be provided!")
	private Integer markNo;

	@JsonProperty("examination")
	@NotNull(message = "Examination type must be provided!")
	private EMarkType markType;

	@JsonProperty("examination_date")
	private ZonedDateTime evaluationDate;
	
	//@JsonProperty("student_id")
	//private Integer studentId;
	
	@JsonProperty("student_first_name")
	private String studentFirstName;
	@JsonProperty("student_last_name")
	private String studentLastName;
	
	@JsonProperty("year_of_school")
	private Integer gradeYear;
	@JsonProperty("classroom")
	private Integer gradeClassrom;
	
	@JsonProperty("teacher_username")
	private String teacherUsername;
	
	@JsonProperty("subject_name")
	private String subjectName;

	public MarkDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMarkNo() {
		return markNo;
	}

	public void setMarkNo(Integer markNo) {
		this.markNo = markNo;
	}

	public EMarkType getMarkType() {
		return markType;
	}

	public void setMarkType(EMarkType markType) {
		this.markType = markType;
	}

	public ZonedDateTime getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(ZonedDateTime evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public Integer getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {
		this.gradeYear = gradeYear;
	}

	public Integer getGradeClassrom() {
		return gradeClassrom;
	}

	public void setGradeClassrom(Integer gradeClassrom) {
		this.gradeClassrom = gradeClassrom;
	}

	public String getTeacherUsername() {
		return teacherUsername;
	}

	public void setTeacherUsername(String teacherUsername) {
		this.teacherUsername = teacherUsername;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	
}
