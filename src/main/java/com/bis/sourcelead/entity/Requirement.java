package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.fasterxml.jackson.annotation.JsonProperty;

	/**
	 * @author BIS101
	 *
	 */
	@Entity
	@Table(name = "REQUIREMENT")
	//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirement")
	public class Requirement implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "JOB_TYPE")
	private String jobType;
	
	@Column(name = "OTHER_JOBTYPE")
	private String otherJobType;
		
	@Column(name = "JOB_ID")
	private String jobId;
	
	@Column(name = "POSITIONS")
	private String positions;
	
	@Column(name = "EDUCATION", columnDefinition = "TEXT default NULL")
	private String education;
	
	@Column(name = "MIN_EXP")
	private String minExp;
	
	@Column(name = "MAX_EXP")
	private String maxExp;
	
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "MAX_RATE")
	private String maxRate;
	
	@Column(name = "RATE_TYPE") 
	private String rateType;
	
	@Column(name = "APPRVER_HIERARCHY")
	private String approverHierarchy;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "COUNTRY", columnDefinition = "TEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "TEXT default NULL")
	private String state;
	
	@Column(name = "CITY", columnDefinition = "TEXT default NULL")
	private String city;
	
	@Column(name = "LOCATION", columnDefinition = "TEXT default NULL")
	private String location;
	
	@Column(name = "ZIPCODE")
	private String zipCode;
	
	@Column(name = "SKILLS", columnDefinition = "TEXT default NULL")
	private String skills;
	
	@Column(name = "DESCRIPTION" , columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name = "NOTICEPERIOD", columnDefinition = "TEXT default NULL")
	private String noticePeriod;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "IS_CLIENT_JOB")
	private String isClientJob;
	
	@Column(name = "IS_RECEIVING_APPLICANTS")
	private String isreceivingApplicants;
	
	@Column(name = "IS_PUBLIC")
	private String isPublic;
	
	@Column(name = "IS_CROWD_SOURCING")
	private String isCrowdSourcing;
	
	@Column(name = "REQUIREMENT_UUID")
	private String requirementUUID;
	
	@Column(name = "INDUSTRY_NAME")
	private String industryName;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )        
    private OrgRelations clientId;
	   
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="SPOC_ID", nullable=false )        
    private OrgSpoc spocId;
	
	@Column(name = "CLIENT_CURRENCY_CODE")
	private String clientCurrencyCode;
	
	@Column(name = "CLIENT_RATE")
	private String clientRate;
	
	@Column(name = "CLIENT_MAX_RATE")
	private String clientMaxRate;
	
	@Column(name = "CLIENT_RATE_TYPE")
	private String clientRateType;
	
	@Column(name = "JOB_RATE")
	private String jobRate;
	
	@Column(name = "INCENTIVIZED")
	private String incentivized;
	
	@Column(name = "PAY_OUT_PERIOD")
	private String payOutPeriod;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "requirementId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementIncentive> requirementIncentive = new HashSet<RequirementIncentive>(0);
	
	@Column(name = "TRAVEL_ALLOWANCE")
	private String travelAllowance;
	
	@Column(name = "POSTED_ORG_NAME")
	private String postedOrgName;
	
	
	@Column(name = "REWARD_PERCENTAGE")
	private String rewardPercentage;
	
	@Column(name = "REWARD_FLAT")
	private String rewardFlat;
	
	@Column(name = "REWARD_FLAT_RATE_TYPE")
	private String rewardFlatRateType;
	
	@Column(name = "REWARD_GUARANTEE_PERIOD")
	private String rewardGuaranteePeriod;
	
	
	@Column(name = "REWARD_TYPE")
	private String rewardType;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="SUBMISSION_TEMPLATE_ID" )        
    private SubmissionTemplate submissionTemplateId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CLIENT_SUBMISSION_TEMPLATE_ID" )        
    private SubmissionTemplate clientSubmissionTemplateId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="MANAGER_USER_ID", nullable=false )        
    private User managerUserId;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "PRIMARY_RECRUITER_USER_ID", referencedColumnName = "id")
	private User primaryRecruiterUserId;
	
	@Column(name="IS_Template")
	private String isTemplate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name = "Process")
	private String process;

	@Column(name = "REQUIREMENT_SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	@JsonProperty
	private REQUIREMENT_SUBMISSION_STATUS requirementSubmissionStatus;
	
	@Column(name = "REQUIREMENT_SUBMISSION_PREVIOUS_STATUS")
	@Enumerated(EnumType.STRING)
	private REQUIREMENT_SUBMISSION_STATUS requirementSubmissionPreviousStatus;

	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String submissionTemplateJsonString;
	
	
	@Column(name = "CLIENT_SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String clientSubmissionTemplateJsonString;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="PARENT_REQUIREMENT_ID")        
    private Requirement parentRequirement;
	
	@Column(name="RELEASED_BY")
	@Type(type="text")
	private String releasedBy;
	
	@Column(name="APPLIED_PROFESSIONALS")
	@Type(type="text")
	private String appliedProfessionals;
	
	@Column(name="APPLIED_RECRUITERS")
	@Type(type="text")
	private String appliedRecruiters;

	@Column(name = "TOTAL_APPLICANTS",nullable = false,columnDefinition = "INT NULL DEFAULT 0")
	private Integer totalApplicants;
	
	@Column(name="PRIORITY")
	private String priority;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CREATED_BY", nullable=false )        
    private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "PROJECT_DURATION_YEARS")
	private String projectDurationYears;
	
	@Column(name = "PROJECT_DURATION_MONTHS")
	private String projectDurationMonths;
	
	
	@Column(name = "REQUIRMENT_CLOSED_DATE")
	private Date closedDate;
	
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "VISA_TYPE", columnDefinition = "TEXT default NULL")
	private String visaType;
	
	
	@Column(name = "REQ_COOLINGOFF_PERIOD")
	private String reqCoolingOffPeriod;
	
	@Column(name = "RELEASE_JOB_TO_ALL_USERS")
	private String releaseJobToAllUsers;
	
	@Column(name = "MEDICAL_CLEARENCE")
	private String medicalClearence;
	
	@Column(name = "SECURITY_CLEARANCE")
	private String securityClearance;
	

	@Column(name = "OVER_TIME")
	private String overTime;
	
	@Column(name = "BACKGROUNG_CHECK")
	private String backgroundCheck;
	
	@Column(name = "GENDER")
	private String gender;
	
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="PROJECT_CLIENT_ID", nullable=true) 
	private ProjectClients projectClientID;
	
	//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "WORKFLOW_ID",  nullable=true)
	private RequirementWorkFlow workflowId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="EXPERIENCE_LEVEL", nullable=true) 
	private LookUpData experienceLevel;
	
	@Column(name = "IS_WALK_IN")
	private boolean walkIn;
	
	@Column(name = "WALK_IN_DAYS", columnDefinition = "TEXT default NULL")
	private String walkInDays;
	
	@Column(name = "REOPENED_DATE")
	private Date repoenedDate;
	
	@Column(name = "PROFILE_PERCENTAGE")
	private Integer profilePercentage;

	@Transient
	private String closedDateStringFormat;

	@Transient
	private String clientSubmissionTemplate;

	@Transient
	private String requirementCreatedDateToString;

	@Transient
	private Long requirementId;

	@Transient
	private String isJobApplied;

	@Transient
	private String projectName;
        
        @Transient
	private List<Long> jobBoardAccountIdList;
        
        @NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "requirement")
	@Fetch(FetchMode.SELECT)
	private List<RequirementJobBoardInfo> requirementJobBoardInfoList;

	@Transient
	private String msg;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "requirementId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementAssignTo> requirementAssignTo = new HashSet<RequirementAssignTo>(0);

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "requirementId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementApproverHierarchy> requirementApprover = new HashSet<RequirementApproverHierarchy>(0);

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "requirementId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementLocations> requirementLocations = new HashSet<RequirementLocations>(0);

	@Transient
	private String searchType;;

	@Transient
	@JsonProperty
	private List<String> locationsList;

	@Transient
	@JsonProperty
	private String clientName;

	@Transient
	@JsonProperty
	private String orgRelationUUID;

	@Transient
	@JsonProperty
	private String spocUUID;

	@Transient
	@JsonProperty
	private String requirementApproverHierarchyStatus;

	@Transient
	private String spocName;
	
	@Transient
	private String spocOwnerName;

	@Transient
	private String spocEmail;

	@Transient
	private String spocMobilePhone;

	@Transient
	private String[] reqEducationList;

	@Transient
	private String reqEducationListString;

	@Transient
	@JsonProperty
	private String manager;
	
	@Transient
	@JsonProperty
	private String jobOwner;
	
	@Transient
	@JsonProperty
	private String primaryRecruiter;

	@Transient
	private String[] reqSkillsList;

	@Transient
	private String reqIndustryList;

	@Transient
	private String reqSkillsListString;

	@Transient
	private String industrySavedName;

	@Transient
	private ArrayList<String> approverNames;

	@Transient
	private Integer highestLevel;

	@Transient
	@JsonProperty
	private String noOfApplicants;

	@Transient
	@JsonProperty
	private String orgImageUrl;

	@Transient
	@JsonProperty
	private String orgRelationId;

	@Transient
	@JsonProperty
	private String noOfShortlistedApplicants;

	@Transient
	@JsonProperty
	private String noOfSubmittedApplicants;

	@Transient
	@JsonProperty
	private String noOfInterviewedApplicants;

	@Transient
	@JsonProperty
	private String noOfOfferedApplicants;
	
	@Transient
	@JsonProperty
	private String noOfJoinedApplicants;

	@Transient
	@JsonProperty
	private String noOfBackOutApplicants;

	@Transient
	@JsonProperty
	private String clientContactName;

	@Transient
	private String clientContactEmail;

	@Transient
	@JsonProperty
	private String isLoginUserAsTeamMember;

	@Transient
	@JsonProperty
	private String isLoginUserAsApprover;

	@Transient
	@JsonProperty
	private String isLoginUserAsManager;

	@Transient
	@JsonProperty
	private String isLoginUserAsInitiator;

	@Transient
	@JsonProperty
	private String mailBody;

	@Transient
	@JsonProperty
	private String submitToClientFromMailId;

	@Transient
	@JsonProperty
	private String submitToClientToMailId;

	@Transient
	@JsonProperty
	private String submitToClientMailSubject;

	@Transient
	@JsonProperty
	private Integer totalApplicantsForRequirement;

	@Transient
	@JsonProperty
	private String approvedBy;

	@Transient
	private String createdByName;

	@Transient
	@JsonProperty
	private String[] level1ApproverUserId;

	@Transient
	@JsonProperty
	private String[] level2ApproverUserId;

	@Transient
	private String level1ApproverStatus;

	@Transient
	private String level2ApproverStatus;

	@Transient
	private String[] requirementAssignedToUsersId;

	@Transient
	@JsonProperty
	private String isRequirementReleasedByuser;

	@Transient
	@JsonProperty
	private String isRequirementReleased;

	@Transient
	private String orgcode;

	@Transient
	private String isAllowStageAccess;

	@Transient
	private String reqlevel1ApproverStatus;

	@Transient
	private String reqlevel2ApproverStatus;

	@Transient
	private String rejectReasonForRequirement;

	@Transient
	private String approvalMechanismType;

	@Transient
	private String postedDateStr;

	@Transient
	private Long submissionId;

	@Transient
	private String requirementOrgName;

	@Column(name = "GENERATED_ID")
	private String generatedId;

	@Transient
	private String systemGeneratedId;

	@Transient
	private Integer vendorInterviewsCount;

	@Transient
	private Integer vendorBackoutCount;

	@Transient
	private Integer vendorOfferedCount;

	@Transient
	private Long referId;

	@Transient
	@JsonProperty
	private String isRequirementRelesedToPublic;

	@Transient
	@JsonProperty
	private List<Long> requirementReleasedUserIds;

	@Transient
	@JsonProperty
	private String postedOnStr;

	@Transient
	@JsonProperty
	private String DT_RowId;

	@Column(name = "FILE_URL")
	private String fileURL;

	@Transient
	@JsonProperty
	private String assignedMembersCount;

	@Transient
	@JsonProperty
	private String experienceLevelStr;
	
	@Transient
	@JsonProperty
	private List<JobFair> jobFairList;
	
	@Transient
    @JsonProperty
    private String jobsTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;

	@Transient
	@JsonProperty
	private String jobFairDates;
	
	@Transient
	@JsonProperty
	private String shortlistCount;
	 
	@Transient
	@JsonProperty
	private String  interviewCount;
	  
	@Transient
	@JsonProperty
	private String submitToClientCount;
	  
	@Transient
	@JsonProperty
	private String offersCount;
	 
	@Transient
	@JsonProperty
	private String joinedCount;
	
	@Transient
	@JsonProperty 
	private String backoutCount;
	
	@Transient
	@JsonProperty
	private String rejectedCount;
	
	@Transient
	@JsonProperty
	private String onHoldCount;
	
	@Transient
	@JsonProperty
	private String noResponseCount;
	
	@Transient
	@JsonProperty
	private String releasedDate;
	
	public String getJobFairDates() {
		return jobFairDates;
	}

	public void setJobFairDates(String jobFairDates) {
		this.jobFairDates = jobFairDates;
	}


	public List<JobFair> getJobFairList() {
		return jobFairList;
	}

	public void setJobFairList(List<JobFair> jobFairList) {
		this.jobFairList = jobFairList;
	}

	public String getRequirementOrgName() {
		return requirementOrgName;
	}

	public void setRequirementOrgName(String requirementOrgName) {
		this.requirementOrgName = requirementOrgName;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getRequirementCreatedDateToString() {
		return requirementCreatedDateToString;
	}

	public void setRequirementCreatedDateToString(String requirementCreatedDateToString) {
		this.requirementCreatedDateToString = requirementCreatedDateToString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public String getRejectReasonForRequirement() {
		return rejectReasonForRequirement;
	}

	public void setRejectReasonForRequirement(String rejectReasonForRequirement) {
		this.rejectReasonForRequirement = rejectReasonForRequirement;
	}

	public String getClientSubmissionTemplate() {
		if (submissionTemplateId != null) {
			clientSubmissionTemplate = submissionTemplateId.getTemplateName();
		}
		return clientSubmissionTemplate;
	}

	public void setClientSubmissionTemplate(String clientSubmissionTemplate) {
		this.clientSubmissionTemplate = clientSubmissionTemplate;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getClientCurrencyCode() {
		return clientCurrencyCode;
	}

	public void setClientCurrencyCode(String clientCurrencyCode) {
		this.clientCurrencyCode = clientCurrencyCode;
	}

	public String getIsJobApplied() {
		return isJobApplied;
	}

	public void setIsJobApplied(String isJobApplied) {
		this.isJobApplied = isJobApplied;
	}

	public String getClientName() {
		if (clientId != null) {
			if (clientId.getResponderType().equalsIgnoreCase("Client")) {
				clientName = clientId.getResponderLocationId().getOrg().getOrgName();
			}
			if (clientId.getResponderType().equalsIgnoreCase("Vendor")) {
				clientName = clientId.getLocationId().getOrg().getOrgName();
			}
		}

		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
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

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skiills) {
		this.skills = skiills;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIsClientJob() {
		return isClientJob;
	}

	public void setIsClientJob(String isClientJob) {
		this.isClientJob = isClientJob;
	}

	public OrgRelations getClientId() {
		return clientId;
	}

	public void setClientId(OrgRelations clientId) {
		this.clientId = clientId;
	}

	public OrgSpoc getSpocId() {
		return spocId;
	}

	public void setSpocId(OrgSpoc spocId) {
		this.spocId = spocId;
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

	public Set<RequirementIncentive> getRequirementIncentive() {
		return requirementIncentive;
	}

	public void setRequirementIncentive(Set<RequirementIncentive> requirementIncentive) {
		this.requirementIncentive = requirementIncentive;
	}

	public SubmissionTemplate getSubmissionTemplateId() {
		return submissionTemplateId;
	}

	public void setSubmissionTemplateId(SubmissionTemplate submissionTemplateId) {
		this.submissionTemplateId = submissionTemplateId;
	}

	public SubmissionTemplate getClientSubmissionTemplateId() {
		return clientSubmissionTemplateId;
	}

	public void setClientSubmissionTemplateId(SubmissionTemplate clientSubmissionTemplateId) {
		this.clientSubmissionTemplateId = clientSubmissionTemplateId;
	}

	public User getManagerUserId() {
		return managerUserId;
	}

	public void setManagerUserId(User managerUserId) {
		this.managerUserId = managerUserId;
	}

	public String getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public REQUIREMENT_SUBMISSION_STATUS getRequirementSubmissionStatus() {
		return requirementSubmissionStatus;
	}

	public void setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS requirementSubmissionStatus) {
		this.requirementSubmissionStatus = requirementSubmissionStatus;
	}

	public Requirement getParentRequirement() {
		return parentRequirement;
	}

	public void setParentRequirement(Requirement parentRequirement) {
		this.parentRequirement = parentRequirement;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
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

	public Set<RequirementAssignTo> getRequirementAssignTo() {
		return requirementAssignTo;
	}

	public void setRequirementAssignTo(Set<RequirementAssignTo> requirementAssignTo) {
		this.requirementAssignTo = requirementAssignTo;
	}

	public Set<RequirementApproverHierarchy> getRequirementApprover() {
		return requirementApprover;
	}

	public void setRequirementApprover(Set<RequirementApproverHierarchy> requirementApprover) {
		this.requirementApprover = requirementApprover;
	}

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public String getSpocOwnerName() {
		return spocOwnerName;
	}

	public void setSpocOwnerName(String spocOwnerName) {
		this.spocOwnerName = spocOwnerName;
	}

	public String getSpocEmail() {
		return spocEmail;
	}

	public void setSpocEmail(String spocEmail) {
		this.spocEmail = spocEmail;
	}

	public String getSpocMobilePhone() {
		return spocMobilePhone;
	}

	public void setSpocMobilePhone(String spocMobilePhone) {
		this.spocMobilePhone = spocMobilePhone;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getManager() {
		if (managerUserId != null) {
			String managerFirstName = "";
			if (StringUtils.isNotEmpty(managerUserId.getFirstName())) {
				managerFirstName = managerUserId.getFirstName();
			}
			String managerLastName = "";
			if (StringUtils.isNotEmpty(managerUserId.getLastName())) {
				managerLastName = managerUserId.getLastName();
			}
			StringBuilder name = new StringBuilder();
			manager = name.append(managerFirstName).append(" ").append(managerLastName).toString();
		}
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String[] getReqEducationList() {
		return reqEducationList;
	}

	public void setReqEducationList(String[] reqEducationList) {
		this.reqEducationList = reqEducationList;
	}

	public String getReqEducationListString() {
		return reqEducationListString;
	}

	public void setReqEducationListString(String reqEducationListString) {
		this.reqEducationListString = reqEducationListString;
	}

	public String[] getReqSkillsList() {
		return reqSkillsList;
	}

	public void setReqSkillsList(String[] reqSkillsList) {
		this.reqSkillsList = reqSkillsList;
	}

	public String getReqSkillsListString() {
		return reqSkillsListString;
	}

	public void setReqSkillsListString(String reqSkillsListString) {
		this.reqSkillsListString = reqSkillsListString;
	}

	public String getSubmissionTemplateJsonString() {
		return submissionTemplateJsonString;
	}

	public void setSubmissionTemplateJsonString(String submissionTemplateJsonString) {
		this.submissionTemplateJsonString = submissionTemplateJsonString;
	}

	public String getClientSubmissionTemplateJsonString() {
		return clientSubmissionTemplateJsonString;
	}

	public void setClientSubmissionTemplateJsonString(String clientSubmissionTemplateJsonString) {
		this.clientSubmissionTemplateJsonString = clientSubmissionTemplateJsonString;
	}

	public String getRequirementApproverHierarchyStatus() {
		return requirementApproverHierarchyStatus;
	}

	public void setRequirementApproverHierarchyStatus(String requirementApproverHierarchyStatus) {
		this.requirementApproverHierarchyStatus = requirementApproverHierarchyStatus;
	}

	public ArrayList<String> getApproverNames() {
		return approverNames;
	}

	public void setApproverNames(ArrayList<String> approverNames) {
		this.approverNames = approverNames;
	}

	public Integer getHighestLevel() {
		return highestLevel;
	}

	public void setHighestLevel(Integer highestLevel) {
		this.highestLevel = highestLevel;
	}

	public String getNoOfApplicants() {
		return noOfApplicants;
	}

	public void setNoOfApplicants(String noOfApplicants) {
		this.noOfApplicants = noOfApplicants;
	}

	public String getNoOfShortlistedApplicants() {
		return noOfShortlistedApplicants;
	}

	public void setNoOfShortlistedApplicants(String noOfShortlistedApplicants) {
		this.noOfShortlistedApplicants = noOfShortlistedApplicants;
	}

	public String getNoOfSubmittedApplicants() {
		return noOfSubmittedApplicants;
	}

	public void setNoOfSubmittedApplicants(String noOfSubmittedApplicants) {
		this.noOfSubmittedApplicants = noOfSubmittedApplicants;
	}

	public String getNoOfInterviewedApplicants() {
		return noOfInterviewedApplicants;
	}

	public void setNoOfInterviewedApplicants(String noOfInterviewedApplicants) {
		this.noOfInterviewedApplicants = noOfInterviewedApplicants;
	}

	public String getNoOfOfferedApplicants() {
		return noOfOfferedApplicants;
	}

	public void setNoOfOfferedApplicants(String noOfOfferedApplicants) {
		this.noOfOfferedApplicants = noOfOfferedApplicants;
	}

	public String getNoOfJoinedApplicants() {
		return noOfJoinedApplicants;
	}

	public void setNoOfJoinedApplicants(String noOfJoinedApplicants) {
		this.noOfJoinedApplicants = noOfJoinedApplicants;
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

	public String getIsLoginUserAsTeamMember() {
		return isLoginUserAsTeamMember;
	}

	public void setIsLoginUserAsTeamMember(String isLoginUserAsTeamMember) {
		this.isLoginUserAsTeamMember = isLoginUserAsTeamMember;
	}

	public String getIsLoginUserAsApprover() {
		return isLoginUserAsApprover;
	}

	public void setIsLoginUserAsApprover(String isLoginUserAsApprover) {
		this.isLoginUserAsApprover = isLoginUserAsApprover;
	}

	public String getIsLoginUserAsManager() {
		return isLoginUserAsManager;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setIsLoginUserAsManager(String isLoginUserAsManager) {
		this.isLoginUserAsManager = isLoginUserAsManager;
	}

	public String getIsLoginUserAsInitiator() {
		return isLoginUserAsInitiator;
	}

	public void setIsLoginUserAsInitiator(String isLoginUserAsInitiator) {
		this.isLoginUserAsInitiator = isLoginUserAsInitiator;
	}

	public REQUIREMENT_SUBMISSION_STATUS getRequirementSubmissionPreviousStatus() {
		return requirementSubmissionPreviousStatus;
	}

	public void setRequirementSubmissionPreviousStatus(
			REQUIREMENT_SUBMISSION_STATUS requirementSubmissionPreviousStatus) {
		this.requirementSubmissionPreviousStatus = requirementSubmissionPreviousStatus;
	}

	public String getOtherJobType() {
		return otherJobType;
	}

	public void setOtherJobType(String otherJobType) {
		this.otherJobType = otherJobType;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getSubmitToClientFromMailId() {
		return submitToClientFromMailId;
	}

	public void setSubmitToClientFromMailId(String submitToClientFromMailId) {
		this.submitToClientFromMailId = submitToClientFromMailId;
	}

	public String getSubmitToClientToMailId() {
		return submitToClientToMailId;
	}

	public void setSubmitToClientToMailId(String submitToClientToMailId) {
		this.submitToClientToMailId = submitToClientToMailId;
	}

	public String getSubmitToClientMailSubject() {
		return submitToClientMailSubject;
	}

	public void setSubmitToClientMailSubject(String submitToClientMailSubject) {
		this.submitToClientMailSubject = submitToClientMailSubject;
	}

	public Integer getTotalApplicantsForRequirement() {
		return totalApplicantsForRequirement;
	}

	public void setTotalApplicantsForRequirement(Integer totalApplicantsForRequirement) {
		this.totalApplicantsForRequirement = totalApplicantsForRequirement;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCreatedByName() {
		if (StringUtils.isNotEmpty(createdBy.getLastName())) {
			createdByName = createdBy.getFirstName() + " " + createdBy.getLastName();
		} else {
			createdByName = createdBy.getFirstName();
		}
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String[] getLevel1ApproverUserId() {
		return level1ApproverUserId;
	}

	public void setLevel1ApproverUserId(String[] level1ApproverUserId) {
		this.level1ApproverUserId = level1ApproverUserId;
	}

	public String[] getLevel2ApproverUserId() {
		return level2ApproverUserId;
	}

	public void setLevel2ApproverUserId(String[] level2ApproverUserId) {
		this.level2ApproverUserId = level2ApproverUserId;
	}

	public String getLevel1ApproverStatus() {
		return level1ApproverStatus;
	}

	public void setLevel1ApproverStatus(String level1ApproverStatus) {
		this.level1ApproverStatus = level1ApproverStatus;
	}

	public String getLevel2ApproverStatus() {
		return level2ApproverStatus;
	}

	public void setLevel2ApproverStatus(String level2ApproverStatus) {
		this.level2ApproverStatus = level2ApproverStatus;
	}

	public String[] getRequirementAssignedToUsersId() {
		return requirementAssignedToUsersId;
	}

	public void setRequirementAssignedToUsersId(String[] requirementAssignedToUsersId) {
		this.requirementAssignedToUsersId = requirementAssignedToUsersId;
	}

	public String getReleasedBy() {
		return releasedBy;
	}

	public void setReleasedBy(String releasedBy) {
		this.releasedBy = releasedBy;
	}

	public String getIsRequirementReleasedByuser() {
		return isRequirementReleasedByuser;
	}

	public void setIsRequirementReleasedByuser(String isRequirementReleasedByuser) {
		this.isRequirementReleasedByuser = isRequirementReleasedByuser;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getIsCrowdSourcing() {
		return isCrowdSourcing;
	}

	public void setIsCrowdSourcing(String isCrowdSourcing) {
		this.isCrowdSourcing = isCrowdSourcing;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public String getPostedOrgName() {
		return postedOrgName;
	}

	public void setPostedOrgName(String postedOrgName) {
		this.postedOrgName = postedOrgName;
	}

	public String getAppliedProfessionals() {
		return appliedProfessionals;
	}

	public void setAppliedProfessionals(String appliedProfessionals) {
		this.appliedProfessionals = appliedProfessionals;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getIsAllowStageAccess() {
		return isAllowStageAccess;
	}

	public void setIsAllowStageAccess(String isAllowStageAccess) {
		this.isAllowStageAccess = isAllowStageAccess;
	}

	public String getReqlevel1ApproverStatus() {
		return reqlevel1ApproverStatus;
	}

	public void setReqlevel1ApproverStatus(String reqlevel1ApproverStatus) {
		this.reqlevel1ApproverStatus = reqlevel1ApproverStatus;
	}

	public String getReqlevel2ApproverStatus() {
		return reqlevel2ApproverStatus;
	}

	public void setReqlevel2ApproverStatus(String reqlevel2ApproverStatus) {
		this.reqlevel2ApproverStatus = reqlevel2ApproverStatus;
	}

	public String getApprovalMechanismType() {

		if (getRequirementApprover() != null && !getRequirementApprover().isEmpty()) {
			RequirementApproverHierarchy rah = getRequirementApprover().iterator().next();
			approvalMechanismType = rah.getHierarchyType().toString();
		}
		return approvalMechanismType;
	}

	public void setApprovalMechanismType(String approvalMechanismType) {
		this.approvalMechanismType = approvalMechanismType;
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

	public String getPostedDateStr() {
		return postedDateStr;
	}

	public void setPostedDateStr(String postedDateStr) {
		this.postedDateStr = postedDateStr;
	}

	public String getAppliedRecruiters() {
		return appliedRecruiters;
	}

	public void setAppliedRecruiters(String appliedRecruiters) {
		this.appliedRecruiters = appliedRecruiters;
	}

	public Integer getTotalApplicants() {
		return totalApplicants;
	}

	public void setTotalApplicants(Integer totalApplicants) {
		this.totalApplicants = totalApplicants;
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

	public String getReleaseJobToAllUsers() {
		return releaseJobToAllUsers;
	}

	public void setReleaseJobToAllUsers(String releaseJobToAllUsers) {
		this.releaseJobToAllUsers = releaseJobToAllUsers;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getIndustrySavedName() {
		return industrySavedName;
	}

	public void setIndustrySavedName(String industrySavedName) {
		this.industrySavedName = industrySavedName;
	}

	public String getReqIndustryList() {
		return reqIndustryList;
	}

	public void setReqIndustryList(String reqIndustryList) {
		this.reqIndustryList = reqIndustryList;
	}

	public String getTravelAllowance() {
		return travelAllowance;
	}

	public void setTravelAllowance(String travelAllowance) {
		this.travelAllowance = travelAllowance;
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

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getClosedDateStringFormat() {
		return closedDateStringFormat;
	}

	public void setClosedDateStringFormat(String closedDateStringFormat) {
		this.closedDateStringFormat = closedDateStringFormat;
	}

	public String getIsreceivingApplicants() {
		return isreceivingApplicants;
	}

	public void setIsreceivingApplicants(String isreceivingApplicants) {
		this.isreceivingApplicants = isreceivingApplicants;
	}

	public String getNoOfBackOutApplicants() {
		return noOfBackOutApplicants;
	}

	public void setNoOfBackOutApplicants(String noOfBackOutApplicants) {
		this.noOfBackOutApplicants = noOfBackOutApplicants;
	}

	public Integer getVendorBackoutCount() {
		return vendorBackoutCount;
	}

	public Integer getVendorInterviewsCount() {
		return vendorInterviewsCount;
	}

	public void setVendorInterviewsCount(Integer vendorInterviewsCount) {
		this.vendorInterviewsCount = vendorInterviewsCount;
	}

	public void setVendorBackoutCount(Integer vendorBackoutCount) {
		this.vendorBackoutCount = vendorBackoutCount;
	}

	public Integer getVendorOfferedCount() {
		return vendorOfferedCount;
	}

	public void setVendorOfferedCount(Integer vendorOfferedCount) {
		this.vendorOfferedCount = vendorOfferedCount;
	}

	public Long getReferId() {
		return referId;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public String getApproverHierarchy() {
		return approverHierarchy;
	}

	public void setApproverHierarchy(String approverHierarchy) {
		this.approverHierarchy = approverHierarchy;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getSystemGeneratedId() {
		return systemGeneratedId;
	}

	public void setSystemGeneratedId(String systemGeneratedId) {
		this.systemGeneratedId = systemGeneratedId;
	}

	public List<Long> getRequirementReleasedUserIds() {
		if (StringUtils.isNotEmpty(releasedBy) && requirementReleasedUserIds != null) {
			for (String s : releasedBy.split(",")) {
				requirementReleasedUserIds.add(Long.parseLong(s));
			}
		}
		return requirementReleasedUserIds;
	}

	public void setRequirementReleasedUserIds(List<Long> requirementReleasedUserIds) {
		this.requirementReleasedUserIds = requirementReleasedUserIds;
	}

	public String getPostedOnStr() {
		if (createdDate != null && orgLocationId != null) {
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			if (dateFormat != null)
				postedOnStr = dateFormat.format(createdDate);
		}
		return postedOnStr;
	}

	public void setPostedOnStr(String postedOnStr) {
		this.postedOnStr = postedOnStr;
	}

	public String getIsRequirementRelesedToPublic() {

		if (isPublic == null && isCrowdSourcing == null) {
			isRequirementRelesedToPublic = Constants.NO;
		} else {
			isRequirementRelesedToPublic = Constants.YES;
		}
		return isRequirementRelesedToPublic;
	}

	public void setIsRequirementRelesedToPublic(String isRequirementRelesedToPublic) {
		this.isRequirementRelesedToPublic = isRequirementRelesedToPublic;
	}

	public String getIsRequirementReleased() {
		return isRequirementReleased;
	}

	public void setIsRequirementReleased(String isRequirementReleased) {
		this.isRequirementReleased = isRequirementReleased;
	}

	public String getDT_RowId() {
		return DT_RowId;
	}

	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMedicalClearence() {
		return medicalClearence;
	}

	public void setMedicalClearence(String medicalClearence) {
		this.medicalClearence = medicalClearence;
	}

	public String getSecurityClearance() {
		return securityClearance;
	}

	public void setSecurityClearance(String securityClearance) {
		this.securityClearance = securityClearance;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(String backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getClientMaxRate() {
		return clientMaxRate;
	}

	public void setClientMaxRate(String clientMaxRate) {
		this.clientMaxRate = clientMaxRate;
	}

	public ProjectClients getProjectClientID() {
		return projectClientID;
	}

	public void setProjectClientID(ProjectClients projectClientID) {
		this.projectClientID = projectClientID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOrgRelationUUID() {
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID) {
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getSpocUUID() {
		return spocUUID;
	}

	public void setSpocUUID(String spocUUID) {
		this.spocUUID = spocUUID;
	}

	public LookUpData getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(LookUpData experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public Set<RequirementLocations> getRequirementLocations() {
		return requirementLocations;
	}

	public void setRequirementLocations(Set<RequirementLocations> requirementLocations) {
		this.requirementLocations = requirementLocations;
	}

	public String getAssignedMembersCount() {
		return assignedMembersCount;
	}

	public void setAssignedMembersCount(String assignedMembersCount) {
		this.assignedMembersCount = assignedMembersCount;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

	public String getExperienceLevelStr() {
		if (StringUtils.isNotEmpty(experienceLevelStr))
			return experienceLevelStr;
		else {
			if (experienceLevel != null) {
				experienceLevelStr = experienceLevel.getName();
			}
			return experienceLevelStr;
		}
	}

	public void setExperienceLevelStr(String experienceLevelStr) {
		this.experienceLevelStr = experienceLevelStr;
	}

	public String getOrgImageUrl() {
		return orgImageUrl;
	}

	public void setOrgImageUrl(String orgImageUrl) {
		this.orgImageUrl = orgImageUrl;
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

	public User getPrimaryRecruiterUserId() {
		return primaryRecruiterUserId;
	}

	public void setPrimaryRecruiterUserId(User primaryRecruiterUserId) {
		this.primaryRecruiterUserId = primaryRecruiterUserId;
	}

	public String getPrimaryRecruiter() {
		return primaryRecruiter;
	}

	public void setPrimaryRecruiter(String primaryRecruiter) {
		this.primaryRecruiter = primaryRecruiter;
	}

    /**
     * @return the jobBoardAccountIdList
     */
    public List<Long> getJobBoardAccountIdList() {
        return jobBoardAccountIdList;
    }

    /**
     * @param jobBoardAccountIdList the jobBoardAccountIdList to set
     */
    public void setJobBoardAccountIdList(List<Long> jobBoardAccountIdList) {
        this.jobBoardAccountIdList = jobBoardAccountIdList;
    }

    /**
     * @return the requirementJobBoardInfoList
     */
    public List<RequirementJobBoardInfo> getRequirementJobBoardInfoList() {
        return requirementJobBoardInfoList;
    }

    /**
     * @param requirementJobBoardInfoList the requirementJobBoardInfoList to set
     */
    public void setRequirementJobBoardInfoList(List<RequirementJobBoardInfo> requirementJobBoardInfoList) {
        this.requirementJobBoardInfoList = requirementJobBoardInfoList;
    }

	public String getJobsTags() {
		return jobsTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setJobsTags(String jobsTags) {
		this.jobsTags = jobsTags;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
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


	public String getShortlistCount() {
		return shortlistCount;
	}

	public void setShortlistCount(String shortlistCount) {
		this.shortlistCount = shortlistCount;
	}

	public String getInterviewCount() {
		return interviewCount;
	}

	public void setInterviewCount(String interviewCount) {
		this.interviewCount = interviewCount;
	}

	public String getSubmitToClientCount() {
		return submitToClientCount;
	}

	public void setSubmitToClientCount(String submitToClientCount) {
		this.submitToClientCount = submitToClientCount;
	}

	public String getOffersCount() {
		return offersCount;
	}

	public void setOffersCount(String offersCount) {
		this.offersCount = offersCount;
	}

	public String getJoinedCount() {
		return joinedCount;
	}

	public void setJoinedCount(String joinedCount) {
		this.joinedCount = joinedCount;
	}

	public String getBackoutCount() {
		return backoutCount;
	}

	public void setBackoutCount(String backoutCount) {
		this.backoutCount = backoutCount;
	}

	public String getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(String rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public Date getRepoenedDate() {
		return repoenedDate;
	}

	public void setRepoenedDate(Date repoenedDate) {
		this.repoenedDate = repoenedDate;

	}

	public String getOnHoldCount()
	{
		return onHoldCount;
	}

	public void setOnHoldCount(String onHoldCount)
	{
		this.onHoldCount = onHoldCount;
	}

	public String getNoResponseCount()
	{
		return noResponseCount;
	}

	public void setNoResponseCount(String noResponseCount)
	{
		this.noResponseCount = noResponseCount;
	}

	public String getJobOwner() {
		return jobOwner;
	}

	public void setJobOwner(String jobOwner) {
		this.jobOwner = jobOwner;
	}

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	public RequirementWorkFlow getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(RequirementWorkFlow workflowId) {
		this.workflowId = workflowId;
	}
	
}
