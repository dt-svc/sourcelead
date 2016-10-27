package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEWER", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementInterviewer")
	public class RequirementInterviewer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="INTERVIEW_ID", nullable=false )        
    private RequirementInterview requirementInterview;
	
	@ManyToOne
    @JoinColumn(name="INTERVIEWER_USER_ID", nullable=false )        
    private User interviewerUser;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String interviewerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementInterview getRequirementInterview() {
		return requirementInterview;
	}

	public void setRequirementInterview(RequirementInterview requirementInterview) {
		this.requirementInterview = requirementInterview;
	}

	public User getInterviewerUser() {
		return interviewerUser;
	}

	public void setInterviewerUser(User interviewerUser) {
		this.interviewerUser = interviewerUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}
			
}
