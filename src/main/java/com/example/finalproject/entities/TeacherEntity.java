package com.example.finalproject.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;


@Entity
@Table(name = "Teacher")
@JsonRootName(value = "Teacher")
@JsonPropertyOrder({ "id", "first_name", "last_name", "date_of_birth", "email", "username", "password", "version" })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class TeacherEntity extends UserEntity {


	@Column
	@JsonProperty("email")
	@NotNull(message = "Email address must be provided!")
	@Pattern(regexp= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	
	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<LectureEntity> lecture;

	@JsonBackReference(value = "Teacher - Lecture")
	public List<LectureEntity> getLecture() {
		return lecture;
	}

	public void setLecture(List<LectureEntity> lecture) {
		this.lecture = lecture;
	}
	
	
	
}
