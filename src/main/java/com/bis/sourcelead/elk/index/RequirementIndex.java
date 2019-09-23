package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

/**
 * 
 * @author Rajashekar and Prasanna
 * @date 11th jan 2017
 */
public class RequirementIndex implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long requirementId;
	
	private Long orgLocationId;
	
	private String jobTitle;
	
	private String currentRateCurrency;
	
	private String numberOfPositions;
	
	private String numberOfApplicants;
	
	private Float rate;
	
	private Float maxRate;
	
	private String isreceivingApplicants;
	
	private String rateType;
	
	private String clientCurrencyCode;
	
	private Float clientRate;
	
	private Float clientMaxRate;
	
	private String clientRateType;
	
	private Float minExperience;
	
	private Float maxExperience;
	
	private String requirementStatus;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String location;
	
	private String zipCode;
	
	private String medicalClearence;
	
	private String securityClearance;
	
	private String overTime;
	
	private String backgroundCheck;
	
	private String gender;
	
	private String experienceLevel;
	
	private String projectClientID;
	
	
	private String[] education;
	
	private String client;
	
	private String orgRelationId;
	
	private String spocId;
	
	private String[] skills;
	
	private String[] noticePeriod;
	
	private String[] appliedProfessionals;
	
	private String[] appliedRecruiters;
	
	private String jobType;
	
	private String jobId;
	
	private String clientContactName;
	
	private String clientContactEmail;
	
	private String manager;
	
	private String managerUserId;
	
	private String priority; 

    private String createdDate;

	private Long reqCreatedBy;
	
	private String reqCreatedDate;
	
	private String reqUpdatedDate;

	private String updatedDate;

	private String reqUpdatedBy;
	
	private String isTemplate;
	
	private String[] reqLevel1ApproverUserId;
	
	private String[] reqLevel2ApproverUserId;
	
	private String[] teamOwnerUserId;
	
	private String[] teamAssignUserId;
	
	private String releasedBy;
	
	private String otherJobType;
	
	private String requirementUUID;
	
	private String requirementAppliedDate;

	private String isPublicJob;
	
	private String isCrowdSourcingJob;
	
	private String calDays;
	
	private String travelAllownace;
	
	private String postedOrg;
	
	private List<String> skillsList;
	
	private String isJobApplied;

	private String requirementSubmissionPreviousStatus;
	
	private String reqLevel1ApproverStatus;
	
	private String reqLevel2ApproverStatus;
	
	private Long referId;
	
	private String referedUser;
	
	private String industryName;
	
	private Integer noOfSearchHits;
	
	private String isReferedRequiremetnApplied;
	
	private String rewardPercentage;
	
	private String rewardFlat;
	
	private String rewardFlatRateType;
	
	private String rewardGuaranteePeriod;
	
	private String projectDurationYears;
	
	private String projectDurationMonths;
	
	private String rewardType;
	
	private Integer recruiterSubmissionsCount;
	
	private Long submissionId;
	
	private String reqCoolingOffPeriod;
	
	private String releaseJobToAllUsers;
	
	private String closedDate;
	
	private String description;
	
	private String jobAlertTitle;
	
	private String isJobSaved;
	
	private String savedJobUUID;
	
	private String referUUID;
	
	private String[] visaTypes;
	private String url;
	private String generatedId;
	
	private List<String> locationsList;
	
	private String isClientJob;
	
	private String primaryRecruiterUserId;
	
	private String tags;
	
	private String process;
	
	private Long workflowId;
	
	public String getPrimaryRecruiterUserId() {
		return primaryRecruiterUserId;
	}

	public void setPrimaryRecruiterUserId(String primaryRecruiterUserId) {
		this.primaryRecruiterUserId = primaryRecruiterUserId;
	}

	public String getManagerUserId() {
		return managerUserId;
	}

	public void setManagerUserId(String managerUserId) {
		this.managerUserId = managerUserId;
	}

	public String getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}
	

	public String[] getReqLevel1ApproverUserId() {
		return reqLevel1ApproverUserId;
	}

	public void setReqLevel1ApproverUserId(String[] reqLevel1ApproverUserId) {
		this.reqLevel1ApproverUserId = reqLevel1ApproverUserId;
	}

	public String[] getReqLevel2ApproverUserId() {
		return reqLevel2ApproverUserId;
	}

	public void setReqLevel2ApproverUserId(String[] reqLevel2ApproverUserId) {
		this.reqLevel2ApproverUserId = reqLevel2ApproverUserId;
	}

	public String[] getTeamAssignUserId() {
		return teamAssignUserId;
	}

	public void setTeamAssignUserId(String[] teamAssignUserId) {
		this.teamAssignUserId = teamAssignUserId;
	}

	
	public String[] getTeamOwnerUserId() {
		return teamOwnerUserId;
	}

	public void setTeamOwnerUserId(String[] teamOwnerUserId) {
		this.teamOwnerUserId = teamOwnerUserId;
	}

	public String getReqCreatedDate() {
		return reqCreatedDate;
	}

	public void setReqCreatedDate(String reqCreatedDate) {
		this.reqCreatedDate = reqCreatedDate;
	}

	public String getReqUpdatedDate() {
		return reqUpdatedDate;
	}

	public void setReqUpdatedDate(String reqUpdatedDate) {
		this.reqUpdatedDate = reqUpdatedDate;
	}

	public Long getReqCreatedBy() {
		return reqCreatedBy;
	}

	public void setReqCreatedBy(Long reqCreatedBy) {
		this.reqCreatedBy = reqCreatedBy;
	}

	public String getReqUpdatedBy() {
		return reqUpdatedBy;
	}

	public void setReqUpdatedBy(String reqUpdatedBy) {
		this.reqUpdatedBy = reqUpdatedBy;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	public String getClientCurrencyCode()
	{
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode)
	{
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public Float getClientRate()
	{
		return clientRate;
	}

	public void setClientRate(Float clientRate)
	{
		this.clientRate = clientRate;
	}

	public Float getClientMaxRate()
	{
		return clientMaxRate;
	}

	public void setClientMaxRate(Float clientMaxRate)
	{
		this.clientMaxRate = clientMaxRate;
	}

	public String getClientRateType()
	{
		return clientRateType;
	}

	public void setClientRateType(String clientRateType)
	{
		this.clientRateType = clientRateType;
	}

	public Float getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(Float minExperience) {
		this.minExperience = minExperience;
	}

	public Float getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(Float maxExperience) {
		this.maxExperience = maxExperience;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}




	public String[] getEducation() {
		return education;
	}

	public void setEducation(String[] education) {
		this.education = education;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getClientContactName() {
		return clientContactName;
	}

	public void setClientContactName(String clientContactName) {
		this.clientContactName = clientContactName;
	}

	public String getClientContactEmail() {
		return clientContactEmail;
	}

	public void setClientContactEmail(String clientContactEmail) {
		this.clientContactEmail = clientContactEmail;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}

	public String getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(String numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public String getNumberOfApplicants() {
		return numberOfApplicants;
	}

	public void setNumberOfApplicants(String numberOfApplicants) {
		this.numberOfApplicants = numberOfApplicants;
	}


	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(String releasedBy) {
		this.releasedBy = releasedBy;
	}

	public String getOtherJobType() {
		return otherJobType;
	}


	public void setOtherJobType(String otherJobType) {
		this.otherJobType = otherJobType;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public String getIsPublicJob() {
		return isPublicJob;
	}

	public void setIsPublicJob(String isPublicJob) {
		this.isPublicJob = isPublicJob;
	}

	public String getIsCrowdSourcingJob() {
		return isCrowdSourcingJob;
	}

	public void setIsCrowdSourcingJob(String isCrowdSourcingJob) {
		this.isCrowdSourcingJob = isCrowdSourcingJob;
	}

	public String getCalDays() {
		return calDays;
	}

	public void setCalDays(String calDays) {
		this.calDays = calDays;
	}

	public String getPostedOrg() {
		return postedOrg;
	}

	public void setPostedOrg(String postedOrg) {
		this.postedOrg = postedOrg;
	}

	public List<String> getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(List<String> skillsList) {
		this.skillsList = skillsList;
	}

	public String[] getAppliedProfessionals() {
		return appliedProfessionals;
	}

	public void setAppliedProfessionals(String[] appliedProfessionals) {
		this.appliedProfessionals = appliedProfessionals;
	}

	public String[] getAppliedRecruiters() {
		return appliedRecruiters;
	}

	public void setAppliedRecruiters(String[] appliedRecruiters) {
		this.appliedRecruiters = appliedRecruiters;
	}

	public String getIsJobApplied() {
		return isJobApplied;
	}

	public void setIsJobApplied(String isJobApplied) {
		this.isJobApplied = isJobApplied;
	}

	public String getRequirementSubmissionPreviousStatus() {
		return requirementSubmissionPreviousStatus;
	}

	public void setRequirementSubmissionPreviousStatus(String requirementSubmissionPreviousStatus) {
		this.requirementSubmissionPreviousStatus = requirementSubmissionPreviousStatus;
	}

	public String getRequirementAppliedDate() {
		return requirementAppliedDate;
	}

	public void setRequirementAppliedDate(String requirementAppliedDate) {
		this.requirementAppliedDate = requirementAppliedDate;
	}

	public String getReqLevel1ApproverStatus() {
		return reqLevel1ApproverStatus;
	}

	public void setReqLevel1ApproverStatus(String reqLevel1ApproverStatus) {
		this.reqLevel1ApproverStatus = reqLevel1ApproverStatus;
	}

	public String getReqLevel2ApproverStatus() {
		return reqLevel2ApproverStatus;
	}

	public void setReqLevel2ApproverStatus(String reqLevel2ApproverStatus) {
		this.reqLevel2ApproverStatus = reqLevel2ApproverStatus;
	}

	public Long getReferId() {
		return referId;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public String getReferedUser() {
		return referedUser;
	}

	public void setReferedUser(String referedUser) {
		this.referedUser = referedUser;
	}

	public Integer getNoOfSearchHits() {
		return noOfSearchHits;
	}

	public void setNoOfSearchHits(Integer noOfSearchHits) {
		this.noOfSearchHits = noOfSearchHits;
	}

	public String getIsReferedRequiremetnApplied() {
		return isReferedRequiremetnApplied;
	}

	public void setIsReferedRequiremetnApplied(String isReferedRequiremetnApplied) {
		this.isReferedRequiremetnApplied = isReferedRequiremetnApplied;
	}

	public String getRewardPercentage() {
		return rewardPercentage;
	}

	public void setRewardPercentage(String rewardPercentage) {
		this.rewardPercentage = rewardPercentage;
	}

	public String getRewardFlat() {
		return rewardFlat;
	}

	public void setRewardFlat(String rewardFlat) {
		this.rewardFlat = rewardFlat;
	}

	public String getRewardFlatRateType() {
		return rewardFlatRateType;
	}

	public void setRewardFlatRateType(String rewardFlatRateType) {
		this.rewardFlatRateType = rewardFlatRateType;
	}

	public String getRewardGuaranteePeriod() {
		return rewardGuaranteePeriod;
	}

	public void setRewardGuaranteePeriod(String rewardGuaranteePeriod) {
		this.rewardGuaranteePeriod = rewardGuaranteePeriod;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getRecruiterSubmissionsCount() {
		return recruiterSubmissionsCount;
	}

	public void setRecruiterSubmissionsCount(Integer recruiterSubmissionsCount) {
		this.recruiterSubmissionsCount = recruiterSubmissionsCount;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}


	public String getReqCoolingOffPeriod() {
		return reqCoolingOffPeriod;
	}

	public void setReqCoolingOffPeriod(String reqCoolingOffPeriod) {
		this.reqCoolingOffPeriod = reqCoolingOffPeriod;
	}
	public String getReleaseJobToAllUsers()
	{
		return releaseJobToAllUsers;
	}

	public void setReleaseJobToAllUsers(String releaseJobToAllUsers)
	{
		this.releaseJobToAllUsers = releaseJobToAllUsers;
	}

	public String[] getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String[] noticePeriod) {
		this.noticePeriod = noticePeriod;

	}

	

	public String getIndustryName()
	{
		return industryName;
	}

	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}

	public Float getMaxRate()
	{
		return maxRate;
	}

	public void setMaxRate(Float maxRate)
	{
		this.maxRate = maxRate;
	}

	public String getTravelAllownace()
	{
		return travelAllownace;
	}

	public void setTravelAllownace(String travelAllownace)
	{
		this.travelAllownace = travelAllownace;
	}

	public String getProjectDurationYears()
	{
		return projectDurationYears;
	}

	public void setProjectDurationYears(String projectDurationYears)
	{
		this.projectDurationYears = projectDurationYears;
	}

	public String getProjectDurationMonths()
	{
		return projectDurationMonths;
	}

	public void setProjectDurationMonths(String projectDurationMonths)
	{
		this.projectDurationMonths = projectDurationMonths;
	}

	public String getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(String closedDate)
	{
		this.closedDate = closedDate;
	}

	public String getIsreceivingApplicants()
	{
		return isreceivingApplicants;
	}

	public void setIsreceivingApplicants(String isreceivingApplicants)
	{
		this.isreceivingApplicants = isreceivingApplicants;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getJobAlertTitle()
	{
		return jobAlertTitle;
	}

	public void setJobAlertTitle(String jobAlertTitle)
	{
		this.jobAlertTitle = jobAlertTitle;
	}

	public String getIsJobSaved()
	{
		return isJobSaved;
	}

	public void setIsJobSaved(String isJobSaved)
	{
		this.isJobSaved = isJobSaved;
	}

	public String getSavedJobUUID()
	{
		return savedJobUUID;
	}

	public void setSavedJobUUID(String savedJobUUID)
	{
		this.savedJobUUID = savedJobUUID;
	}

	public String getReferUUID()
	{
		return referUUID;
	}

	public void setReferUUID(String referUUID)
	{
		this.referUUID = referUUID;
	}

	public String[] getVisaTypes()
	{
		return visaTypes;
	}

	public void setVisaTypes(String[] visaTypes)
	{
		this.visaTypes = visaTypes;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getMedicalClearence()
	{
		return medicalClearence;
	}

	public void setMedicalClearence(String medicalClearence)
	{
		this.medicalClearence = medicalClearence;
	}

	public String getSecurityClearance()
	{
		return securityClearance;
	}

	public void setSecurityClearance(String securityClearance)
	{
		this.securityClearance = securityClearance;
	}

	public String getOverTime()
	{
		return overTime;
	}

	public void setOverTime(String overTime)
	{
		this.overTime = overTime;
	}

	public String getBackgroundCheck()
	{
		return backgroundCheck;
	}

	public void setBackgroundCheck(String backgroundCheck)
	{
		this.backgroundCheck = backgroundCheck;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getExperienceLevel()
	{
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel)
	{
		this.experienceLevel = experienceLevel;
	}

	public String getProjectClientID()
	{
		return projectClientID;
	}

	public void setProjectClientID(String projectClientID)
	{
		this.projectClientID = projectClientID;
	}

	public String getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public String getSpocId()
	{
		return spocId;
	}

	public void setSpocId(String spocId)
	{
		this.spocId = spocId;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

	public String getIsClientJob() {
		return isClientJob;
	}

	public void setIsClientJob(String isClientJob) {
		this.isClientJob = isClientJob;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getProcess()
	{
		return process;
	}

	public void setProcess(String process)
	{
		this.process = process;
	}

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	
}
