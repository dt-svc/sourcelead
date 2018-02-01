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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "INTERVIEW_PROPOSE_TIME")
public class ProposeTime implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INTERVIEW_ID", nullable = false)
	private RequirementInterview interviewId;
    
    @Column(name = "PROPOSE_DATE")
   	private Date proposeDate;
    
    @Column(name = "PROPOSE_TIME")
	private String proposeTime;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name = "INTERVIEW_TIME_STATUS")
   	private String interviewTimeStatus;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	@Column(name = "CREATED_BY")
   	private String createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
   	@Column(name = "UPDATED_BY")
   	private String updatedBy;

   	@Transient
   	private String proposeDateStr;
   	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementInterview getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(RequirementInterview interviewId) {
		this.interviewId = interviewId;
	}

	public Date getProposeDate() {
		return proposeDate;
	}

	public void setProposeDate(Date proposeDate) {
		this.proposeDate = proposeDate;
	}

	public String getProposeTime() {
		return proposeTime;
	}

	public void setProposeTime(String proposeTime) {
		this.proposeTime = proposeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInterviewTimeStatus() {
		return interviewTimeStatus;
	}

	public void setInterviewTimeStatus(String interviewTimeStatus) {
		this.interviewTimeStatus = interviewTimeStatus;
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

	public String getProposeDateStr() {
		return proposeDateStr;
	}

	public void setProposeDateStr(String proposeDateStr) {
		this.proposeDateStr = proposeDateStr;
	}
	
	
}
