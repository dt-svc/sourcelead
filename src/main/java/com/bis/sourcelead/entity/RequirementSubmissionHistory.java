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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REQUIREMENT_SUBMISSION_HISTORY")
public class RequirementSubmissionHistory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "MASTER_USER_CODE")
	private String masterUserCode;
	
	@JsonProperty
    @Transient
    private String fileName;
	
    @JsonProperty
    @Transient
    private String fileUrl;
    
    @JsonProperty
    @Transient
    private String contentType;
	 
    @Transient
    @JsonProperty
    private String resumeid;
    
    @Transient
    @JsonProperty
    private String createdDateStr;
    
    @Transient
    @JsonProperty
    private String orgLocationId;
    
    @Transient
    @JsonProperty
    private Long offerResumeid;
    
    @Transient
    @JsonProperty
    @Lob
    private  byte[] docFile;
    
    @Transient
    @JsonProperty
    private String fileContentType;
    
    @Transient
    @JsonProperty
    private String loginUserName;
    
    @Transient
    @JsonProperty
    private String userName;
    
    @Transient
    @JsonProperty
    private String submissionStage;
    
    @Transient
    @JsonProperty
    private String requirementReson;
    
    @Transient
    @JsonProperty
    private String interviewStartTime;
    
    @Transient
    @JsonProperty
    private String interviewEndTime;
    
    @Transient
    @JsonProperty
    private String interviewDateStr;
    
    @Transient
    @JsonProperty
    private String interviewType;
    
    @Transient
    @JsonProperty
    private String interviewerName;
    
    @Transient
    @JsonProperty
    private String intervieweSourceType;
    
    @Transient
    @JsonProperty
    private String clientName;
    
    @Transient
    @JsonProperty
    private Boolean isInCoolingOffPeriod;
    
    @Transient
    @JsonProperty
    private String coolingOffPeriodEndDateStr;
    
    
    @Transient
	List<RequirementInterviewHistory> requirementInterviewHistoryList;
    
    @Transient
	List<RequirementOfferHistory> requirementOfferHistoryList;
    
    @Transient
    @JsonProperty
    private String candidateName;
    
    @Transient
	@JsonProperty
	private String requirementUUID;
    
    @Transient
	@JsonProperty
	private String jobTitle;
	
	@Transient
	@JsonProperty
	private String orgLocationName;
	
	@Transient
	@JsonProperty
	private String email;
	
	@Transient
	@JsonProperty
	private String mobilePhone;
	
	@Transient
	@JsonProperty
	private String resumeUUID;
	
	@Transient
	@JsonProperty
	private String primarySkill;
	
	@Transient
	@JsonProperty
	private String candidateScore;
	
	@Transient
	@JsonProperty
	private String submissionStatus;
	
	@Transient
	@JsonProperty
	private String candidateUUID;
	
   @Transient
   @JsonProperty
   private String payOutPeriod;
   @Transient
   @JsonProperty
   private String generateInvoice;
   
   @Transient
   @JsonProperty
   private String finalizedRate;
	
	
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


	public String getFileUrl()
	{
		return fileUrl;
	}

	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public byte[] getDocFile()
	{
		return docFile;
	}

	public void setDocFile(byte[] docFile)
	{
		this.docFile = docFile;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getCandidateName()
	{
		return candidateName;
	}

	public void setCandidateName(String candidateName)
	{
		this.candidateName = candidateName;
	}

	public String getRequirementUUID()
	{
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID)
	{
		this.requirementUUID = requirementUUID;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public String getOrgLocationName()
	{
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName)
	{
		this.orgLocationName = orgLocationName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getResumeUUID()
	{
		return resumeUUID;
	}

	public void setResumeUUID(String resumeUUID)
	{
		this.resumeUUID = resumeUUID;
	}

	public String getPrimarySkill()
	{
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill)
	{
		this.primarySkill = primarySkill;
	}

	public String getCandidateScore()
	{
		return candidateScore;
	}

	public void setCandidateScore(String candidateScore)
	{
		this.candidateScore = candidateScore;
	}

	public String getSubmissionStatus()
	{
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus)
	{
		this.submissionStatus = submissionStatus;
	}

	public String getCandidateUUID()
	{
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID)
	{
		this.candidateUUID = candidateUUID;
	}

	public String getPayOutPeriod()
	{
		return payOutPeriod;
	}

	public void setPayOutPeriod(String payOutPeriod)
	{
		this.payOutPeriod = payOutPeriod;
	}

	public String getGenerateInvoice()
	{
		return generateInvoice;
	}

	public void setGenerateInvoice(String generateInvoice)
	{
		this.generateInvoice = generateInvoice;
	}

	public String getMasterUserCode() {
		return masterUserCode;
	}

	public void setMasterUserCode(String masterUserCode) {
		this.masterUserCode = masterUserCode;
	}

	public String getFinalizedRate() {
		return finalizedRate;
	}

	public void setFinalizedRate(String finalizedRate) {
		this.finalizedRate = finalizedRate;
	}
	

}
