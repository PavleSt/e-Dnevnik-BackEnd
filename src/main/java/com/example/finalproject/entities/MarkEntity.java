package com.example.finalproject.entities;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.finalproject.entities.enums.EMarkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Mark")
public class MarkEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private Integer markNo;
	@Column
	private EMarkType markType;
	@Column
	private ZonedDateTime evaluationDate;
	@Version
	private Integer version;


	public MarkEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMarkNo() {
		return markNo;
	}

	public void setMarkNo(Integer markNo) {
		this.markNo = markNo;
	}

	public EMarkType getMarkType() {
		return markType;
	}

	public void setMarkType(EMarkType markType) {
		this.markType = markType;
	}

	public ZonedDateTime getEvaluationDate() {
		return evaluationDate;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public void setEvaluationDate(ZonedDateTime evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture")
	public LectureEntity lecture;

	//@JsonManagedReference(value = "Teaches - Marks")
	public LectureEntity getLecture() {
		return lecture;
	}

	public void setLecture(LectureEntity lecture) {
		this.lecture = lecture;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
	public StudentEntity student;

	//@JsonManagedReference(value = "Student - Marks")
	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	
	
	
}

