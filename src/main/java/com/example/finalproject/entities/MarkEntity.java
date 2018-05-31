package com.example.finalproject.entities;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Mark")
public class MarkEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column
	private Integer mark;
	@Column
	private EMarkType markType;
	@Column
	private Date evaluationDate;
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

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public EMarkType getMarkType() {
		return markType;
	}

	public void setMarkType(EMarkType markType) {
		this.markType = markType;
	}

	public Date getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teaches")
	public LectureEntity teaches;

	//@JsonManagedReference(value = "Teaches - Marks")
	public LectureEntity getTeaches() {
		return teaches;
	}

	public void setTeaches(LectureEntity teaches) {
		this.teaches = teaches;
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

