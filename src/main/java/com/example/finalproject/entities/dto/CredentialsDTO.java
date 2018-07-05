package com.example.finalproject.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialsDTO {

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
	
	private String confirmPassword;
	
	private String usernameNew;
	private String passwordNew;
	public CredentialsDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getUsernameNew() {
		return usernameNew;
	}
	public void setUsernameNew(String usernameNew) {
		this.usernameNew = usernameNew;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}


	

	
	
}
