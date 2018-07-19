package com.example.finalproject.entities.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentUpdateDTO {

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
	
	@JsonProperty("email")
	@NotNull(message = "Email address must be provided!")
	@Pattern(regexp= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	public ParentUpdateDTO() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
