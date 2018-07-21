package com.example.finalproject.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GeneratorType;

import com.example.finalproject.utils.CustomDateDeserializer;
import com.example.finalproject.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
//@JsonIgnoreProperties
public abstract class UserEntity {
	
	@Id
	@GeneratedValue
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	@JsonProperty("first_name")
	@NotNull(message = "First name must be provided!")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z]*$")
	private String firstName;
	
	@Column
	@JsonProperty("last_name")
	@NotNull(message = "Last name must be provided!")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z']*$")
	private String lastName;
	
	@Column
	@JsonProperty("date_of_birth")
	@NotNull(message = "Date of birth must be provided!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	//@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d\\d$")
	//@Past
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column
	@JsonProperty("username")
	//@NotNull(message = "Username must be provided!")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String username;
	
	@Column
	@JsonProperty("password")
	@NotNull(message = "Password must be provided!")
	@Size(min = 5, max = 100, message = "Password must be between {min} and {max} characters long!")
	//@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String password;
	
	@Column
	@NotNull(message = "Information 'deleted' about entity must be provided!")
	private Boolean deleted;
	
	@Version
	private Integer version;
	
	
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	//@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	@NotNull(message = "User must have asigned role!")
	public RoleEntity role;

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
