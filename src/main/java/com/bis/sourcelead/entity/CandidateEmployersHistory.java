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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CANDIDATE_EMPLOYERS_HISTORY")
public class CandidateEmployersHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_EMPLOYERS", nullable = false)
	private CandidateEmployers candidateEmployers;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ISCURRENTPOSITION")
	private String isCurrentPosition;
	@Column(name = "DESIGNATION ")
	private String designation;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = " STARTMONTH ")
	private String startMonth;
	@Column(name = " STARTDATE ")
	private Date startDate;
	@Column(name = " ENDMONTH ")
	private String endMonth;
	@Column(name = " STARTYEAR ")
	private String startYear;
	@Column(name = " ENDYEAR ")
	private String endYear;
	@Column(name = " ENDDATE  ")
	private Date endDate;
	// immigration column start
	@Column(name = " TAX_ID")
	private String taxId;
	@Column(name = " EMPLOYMENT_TYPE")
	private String employmentType;
	@Column(name = " JOB_TITLE")
	private String jobTitle;
	// immigration column end
	@Column(name = "STATUS ")
	private Integer status;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getIsCurrentPosition()
	{
		return isCurrentPosition;
	}
	
	public void setIsCurrentPosition(String isCurrentPosition)
	{
		this.isCurrentPosition = isCurrentPosition;
	}
	
	public String getDesignation()
	{
		return designation;
	}
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getStartMonth()
	{
		return startMonth;
	}
	
	public void setStartMonth(String startMonth)
	{
		this.startMonth = startMonth;
	}
	
	public String getEndMonth()
	{
		return endMonth;
	}
	
	public void setEndMonth(String endMonth)
	{
		this.endMonth = endMonth;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	
	public String getStartYear()
	{
		return startYear;
	}
	
	public void setStartYear(String startYear)
	{
		this.startYear = startYear;
	}
	
	public String getEndYear()
	{
		return endYear;
	}
	
	public void setEndYear(String endYear)
	{
		this.endYear = endYear;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	
	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
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
	
	public CandidateEmployers getCandidateEmployers()
	{
		return candidateEmployers;
	}
	
	public void setCandidateEmployers(CandidateEmployers candidateEmployers)
	{
		this.candidateEmployers = candidateEmployers;
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
