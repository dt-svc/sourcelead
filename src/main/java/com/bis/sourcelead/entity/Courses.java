package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "COURSES")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Courses {
	
	@Id
    @Column(name = "id", nullable = false)
	private Integer Id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="subjectId", nullable=false )	
	private Subjects subjects;
	
	@Column(name = "courseName")
	private String courseName;
	
	@Column(name = "duration")
	private String duration;
	
	
	
	

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Subjects getSubjects() {
		return subjects;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
