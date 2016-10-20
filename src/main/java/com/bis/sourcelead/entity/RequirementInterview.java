package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEW", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirmentInterview")
	public class RequirementInterview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@Column(name = "INTERVIEW_TYPE")
	private String interviewType;
	
	@Column(name = "START_TIME")
	private String startTime;
	
	@Column(name = "END_TIME")
	private String endTime;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name="NOTIFICATION_TO_APPLICANT")
	private String notificationToApplicant;
	
	@Column(name="NOTIFICATION_TO_SPOC")
	private String notificationToSpoc;

	@Column(name="REMAINED_BEFORE")
	private String remainedBefore;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNotificationToApplicant() {
		return notificationToApplicant;
	}

	public void setNotificationToApplicant(String notificationToApplicant) {
		this.notificationToApplicant = notificationToApplicant;
	}

	public String getNotificationToSpoc() {
		return notificationToSpoc;
	}

	public void setNotificationToSpoc(String notificationToSpoc) {
		this.notificationToSpoc = notificationToSpoc;
	}

	public String getRemainedBefore() {
		return remainedBefore;
	}

	public void setRemainedBefore(String remainedBefore) {
		this.remainedBefore = remainedBefore;
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
	
	
	
}
