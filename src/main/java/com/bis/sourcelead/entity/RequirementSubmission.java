package com.bis.sourcelead.entity;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.enums.ApplicantSubmissionStatus;

@Entity
@Table(name = "REQUIREMENT_SUBMISSION")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="requirementSubmission")
public class RequirementSubmission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REQUIREMENT_ID")
   	private Requirement requirement;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CANDIDATE_ID")
   	private Candidate candidate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOURCE_SUBMISSION_ID")
   	private RequirementSubmission sourceSubmission;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CANDIDATE_RESUEME_ID")
   	private CandidateResumes candidateResumes;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_LOCATION_ID")
   	private OrgLocations orgLocations;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VENDOR_LOCATION_ID")
   	private OrgLocations vendorLocationId;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonString;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING_VALUES", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonStringValue;
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private ApplicantSubmissionStatus submissionStatus;
	
	@Column(name = "STATUS")
    private Integer status;
	
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Transient
	private String userName;
    
    @Transient
	private String candidateName;
	
    @Transient
	private String candidateEmail;
	
    @Transient
	private String phoneNumber;
    
    @Transient
	private String expectedCTC;
    
    @Transient
	private String workExperience;
    
    @Transient
   	private String createdDateStr;
    
    @Transient
	private String skillsList;
    
    @Transient
   	private String candidateId;
    
    @Transient
   	private String resumeName;
    
    @Transient
   	private String requirementName;
    
    @Transient
   	private ApplicantSubmissionStatus requirementStage;
    
	@Transient
   	private String score;

	public OrgLocations getVendorLocationId() {
		return vendorLocationId;
	}

	public void setVendorLocationId(OrgLocations vendorLocationId) {
		this.vendorLocationId = vendorLocationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExpectedCTC() {
		return expectedCTC;
	}

	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}


	public RequirementSubmission getSourceSubmission() {
		return sourceSubmission;
	}

	public void setSourceSubmission(RequirementSubmission sourceSubmission) {
		this.sourceSubmission = sourceSubmission;
	}

	public CandidateResumes getCandidateResumes() {
		return candidateResumes;
	}

	public void setCandidateResumes(CandidateResumes candidateResumes) {
		this.candidateResumes = candidateResumes;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public String getSubmissionTemplateJsonString() {
		return submissionTemplateJsonString;
	}

	public void setSubmissionTemplateJsonString(String submissionTemplateJsonString) {
		this.submissionTemplateJsonString = submissionTemplateJsonString;
	}

	public String getSubmissionTemplateJsonStringValue() {
		return submissionTemplateJsonStringValue;
	}

	public void setSubmissionTemplateJsonStringValue(String submissionTemplateJsonStringValue) {
		this.submissionTemplateJsonStringValue = submissionTemplateJsonStringValue;
	}

	public ApplicantSubmissionStatus getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(ApplicantSubmissionStatus submissionStatus) {
		this.submissionStatus = submissionStatus;
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

	public String getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(String skillsList) {
		this.skillsList = skillsList;
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


	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public ApplicantSubmissionStatus getRequirementStage() {
		return requirementStage;
	}

	public void setRequirementStage(ApplicantSubmissionStatus requirementStage) {
		this.requirementStage = requirementStage;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	
}
