package com.bis.sourcelead.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "SUBJECTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL )
public class Subjects {
	
	@Id
    @Column(name = "id", nullable = false)
    private Integer id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="studentId", nullable=false )	
	private Student student;
	
	@Column(name = "subjectName")
	private String subjectName;
	
	@Column(name = "marks")
	private Integer marks;
	
    @OneToMany(mappedBy="subjects",cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Courses> courses;
	
	

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public Set<Courses> getCourses() {
		return courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}

}
