package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bis.sourcelead.enums.ApplicantSubmissionStatus;

@SuppressWarnings("serial")
public class RequirementSubmissionHistoryDTO extends SearchDTO implements Serializable{

	private String candidateName;
	private String jobTitle;
	private String requirementUUID;
	private String requirementId;
	private String orgLocationName;
	private String email;
	private String mobilePhone;
	private String resumeUUID;
	private String fileUrl;
	private String primarySkill;
	private String createdUser;
	private String candidateScore;
	private String submissionStatus;
	private String candidateUUID;
	private Long SubmissionId;
	private String fileName;
	private String contentType;
	private String submittedOn;
	private String experienceYears;
	private String finalizedRate;
	private String submittedBy;
	private String toStage;
	private String appliedOn;
	private int numOfInterview;
	private String offerJoinDate;
	private String offerRate;
	private String positionType;
	private String attachment;
	private Long offerId;
	private String paymentType;
	private String paymentMode;
	private String experienceMonths;
	private Long orgLocationId;
	private String tabType;
	private String frequency;
	private String fromDate;
	private String toDate;
	private String employmentType;
	private String clientId;
	private String teamId;
	private String exportType;
	private List<String> locationsList;
	private String jobGeneratedId;
	private String backoutType;
	private String backoutReason;
	private String incentivize;
	private String searchType;
	private String jobRating;
	private String rejectedType;
	private String rejectedReason;
	private String joinDate;
	private String incentivizedType;
	private String applicants;
	private String offerRateforYearly;
	private String marginRateYearly;
	
	private int payTerm;
	
	private String jobRate;
	
	private String interviewOn;
	
	@Enumerated(EnumType.STRING)
	private ApplicantSubmissionStatus finalsSubmissionStatus;
	
	private String type;
	
	private String payOutPeriod;
	
	private String generateInvoice;
	
	public String getCandidateName()
	{
		return candidateName;
	}
	public void setCandidateName(String candidateName)
	{
		this.candidateName = candidateName;
	}
	public String getJobTitle()
	{
		return jobTitle;
	}
	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}
	public String getRequirementUUID()
	{
		return requirementUUID;
	}
	public void setRequirementUUID(String requirementUUID)
	{
		this.requirementUUID = requirementUUID;
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
	public String getFileUrl()
	{
		return fileUrl;
	}
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	public String getPrimarySkill()
	{
		return primarySkill;
	}
	public void setPrimarySkill(String primarySkill)
	{
		this.primarySkill = primarySkill;
	}
	public String getCreatedUser()
	{
		return createdUser;
	}
	public void setCreatedUser(String createdUser)
	{
		this.createdUser = createdUser;
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
	public Long getSubmissionId()
	{
		return SubmissionId;
	}
	public void setSubmissionId(Long submissionId)
	{
		SubmissionId = submissionId;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String getSubmittedOn()
	{
		return submittedOn;
	}
	public void setSubmittedOn(String submittedOn)
	{
		this.submittedOn = submittedOn;
	}
	public String getExperienceYears()
	{
		return experienceYears;
	}
	public void setExperienceYears(String experienceYears)
	{
		this.experienceYears = experienceYears;
	}
	public String getFinalizedRate()
	{
		return finalizedRate;
	}
	public void setFinalizedRate(String finalizedRate)
	{
		this.finalizedRate = finalizedRate;
	}
	public String getSubmittedBy()
	{
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy)
	{
		this.submittedBy = submittedBy;
	}
	public String getToStage()
	{
		return toStage;
	}
	public void setToStage(String toStage)
	{
		this.toStage = toStage;
	}
	public String getAppliedOn()
	{
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn)
	{
		this.appliedOn = appliedOn;
	}
	public int getNumOfInterview()
	{
		return numOfInterview;
	}
	public void setNumOfInterview(int numOfInterview)
	{
		this.numOfInterview = numOfInterview;
	}
	public String getOfferJoinDate()
	{
		return offerJoinDate;
	}
	public void setOfferJoinDate(String offerJoinDate)
	{
		this.offerJoinDate = offerJoinDate;
	}
	public String getOfferRate()
	{
		return offerRate;
	}
	public void setOfferRate(String offerRate)
	{
		this.offerRate = offerRate;
	}
	public String getPositionType()
	{
		return positionType;
	}
	public void setPositionType(String positionType)
	{
		this.positionType = positionType;
	}
	public String getAttachment()
	{
		return attachment;
	}
	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}
	public Long getOfferId()
	{
		return offerId;
	}
	public void setOfferId(Long offerId)
	{
		this.offerId = offerId;
	}
	public String getPaymentType()
	{
		return paymentType;
	}
	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}
	public String getPaymentMode()
	{
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode)
	{
		this.paymentMode = paymentMode;
	}
	public String getExperienceMonths()
	{
		return experienceMonths;
	}
	public void setExperienceMonths(String experienceMonths)
	{
		this.experienceMonths = experienceMonths;
	}
	public Long getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getTabType()
	{
		return tabType;
	}
	public void setTabType(String tabType)
	{
		this.tabType = tabType;
	}
	public String getFrequency()
	{
		return frequency;
	}
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public List<String> getLocationsList() {
		return locationsList;
	}
	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}
	public String getJobGeneratedId() {
		return jobGeneratedId;
	}
	public void setJobGeneratedId(String jobGeneratedId) {
		this.jobGeneratedId = jobGeneratedId;
	}
	public String getBackoutType() {
		return backoutType;
	}
	public String getBackoutReason() {
		return backoutReason;
	}
	public String getIncentivize() {
		return incentivize;
	}
	 
	public void setBackoutType(String backoutType) {
		this.backoutType = backoutType;
	}
	public void setBackoutReason(String backoutReason) {
		this.backoutReason = backoutReason;
	}
	public void setIncentivize(String incentivize) {
		this.incentivize = incentivize;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getJobRating() {
		return jobRating;
	}
	public void setJobRating(String jobRating) {
		this.jobRating = jobRating;
	}
	public String getRejectedType() {
		return rejectedType;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedType(String rejectedType) {
		this.rejectedType = rejectedType;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getIncentivizedType() {
		return incentivizedType;
	}
	public void setIncentivizedType(String incentivizedType) {
		this.incentivizedType = incentivizedType;
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
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	public ApplicantSubmissionStatus getFinalsSubmissionStatus() {
		return finalsSubmissionStatus;
	}
	public void setFinalsSubmissionStatus(ApplicantSubmissionStatus finalsSubmissionStatus) {
		this.finalsSubmissionStatus = finalsSubmissionStatus;
	}
	public String getApplicants()
	{
		return applicants;
	}
	public void setApplicants(String applicants)
	{
		this.applicants = applicants;
	}
	public int getPayTerm()
	{
		return payTerm;
	}
	public void setPayTerm(int payTerm)
	{
		this.payTerm = payTerm;
	}
	public String getJobRate()
	{
		return jobRate;
	}
	public void setJobRate(String jobRate)
	{
		this.jobRate = jobRate;
	}
	public String getOfferRateforYearly() {
		return offerRateforYearly;
	}
	public void setOfferRateforYearly(String offerRateforYearly) {
		this.offerRateforYearly = offerRateforYearly;
	}
	public String getMarginRateYearly() {
		return marginRateYearly;
	}
	public void setMarginRateYearly(String marginRateYearly) {
		this.marginRateYearly = marginRateYearly;
	}
	public String getInterviewOn() {
		return interviewOn;
	}
	public void setInterviewOn(String interviewOn) {
		this.interviewOn = interviewOn;
	}
	
	
}