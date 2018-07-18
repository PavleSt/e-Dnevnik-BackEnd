package com.example.finalproject.entities.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDTO {

	@JsonProperty("first_name")
	@NotNull(message = "First name must be provided!")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z]*$")
	private String firstName;
	
	@JsonProperty("last_name")
	@NotNull(message = "Last name must be provided!")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z']*$")
	private String lastName;
	
	@JsonProperty("date_of_birth")
	@NotNull(message = "Date of birth must be provided!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	//@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d\\d$")
	//@Past
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	// dodaj custom anotaciju za proveru
	@JsonProperty("parent_id")
	private Integer parentId;
	// dodaj custom anotaciju za proveru
	@JsonProperty("grade_id")
	private Integer gradeId;
	
	
	@JsonProperty("username")
	@NotNull(message = "Username must be provided!")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String username;
	
	@JsonProperty("password")
	@NotNull(message = "Password must be provided!")
	@Size(min = 5, max = 15, message = "Password must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String password;
	
	@JsonProperty("confirm_password")
	private String confirmPassword;
	
	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
}
