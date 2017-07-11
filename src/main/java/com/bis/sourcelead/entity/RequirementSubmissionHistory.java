package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "REQUIREMENT_SUBMISSION_HISTORY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementSubmissionHistory")
public class RequirementSubmissionHistory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@ManyToOne
    @JoinColumn(name="INTERVIEWER_ID", nullable=false )        
    private RequirementInterviewer interviewerId;

	@Column(name = "FROM_STAGE")
	private String fromStage;
	
	@Column(name = "TO_STAGE")
	private String toStage;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CREATED_USER")
	private String createdUser;
	
    @Transient
    private String fileName;
	 
    @Transient
    private String resumeid;
    
    @Transient
    private String createdDateStr;
    
    @Transient
    private String orgLocationId;
    
    @Transient
    private Long offerResumeid;
    
    @Transient
    private String loginUserName;
    
    @Transient
    private String userName;
    
    @Transient
    private String submissionStage;
    
    @Transient
    private String requirementReson;
    
    @Transient
    private String interviewStartTime;
    
    @Transient
    private String interviewEndTime;
    
    @Transient
    private String interviewDateStr;
    
    @Transient
    private String interviewType;
    
    @Transient
    private String interviewerName;
    
    @Transient
    private String intervieweSourceType;
    
    @Transient
    private String clientName;
    
    @Transient
    private Boolean isInCoolingOffPeriod;
    
    @Transient
    private String coolingOffPeriodEndDateStr;
    
    
    @Transient
	List<RequirementInterviewHistory> requirementInterviewHistoryList;
    
    @Transient
	List<RequirementOfferHistory> requirementOfferHistoryList;
    
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

	public RequirementInterviewer getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(RequirementInterviewer interviewerId) {
		this.interviewerId = interviewerId;
	}

	public String getFromStage() {
		return fromStage;
	}

	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}

	public String getToStage() {
		return toStage;
	}

	public void setToStage(String toStage) {
		this.toStage = toStage;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResumeid() {
		return resumeid;
	}

	public void setResumeid(String resumeid) {
		this.resumeid = resumeid;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOfferResumeid() {
		return offerResumeid;
	}

	public void setOfferResumeid(Long offerResumeid) {
		this.offerResumeid = offerResumeid;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
	}

	public String getRequirementReson() {
		return requirementReson;
	}

	public void setRequirementReson(String requirementReson) {
		this.requirementReson = requirementReson;
	}

	public String getInterviewStartTime() {
		return interviewStartTime;
	}

	public void setInterviewStartTime(String interviewStartTime) {
		this.interviewStartTime = interviewStartTime;
	}

	public String getInterviewEndTime() {
		return interviewEndTime;
	}

	public void setInterviewEndTime(String interviewEndTime) {
		this.interviewEndTime = interviewEndTime;
	}

	public String getInterviewDateStr() {
		return interviewDateStr;
	}

	public void setInterviewDateStr(String interviewDateStr) {
		this.interviewDateStr = interviewDateStr;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getIntervieweSourceType() {
		return intervieweSourceType;
	}

	public void setIntervieweSourceType(String intervieweSourceType) {
		this.intervieweSourceType = intervieweSourceType;
	}

	public List<RequirementInterviewHistory> getRequirementInterviewHistoryList() {
		return requirementInterviewHistoryList;
	}

	public void setRequirementInterviewHistoryList(List<RequirementInterviewHistory> requirementInterviewHistoryList) {
		this.requirementInterviewHistoryList = requirementInterviewHistoryList;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List<RequirementOfferHistory> getRequirementOfferHistoryList() {
		return requirementOfferHistoryList;
	}

	public void setRequirementOfferHistoryList(List<RequirementOfferHistory> requirementOfferHistoryList) {
		this.requirementOfferHistoryList = requirementOfferHistoryList;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Boolean getIsInCoolingOffPeriod() {
		return isInCoolingOffPeriod;
	}

	public void setIsInCoolingOffPeriod(Boolean isInCoolingOffPeriod) {
		this.isInCoolingOffPeriod = isInCoolingOffPeriod;
	}

	public String getCoolingOffPeriodEndDateStr() {
		return coolingOffPeriodEndDateStr;
	}

	public void setCoolingOffPeriodEndDateStr(String coolingOffPeriodEndDateStr) {
		this.coolingOffPeriodEndDateStr = coolingOffPeriodEndDateStr;
	}
}
