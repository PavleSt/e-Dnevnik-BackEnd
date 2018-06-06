package com.example.finalproject.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "Lecture")
@JsonRootName(value = "Lecture")
@JsonPropertyOrder({ "id", "teacher", "subject", "grade", "version" })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class LectureEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Version
	private Integer version;
	

	public LectureEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher")
	public TeacherEntity teacher;

	//@JsonManagedReference(value = "Teacher - Teahces")
	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	public SubjectEntity subject;

	//@JsonManagedReference(value = "Subject - Teaches")
	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	public GradeEntity grade;

	//@JsonManagedReference(value = "Grade - Teaches")
	public GradeEntity getGrade() {
		return grade;
	}

	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}
		
	@OneToMany(mappedBy = "lecture", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<MarkEntity> marks;

	@JsonBackReference(value = "Teaches - Marks")
	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}
	
	
	
	
	
}
