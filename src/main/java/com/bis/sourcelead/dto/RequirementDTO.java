package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class RequirementDTO extends CommonDTO {

	private long id;
	
	private String staticPath;

	private List<FileInfo> fileInfo;

	private List<FileInfo> uploadedFileInfo;

	private List<String> assignedTo;

	private String template;

	@Required(desc = "job title")
	private String jobTitle;
	
	@Required(desc = "profilePercentage")
	private String profilePercentage;

	private String jobType;

	private String jobId;
	
	private String importValue;

	private String otherJobType;

	private Long orgLocationId;

	private String projectDurationYears;

	private String projectDurationMonths;

	private String positions;

	private String education;

	private String minExp;

	private String maxExp;

	private String industryName;

	private String currencyCode;

	private String rate;

	private String maxRate;

	private String rateValidation;

	private String rateType;

	private String travelAllowance;

	private String closedDate;

	private String isreceivingApplicants;

	private String location;

	private String countryReq;

	private String country;

	private String state;

	private String city;

	private String zipCode;

	private String skills;

	private String medicalClearence;
	
	private String securityClearance;
	
	private String overTime;
	
	private String backgroundCheck;
	
	private String gender;
	
	private String experienceLevel;
	
	
	private List<String> visa;

	@CleanHtml(escape=false)
	private String description;

	private List<String> noticePeriod;

	private String coolingOff;

	private String isTemplate;

	private String isClient;

	@Required(desc = "client", dependsOn = "isClient", parentValue = "Y")
	private String selectClient;

	private String clientCurrencyCode;

	private String clientRate;
	
	private String clientMaxRate;

	private String clientRateType;

	@Required(desc = "clientForm", dependsOn = "isClient", parentValue = "Y")
	private String clientSubmissionForm;

	private String cd;
	
	private String jobRate;
	
	private String incentivized;
	
	private String payOutPeriod;
	
	private List<RequirementIncentiveDTO> requirementIncentive;
	
	private String arrDeletedIds;

	private String approver;

	private List<String> parallelApprover;

	private String sequentialApprover1;

	private String sequentialApprover2;

	private String manager;

	private int vendorInterviewsCount;

	private int vendorBackoutCount;

	private int vendorOfferedCount;
	
	private String noOfSubmittedApplicants;
	
	private String postedDateStr;
	
	private String requirementSubmissionStatus;
	
	private String noOfInterviewedApplicants;
	
	private String noOfOfferedApplicants;
	
	private String noOfApplicants;
	
	private String noOfShortlistApplicants;
	
	private String noOfRejectedApplicants;
	
	private String noOfJoinedApplicants;
	
	private String noOfBackoutApplicants;
	

	private String projectClientID;
	
	private String projectName;

	private String sortColumn;
	
	private String sortOrder;
	 
	private List<Map<String, String>> order;
	 
	private String searchValue;
	
	private String jobCampaignType;
	
	private int start;
	 
	private int length;
	
	private List<CustomFormFieldDTO> customFormData;
	
	private String url;
	
	private String fileURL;
	
	private String fileContentType;
	
	private String fileName;
	
	private List<RequirementLocationsDTO> locationInfo;
	
	private List<RequirementLocationsDTO> savedLocationInfo;

	private List<String> locationsList;
	
	private boolean walkIn;
	
	private String walkInDays;
	
	private boolean jobFair;
	
	private long jobFairId;
	
	private String[] jobFairDates;
	
	private Map<Long, String[]> jobFairMap;
	
	private String orgTeams;
	
	private String process;
	
	private String jobCreatedDateRange;
	
	private String clientIds;
	
	private String startDateStr;
	
	private String endDateStr;
	// below lines is commented for to move in next release build.!--//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
	private String workflow;
	/*
	 * private List<Map<String, String>> assinedToTeam;
	 * 
	 * private List<AssinedTo> assinedTeamInfo;
	 */

	public Map<Long, String[]> getJobFairMap() {
		return jobFairMap;
	}

	public boolean isJobFair() {
		return jobFair;
	}

	public void setJobFair(boolean jobFair) {
		this.jobFair = jobFair;
	}

	public void setJobFairMap(Map<Long, String[]> jobFairMap) {
		this.jobFairMap = jobFairMap;
	}

	public String getOrgTeams() {
		return orgTeams;
	}

	public void setOrgTeams(String orgTeams) {
		this.orgTeams = orgTeams;
	}

	public String getNoOfOfferedApplicants() {
		return noOfOfferedApplicants;
	}

	public boolean isWalkIn() {
		return walkIn;
	}

	public void setWalkIn(boolean isDriveJob) {
		this.walkIn = isDriveJob;
	}

	public String getWalkInDays() {
		return walkInDays;
	}

	public void setWalkInDays(String driveDays) {
		this.walkInDays = driveDays;
	}

	public void setNoOfOfferedApplicants(String noOfOfferedApplicants) {
		this.noOfOfferedApplicants = noOfOfferedApplicants;
	}

	public String getNoOfInterviewedApplicants() {
		return noOfInterviewedApplicants;
	}

	public void setNoOfInterviewedApplicants(String noOfInterviewedApplicants) {
		this.noOfInterviewedApplicants = noOfInterviewedApplicants;
	}

	public String getRequirementSubmissionStatus() {
		return requirementSubmissionStatus;
	}

	public void setRequirementSubmissionStatus(String requirementSubmissionStatus) {
		this.requirementSubmissionStatus = requirementSubmissionStatus;
	}

	public int getVendorInterviewsCount() {
		return vendorInterviewsCount;
	}

	public void setVendorInterviewsCount(int vendorInterviewsCount) {
		this.vendorInterviewsCount = vendorInterviewsCount;
	}

	public int getVendorBackoutCount() {
		return vendorBackoutCount;
	}

	public void setVendorBackoutCount(int vendorBackoutCount) {
		this.vendorBackoutCount = vendorBackoutCount;
	}

	public int getVendorOfferedCount() {
		return vendorOfferedCount;
	}

	public void setVendorOfferedCount(int vendorOfferedCount) {
		this.vendorOfferedCount = vendorOfferedCount;
	}

	public String getNoOfSubmittedApplicants() {
		return noOfSubmittedApplicants;
	}

	public void setNoOfSubmittedApplicants(String noOfSubmittedApplicants) {
		this.noOfSubmittedApplicants = noOfSubmittedApplicants;
	}

	public String getPostedDateStr() {
		return postedDateStr;
	}

	public void setPostedDateStr(String postedDateStr) {
		this.postedDateStr = postedDateStr;
	}

	private String priority;

	private String applicationFrom;

	private String releaseToAllUsers;

	private String requirementId;

	private String templateId;

	private String saveType;

	@Required(desc = "clientContact", dependsOn = "isClient", parentValue = "Y")
	private String selectContact;

	private String assignedToMaxCount;

	private String assignedTeamId;

	private List<String> assignUsers;

	private String jobOtherType;

	// for save elk requirement
	private String generatedId;

	private String createdDate;

	private Long reqCreatedBy;

	private String reqCreatedDate;

	private String reqUpdatedDate;

	private String updatedDate;

	private String reqUpdatedBy;

	private String requirementStatus;

	private String[] reqLevel1ApproverUserId;

	private String[] reqLevel2ApproverUserId;

	private String[] teamOwnerUserId;

	private String[] teamAssignUserId;

	private String requirementUUID;
	
	private String[] reqUUIDArr;

	private String requirementAppliedDate;

	private String isPublicJob;

	private String isCrowdSourcingJob;

	private String postedOrg;

	private String[] appliedProfessionals;

	private String[] appliedRecruiters;

	private String rewardType;

	private String rewardPercentage;

	private String rewardFlat;

	private String rewardFlatRateType;

	private String rewardGuaranteePeriod;

	private String reqLevel1ApproverStatus;

	private String reqLevel2ApproverStatus;

	private String managerUserId;
	
	private long primaryRecruiterUserId;

	private String client;

	private String clientContactName;

	private String clientContactEmail;

	private String numberOfApplicants;

	private String releasedBy;

	private String currentRateCurrency;
	
	private BigInteger reqId;
	
	private BigInteger requirementCreatedUserId;
	

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	public String getOtherJobType() {
		return otherJobType;
	}

	public void setOtherJobType(String otherJobType) {
		this.otherJobType = otherJobType;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getProjectDurationYears() {
		return projectDurationYears;
	}

	public void setProjectDurationYears(String projectDurationYears) {
		this.projectDurationYears = projectDurationYears;
	}

	public String getProjectDurationMonths() {
		return projectDurationMonths;
	}

	public void setProjectDurationMonths(String projectDurationMonths) {
		this.projectDurationMonths = projectDurationMonths;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMinExp() {
		return minExp;
	}

	public void setMinExp(String minExp) {
		this.minExp = minExp;
	}

	public String getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(String maxExp) {
		this.maxExp = maxExp;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getRateValidation() {
		return rateValidation;
	}

	public void setRateValidation(String rateValidation) {
		this.rateValidation = rateValidation;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getTravelAllowance() {
		return travelAllowance;
	}

	public void setTravelAllowance(String travelAllowance) {
		this.travelAllowance = travelAllowance;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getIsreceivingApplicants() {
		return isreceivingApplicants;
	}

	public void setIsreceivingApplicants(String isreceivingApplicants) {
		this.isreceivingApplicants = isreceivingApplicants;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountryReq() {
		return countryReq;
	}

	public void setCountryReq(String countryReq) {
		this.countryReq = countryReq;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public List<String> getVisa() {
		return visa;
	}

	public void setVisa(List<String> visa) {
		this.visa = visa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(List<String> noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getCoolingOff() {
		return coolingOff;
	}

	public void setCoolingOff(String coolingOff) {
		this.coolingOff = coolingOff;
	}

	public String getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getIsClient() {
		return isClient;
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public String getSelectClient() {
		return selectClient;
	}

	public void setSelectClient(String selectClient) {
		this.selectClient = selectClient;
	}

	public String getClientCurrencyCode() {
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode) {
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public String getClientRate() {
		return clientRate;
	}

	public void setClientRate(String clientRate) {
		this.clientRate = clientRate;
	}

	public String getClientRateType() {
		return clientRateType;
	}

	public void setClientRateType(String clientRateType) {
		this.clientRateType = clientRateType;
	}

	public String getClientSubmissionForm() {
		return clientSubmissionForm;
	}

	public void setClientSubmissionForm(String clientSubmissionForm) {
		this.clientSubmissionForm = clientSubmissionForm;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getJobRate() {
		return jobRate;
	}

	public void setJobRate(String jobRate) {
		this.jobRate = jobRate;
	}

	public String getIncentivized() {
		return incentivized;
	}

	public void setIncentivized(String incentivized) {
		this.incentivized = incentivized;
	}

	public String getPayOutPeriod() {
		return payOutPeriod;
	}

	public void setPayOutPeriod(String payOutPeriod) {
		this.payOutPeriod = payOutPeriod;
	}

	public List<RequirementIncentiveDTO> getRequirementIncentive() {
		return requirementIncentive;
	}

	public void setRequirementIncentive(List<RequirementIncentiveDTO> requirementIncentive) {
		this.requirementIncentive = requirementIncentive;
	}

	public String getArrDeletedIds() {
		return arrDeletedIds;
	}

	public void setArrDeletedIds(String arrDeletedIds) {
		this.arrDeletedIds = arrDeletedIds;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public List<String> getParallelApprover() {
		return parallelApprover;
	}

	public void setParallelApprover(List<String> parallelApprover) {
		this.parallelApprover = parallelApprover;
	}

	public String getSequentialApprover1() {
		return sequentialApprover1;
	}

	public void setSequentialApprover1(String sequentialApprover1) {
		this.sequentialApprover1 = sequentialApprover1;
	}

	public String getSequentialApprover2() {
		return sequentialApprover2;
	}

	public void setSequentialApprover2(String sequentialApprover2) {
		this.sequentialApprover2 = sequentialApprover2;
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

	public String getApplicationFrom() {
		return applicationFrom;
	}

	public void setApplicationFrom(String applicationFrom) {
		this.applicationFrom = applicationFrom;
	}

	public String getReleaseToAllUsers() {
		return releaseToAllUsers;
	}

	public void setReleaseToAllUsers(String releaseToAllUsers) {
		this.releaseToAllUsers = releaseToAllUsers;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getSelectContact() {
		return selectContact;
	}

	public void setSelectContact(String selectContact) {
		this.selectContact = selectContact;
	}

	public String getAssignedToMaxCount() {
		return assignedToMaxCount;
	}

	public void setAssignedToMaxCount(String assignedToMaxCount) {
		this.assignedToMaxCount = assignedToMaxCount;
	}

	public String getAssignedTeamId() {
		return assignedTeamId;
	}

	public void setAssignedTeamId(String assignedTeamId) {
		this.assignedTeamId = assignedTeamId;
	}

	public List<String> getAssignUsers() {
		return assignUsers;
	}

	public void setAssignUsers(List<String> assignUsers) {
		this.assignUsers = assignUsers;
	}

	public String getJobOtherType() {
		return jobOtherType;
	}

	public void setJobOtherType(String jobOtherType) {
		this.jobOtherType = jobOtherType;
	}

	public List<String> getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(List<String> assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getReqCreatedBy() {
		return reqCreatedBy;
	}

	public void setReqCreatedBy(Long reqCreatedBy) {
		this.reqCreatedBy = reqCreatedBy;
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

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getReqUpdatedBy() {
		return reqUpdatedBy;
	}

	public void setReqUpdatedBy(String reqUpdatedBy) {
		this.reqUpdatedBy = reqUpdatedBy;
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

	public String[] getTeamOwnerUserId() {
		return teamOwnerUserId;
	}

	public void setTeamOwnerUserId(String[] teamOwnerUserId) {
		this.teamOwnerUserId = teamOwnerUserId;
	}

	public String[] getTeamAssignUserId() {
		return teamAssignUserId;
	}

	public void setTeamAssignUserId(String[] teamAssignUserId) {
		this.teamAssignUserId = teamAssignUserId;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public String[] getReqUUIDArr() {
		return reqUUIDArr;
	}

	public void setReqUUIDArr(String[] reqUUIDArr) {
		this.reqUUIDArr = reqUUIDArr;
	}

	public String getRequirementAppliedDate() {
		return requirementAppliedDate;
	}

	public void setRequirementAppliedDate(String requirementAppliedDate) {
		this.requirementAppliedDate = requirementAppliedDate;
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

	public String getPostedOrg() {
		return postedOrg;
	}

	public void setPostedOrg(String postedOrg) {
		this.postedOrg = postedOrg;
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

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
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

	public String getManagerUserId() {
		return managerUserId;
	}

	public void setManagerUserId(String managerUserId) {
		this.managerUserId = managerUserId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public String getNumberOfApplicants() {
		return numberOfApplicants;
	}

	public void setNumberOfApplicants(String numberOfApplicants) {
		this.numberOfApplicants = numberOfApplicants;
	}

	public String getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(String releasedBy) {
		this.releasedBy = releasedBy;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
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

	public String getClientMaxRate()
	{
		return clientMaxRate;
	}

	public void setClientMaxRate(String clientMaxRate)
	{
		this.clientMaxRate = clientMaxRate;
	}

	

	public String getProjectClientID()
	{
		return projectClientID;
	}

	public void setProjectClientID(String projectClientID)
	{
		this.projectClientID = projectClientID;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}


	public String getSortColumn()
	{
		return sortColumn;
	}

	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder()
	{
		return order;
	}

	public void setOrder(List<Map<String, String>> order)
	{
		this.order = order;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public String getJobCampaignType()
	{
		return jobCampaignType;
	}

	public void setJobCampaignType(String jobCampaignType)
	{
		this.jobCampaignType = jobCampaignType;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public List<CustomFormFieldDTO> getCustomFormData() {
		return customFormData;
	}

	public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
		this.customFormData = customFormData;
	}


	public String getNoOfApplicants() {
		return noOfApplicants;
	}

	public void setNoOfApplicants(String noOfApplicants) {
		this.noOfApplicants = noOfApplicants;
	}

	public String getNoOfShortlistApplicants() {
		return noOfShortlistApplicants;
	}

	public void setNoOfShortlistApplicants(String noOfShortlistApplicants) {
		this.noOfShortlistApplicants = noOfShortlistApplicants;
	}

	public String getNoOfRejectedApplicants() {
		return noOfRejectedApplicants;
	}

	public void setNoOfRejectedApplicants(String noOfRejectedApplicants) {
		this.noOfRejectedApplicants = noOfRejectedApplicants;
	}

	public String getNoOfJoinedApplicants() {
		return noOfJoinedApplicants;
	}

	public void setNoOfJoinedApplicants(String noOfJoinedApplicants) {
		this.noOfJoinedApplicants = noOfJoinedApplicants;
	}

	public String getNoOfBackoutApplicants() {
		return noOfBackoutApplicants;
	}

	public void setNoOfBackoutApplicants(String noOfBackoutApplicants) {
		this.noOfBackoutApplicants = noOfBackoutApplicants;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}


	public List<RequirementLocationsDTO> getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(List<RequirementLocationsDTO> locationInfo) {
		this.locationInfo = locationInfo;
	}
	public String getImportValue() {
		return importValue;
	}

	public List<RequirementLocationsDTO> getSavedLocationInfo() {
		return savedLocationInfo;
	}

	public void setSavedLocationInfo(List<RequirementLocationsDTO> savedLocationInfo) {
		this.savedLocationInfo = savedLocationInfo;
	}

	public void setImportValue(String importValue) {
		this.importValue = importValue;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

	public long getPrimaryRecruiterUserId() {
		return primaryRecruiterUserId;
	}

	public void setPrimaryRecruiterUserId(long primaryRecruiterUserId) {
		this.primaryRecruiterUserId = primaryRecruiterUserId;
	}
	
	public BigInteger getReqId() {
		return reqId;
	}

	public void setReqId(BigInteger reqId) {
		this.reqId = reqId;
	}

	public BigInteger getRequirementCreatedUserId() {
		return requirementCreatedUserId;
	}

	public void setRequirementCreatedUserId(BigInteger requirementCreatedUserId) {
		this.requirementCreatedUserId = requirementCreatedUserId;
	}

	public long getJobFairId() {
		return jobFairId;
	}

	public void setJobFairId(long jobFairId) {
		this.jobFairId = jobFairId;
	}

	public String[] getJobFairDates() {
		return jobFairDates;
	}

	public void setJobFairDates(String[] jobFairDates) {
		this.jobFairDates = jobFairDates;
	}

	public String getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(String profilePercentage) {
		this.profilePercentage = profilePercentage;
	}

	public String getProcess()
	{
		return process;
	}

	public void setProcess(String process)
	{
		this.process = process;
	}

	public String getJobCreatedDateRange() {
		return jobCreatedDateRange;
	}

	public void setJobCreatedDateRange(String jobCreatedDateRange) {
		this.jobCreatedDateRange = jobCreatedDateRange;
	}

	public String getClientIds() {
		return clientIds;
	}

	public void setClientIds(String clientIds) {
		this.clientIds = clientIds;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	
	
}
