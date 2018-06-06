package com.example.finalproject.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "Grade")
@JsonRootName(value = "Grade")
@JsonPropertyOrder({ "id", "year", "class", "version" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GradeEntity {

	@Id
	@GeneratedValue
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	@JsonProperty("year")
	@NotNull(message = "Year of school must be provided!")
	@Min(value = 1, message = "Year of school must be greater than or equal to 1")
	@Max(value = 8, message = "Year of school must be less than or equal to 8")
	private Integer year;
	
	@Column
	@JsonProperty("class")
	@NotNull(message = "Student's class must be provided!")
	@Min(value = 1, message = "Student's class must be greater than or equal to 1")
	@Max(value = 3, message = "Student's class must be less than or equal to 3")
	private Integer classroom;
	
	@Version
	private Integer version;
	
	
	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getClassroom() {
		return classroom;
	}

	public void setClassroom(Integer classroom) {
		this.classroom = classroom;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<LectureEntity> lecture;

	@JsonBackReference(value = "Grade - Lecture")
	public List<LectureEntity> getLecture() {
		return lecture;
	}

	public void setLecture(List<LectureEntity> lecture) {
		this.lecture = lecture;
	}
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<StudentEntity> student;

	@JsonBackReference(value = "Grade - Students")
	public List<StudentEntity> getStudent() {
		return student;
	}

	public void setStudent(List<StudentEntity> student) {
		this.student = student;
	}
	
	
	
}
