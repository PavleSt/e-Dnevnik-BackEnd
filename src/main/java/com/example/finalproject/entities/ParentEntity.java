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
@Table(name = "Parent")
@JsonRootName(value = "Parent")
@JsonPropertyOrder({ "id", "first_name", "last_name", "date_of_birth", "email", "username", "password", "version" })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class ParentEntity extends UserEntity{

	@Column
	@JsonProperty("email")
	@NotNull(message = "Email address must be provided!")
	@Pattern(regexp= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
			message = "Incompatibile email pattern")
	private String email;

	
	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<StudentEntity> student;

	@JsonBackReference(value = "Parent - Students")
	public List<StudentEntity> getStudent() {
		return student;
	}

	public void setStudent(List<StudentEntity> student) {
		this.student = student;
	}
	
}
