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
	@Table(name = "REQUIREMENT", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirement")
	public class Requirement {

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
	
	@Column(name = "JOB_ID")
	private String jobId;
	
	@Column(name = "POSITIONS")
	private String positions;
	
	@Column(name = "EDUCATION")
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
	
	@Column(name = "SKILLS")
	private String skills;
	
	@Column(name = "DESCRIPTION")
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
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )        
    private OrgRelations clientId;
	    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SPOC_ID", nullable=false )        
    private OrgSpoc spocId;
	
	@Column(name = "CLIENT_CURRENCY_CODE")
	private String clientCurrencyCode;
	
	@Column(name = "CLIENT_RATE")
	private String clientRate;
	
	@Column(name = "CLIENT_RATE_TYPE")
	private String clientRateType;
	
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_TEMPLATE_ID", nullable=false )        
    private SubmissionTemplate submissionTemplateId;
	
	
	@ManyToOne
    @JoinColumn(name="CLIENT_SUBMISSION_TEMPLATE_ID", nullable=false )        
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

	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String submissionTemplateJsonString;
	
	
	@Column(name = "CLIENT_SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
	private String clientSubmissionTemplateJsonString;
	
	
	@Column(name="PARENT_REQUIREMENT_ID")
	private Long parent_requirement_id;
	
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
	
	@OneToMany(mappedBy = "requirementId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementAssignTo> requirementAssignTo = new HashSet<RequirementAssignTo>(0);
	
	@OneToMany(mappedBy = "requirementId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementApproverHierarchy> requirementApprover = new HashSet<RequirementApproverHierarchy>(0);
	
	
	@Transient
	private String clientName;
	
	@Transient
	private String spocName;
	
	@Transient
	private String spocEmail;
	
	@Transient
	private String spocMobilePhone;
	
	@Transient
	private String[] reqEducationList;
	
	@Transient
	private String manager;
	
	@Transient
	private String[] reqSkillsList;

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

	public Long getParent_requirement_id() {
		return parent_requirement_id;
	}

	public void setParent_requirement_id(Long parent_requirement_id) {
		this.parent_requirement_id = parent_requirement_id;
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

	public String[] getReqSkillsList() {
		return reqSkillsList;
	}

	public void setReqSkillsList(String[] reqSkillsList) {
		this.reqSkillsList = reqSkillsList;
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

		
}
