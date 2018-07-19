package com.example.finalproject.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectDTO {

	@Column
	@JsonProperty("subject_name")
	@NotNull(message = "Name of the subject must be provided!")
	@Size(min = 2, max = 20, message = "Name of the subject must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z1-8_]*$")
	private String subjectName;
	
	@Column
	@JsonProperty("number_of_classes_per_week")
	@NotNull(message = "Number of classes must be provided!")
	@Min(value = 1, message = "Number of classes must be greater than or equal to 1")
	@Max(value = 10, message = "Number of classes must be less than or equal to 10")
	private Integer weeklyLectures;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getWeeklyLectures() {
		return weeklyLectures;
	}

	public void setWeeklyLectures(Integer weeklyLectures) {
		this.weeklyLectures = weeklyLectures;
	}
	
	
}
