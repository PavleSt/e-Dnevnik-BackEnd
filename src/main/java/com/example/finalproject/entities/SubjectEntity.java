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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;


@Entity
@Table(name = "Subject")
@JsonRootName(value = "Subject")
@JsonPropertyOrder({ "id", "subject_name", "number_of_lectures_per_week", "version" })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class SubjectEntity {

	@Id
	@GeneratedValue
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	@JsonProperty("subject_name")
	@NotNull(message = "Name of the subject must be provided!")
	@Size(min = 2, max = 20, message = "Name of the subject must be between {min} and {max} characters long!")
	@Pattern(regexp = "^[a-zA-Z1-8_]*$")
	private String subjectName;
	
	@Column
	@JsonProperty("number_of_lectures_per_week")
	@NotNull(message = "Number of lectures must be provided!")
	@Min(value = 2, message = "Number of lectures must be greater than or equal to 2")
	@Max(value = 10, message = "Number of lectures must be less than or equal to 10")
	private Integer weeklyLectures;
	
	@Version
	private Integer version;

	
	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<LectureEntity> lecture;

	@JsonBackReference(value = "Subject - Lecture")
	public List<LectureEntity> getLecture() {
		return lecture;
	}

	public void setLecture(List<LectureEntity> lecture) {
		this.lecture = lecture;
	}
	
	
}
