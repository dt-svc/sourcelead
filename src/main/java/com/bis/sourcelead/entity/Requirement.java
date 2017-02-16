package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;

	@Entity
	@Table(name = "REQUIREMENT", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirement1")
	public class Requirement implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
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
	
	@Column(name = "RATE_TYPE") 
	private String rateType;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "SKILLS", columnDefinition = "TEXT default NULL")
	private String skills;
	
	@Column(name = "DESCRIPTION" , columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "IS_CLIENT_JOB")
	private String isClientJob;
	
	@Column(name = "IS_PUBLIC")
	private String isPublic;
	
	@Column(name = "IS_CROWD_SOURCING")
	private String isCrowdSourcing;
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )        
    private OrgRelations clientId;
	    
	@ManyToOne
    @JoinColumn(name="SPOC_ID", nullable=false )        
    private OrgSpoc spocId;
	
	@Column(name = "CLIENT_CURRENCY_CODE")
	private String clientCurrencyCode;
	
	@Column(name = "CLIENT_RATE")
	private String clientRate;
	
	@Column(name = "CLIENT_RATE_TYPE")
	private String clientRateType;
	
	@Column(name = "POSTED_ORG_NAME")
	private String postedOrgName;
	
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_TEMPLATE_ID" )        
    private SubmissionTemplate submissionTemplateId;
	
	@ManyToOne
    @JoinColumn(name="CLIENT_SUBMISSION_TEMPLATE_ID" )        
    private SubmissionTemplate clientSubmissionTemplateId;
	
	@ManyToOne
    @JoinColumn(name="MANAGER_USER_ID", nullable=false )        
    private User managerUserId;
	
	@Column(name="IS_Template")
	private String isTemplate;
	
	@Column(name="STATUS")
	private String status;
	

	@Column(name = "REQUIREMENT_SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private REQUIREMENT_SUBMISSION_STATUS requirementSubmissionStatus;
	
	@Column(name = "REQUIREMENT_SUBMISSION_PREVIOUS_STATUS")
	@Enumerated(EnumType.STRING)
	private REQUIREMENT_SUBMISSION_STATUS requirementSubmissionPreviousStatus;

	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String submissionTemplateJsonString;
	
	
	@Column(name = "CLIENT_SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String clientSubmissionTemplateJsonString;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_REQUIREMENT_ID")        
    private Requirement parentRequirement;
	
	@Column(name="RELEASED_BY")
	@Type(type="text")
	private String releasedBy;
	
	@Column(name="APPLIED_PROFESSIONALS")
	@Type(type="text")
	private String appliedProfessionals;
	
	@Column(name="PRIORITY")
	private String priority;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String clientSubmissionTemplate;
	
	@Transient
	private String requirementCreatedDateToString;

	@Transient
	private  Long  requirementId;
	
	@Transient
	private String msg;
	
	@OneToMany(mappedBy = "requirementId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementAssignTo> requirementAssignTo = new HashSet<RequirementAssignTo>(0);
	
	@OneToMany(mappedBy = "requirementId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementApproverHierarchy> requirementApprover = new HashSet<RequirementApproverHierarchy>(0);
	
	@Transient
	private String searchType;;
	
	@Transient
	private String clientName;
	
	@Transient
	private String requirementApproverHierarchyStatus;
	
	@Transient
	private String spocName;
	
	@Transient
	private String spocEmail;
	
	@Transient
	private String spocMobilePhone;
	
	@Transient
	private String[] reqEducationList;
	
	@Transient
	private String reqEducationListString;
	
	@Transient
	private String manager;
	
	@Transient
	private String[] reqSkillsList;
	
	@Transient
	private String reqSkillsListString;
	
	@Transient
	private ArrayList<String> approverNames;
	
	@Transient
	private Integer highestLevel;
	
	@Transient
	private String noOfApplicants;
	
	@Transient
	private String noOfShortlistedApplicants;
	
	@Transient
	private String noOfSubmittedApplicants;
	
	@Transient
	private String noOfInterviewedApplicants;
	
	@Transient
	private String noOfOfferedApplicants;
	
	@Transient
	private String clientContactName;
	
	@Transient
	private String clientContactEmail;
	
	@Transient
	private String isLoginUserAsTeamMember;
	
	@Transient
	private String isLoginUserAsApprover;
	
	@Transient
	private String isLoginUserAsManager;
	
	@Transient
	private String mailBody;
	
	@Transient
	private String submitToClientFromMailId;
	
	@Transient
	private String submitToClientToMailId;
	
	@Transient
	private String submitToClientMailSubject;
	
	@Transient
	private Integer totalApplicantsForRequirement;
	
	@Transient
	private String approvedBy;
	
	@Transient
	private String createdByName;
	
	@Transient
    private String[] level1ApproverUserId;
	
	@Transient
	private String[] level2ApproverUserId;
	
	@Transient
	private String level1ApproverStatus;
	
	@Transient
	private String level2ApproverStatus;
	
	@Transient
	private String[] requirementAssignedToUsersId;
	
	@Transient
	private String isRequirementReleasedByuser;
	
	

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

	public String getClientSubmissionTemplate() {
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



	public String getClientName() {
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

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
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

	public REQUIREMENT_SUBMISSION_STATUS getRequirementSubmissionPreviousStatus() {
		return requirementSubmissionPreviousStatus;
	}

	public void setRequirementSubmissionPreviousStatus(REQUIREMENT_SUBMISSION_STATUS requirementSubmissionPreviousStatus) {
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
	
}
