package com.bis.sourcelead.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "STUDENT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Student {
	
	@Id
    @Column(name = "id", nullable = false)
    private Integer id;
 	
    @Column(name = "studentName")
    private String studentName;
    
    @Column(name = "standard")
    private String standard;
    
    
    /*TO DO
    From Hibernate 3.x we have 3 fetch modes ie., JOIN, SELECT, SUBSELECT
    If we use SELECT AND SUBSELECT the fetching stratagy is Parent and child records are individualy called from DB.
    If we use JOIN internally outer join is made and both parent and child records are loaded is single query.*/
     
    @OneToMany(mappedBy="student",cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Subjects> subjects;
    


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}


	public Set<Subjects> getSubjects() {
		return subjects;
	}


	public void setSubjects(Set<Subjects> subjects) {
		this.subjects = subjects;
	}

    

}
