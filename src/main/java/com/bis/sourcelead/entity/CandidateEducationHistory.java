package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Shivayogi
 *		
 */
@Entity
@Table(name = "CANDIDATE_EDUCATION_HISTORY")
public class CandidateEducationHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_EDUCATION", nullable = false)
	private CandidateEducation candidateEducation;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SPECIALIZATION")
	private String specialization;
	@Column(name = "STARTDATE")
	private Date startDate;
	@Column(name = "ENDDATE")
	private Date endDate;
	@Column(name = "GRADE")
	private String grade;
	@Column(name = "GRADE_TYPE")
	private String gradeType;
	@Column(name = "INSTITUTE")
	private String institute;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = " STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public CandidateEducation getCandidateEducation()
	{
		return candidateEducation;
	}
	
	public void setCandidateEducation(CandidateEducation candidateEducation)
	{
		this.candidateEducation = candidateEducation;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSpecialization()
	{
		return specialization;
	}
	
	public void setSpecialization(String specialization)
	{
		this.specialization = specialization;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	
	public String getGrade()
	{
		return grade;
	}
	
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	
	public String getGradeType()
	{
		return gradeType;
	}
	
	public void setGradeType(String gradeType)
	{
		this.gradeType = gradeType;
	}
	
	public String getInstitute()
	{
		return institute;
	}
	
	public void setInstitute(String institute)
	{
		this.institute = institute;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
}
