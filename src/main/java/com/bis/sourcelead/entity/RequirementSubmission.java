package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;

@Entity
@Table(name = "REQUIREMENT_SUBMISSION")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="requirementSubmission")
public class RequirementSubmission implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@ManyToOne
	@JoinColumn(name = "REQUIREMENT_ID")
   	private Requirement requirement;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
   	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "SOURCE_SUBMISSION_ID")
   	private RequirementSubmission sourceSubmission;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_RESUEME_ID")
   	private CandidateResumes candidateResumes;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
   	private OrgLocations orgLocations;
	
	@ManyToOne
	@JoinColumn(name = "VENDOR_LOCATION_ID")
   	private OrgLocations vendorLocationId;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonString;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING_VALUES", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonStringValue;
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private ApplicantSubmissionStatus submissionStatus;
	
	@Column(name = "NEED_APPLICANT_PERMIT")
    private String needApplicantPermit;
	
	@Column(name = "SUBMISSION_RATE_STATUS")
	@Enumerated(EnumType.STRING)
	private SubmissionRateStatus submissionRateStatus;
	
	@Column(name = "SUBMISSION_RATE_COMMENT", columnDefinition = "TEXT default NULL")
	private String submissionRateComment;
	
	@Column(name = " SUBMISSION_RATE_DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] submissionRateDocFile;
	
	@Column(name = "SUBMISSION_RATE_FILE_CONTENT_TYPE")
	private String submissionRateFileContentType;
	
	@Column(name = "SUBMISSION_RATE_FILE_NAME", columnDefinition = "TEXT default NULL")
	private String submissionRateFileName;

	@Column(name = "SUBMISSION_REQUEST_COMMENT")
	private String submissionRequestComment;
	
	@Column(name = "SUBMISSION_REQUEST_USER")
	private String submissionRequestUser;
	
	@Column(name = " SUBMISSION_REQUEST_DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] submissionRequestDocFile;
	
	@Column(name = "SUBMISSION_REQUEST_FILE_CONTENT_TYPE")
	private String submissionRequestFileContentType;
	
	@Column(name = "SUBMISSION_REQUEST_FILE_NAME", columnDefinition = "TEXT default NULL")
	private String submissionRequestFileName;
	
	@Column(name = "PREVIOUS_SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
    private ApplicantSubmissionStatus previousSubmissionStatus;
	
	@Column(name = "REJECTED_REASON", columnDefinition = "TEXT default NULL")
    private String rejectedReason;
	
	@Column(name = "FINALIZED_RATE")
	private String finalizedRate;
	
	@Column(name = "FINALIZED_RATE_TYPE")
	private String finalizedRateType;
		
	@Column(name = "STATUS")
    private Integer status;
	
	@Column(name = "SCORE")
    private String candidateScore;
	
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Column(name = "CREATED_USER")
	@Type(type="text")
	private String createdUser;
	
    
    @Column(name = "UPDATED_USER")
	@Type(type="text")
	private String updatedUser;
	
    
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
   	private String upDatedStr;
    
    
    @Transient
	private String skillsList;
    
    @Transient
   	private String candidateId;
    
    @Transient
   	private String resumeName;
    
    @Transient
   	private String requirementName;
    
    @Transient
   	private Long resumeId;
    
    @Transient
   	private ApplicantSubmissionStatus requirementStage;
    
	@Transient
   	private String score;
	
	@Transient
	private int numOfSubmissions;
	
	@Transient
	private int numOfShortList;
	
	@Transient
	private int numOfInterview;
	
	@Transient
	private int numOfSubmitToClient;
	
	@Transient
	private int numOfOffers;
	
	@Transient
	private int numOfRejected;
	
	@Transient
	private int totalNoOfInterviews;
	
	@Transient
	private int numofrequestsent;
	
	@Transient
	List<RequirementInterview> requirementInterviewList;
	
	@Transient
	List<RequirementInterviewHistory> requirementInterviewHistoryList;
	
	@Transient
	private String offerJoinDate;
	
	@Transient
	private String positionType;
	
	@Transient
	private String offerRate;
	
	@Transient
	private String attachment;
	
	@Transient
	private Long offerId;
	
	@Transient
	private String submittedBy;
	
	@Transient
	private String proposedSubmissionRate;
	
	@Transient
	private String clientSubmissionId;
	
	@Transient
	private String submittedOn;
	
	@Transient
	private String state;
	
	@Transient
	private String city;
	
	@Transient
	private String country;
	
	@Transient
	private String ownerName;
	
	@Transient
	private String clientName;
	
	@Transient
	private String rate;
	
	@Transient
	private String employeeType;
	
	@Transient
	private String orgLocationId;
	
	@Transient
	private String filename;

	@Transient
	private int duplicateCount;
	
	@Transient
	private Long requirementId;
	
	@Transient
	private String orgName;
	
	@Transient
	private String submitToClientRate;
	
	@Transient
	private RequirementSubmissionTemplateFields requirementSubmissionTemplateFields;
	
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

	public String getNeedApplicantPermit() {
		return needApplicantPermit;
	}

	public void setNeedApplicantPermit(String needApplicantPermit) {
		this.needApplicantPermit = needApplicantPermit;
	}

	public SubmissionRateStatus getSubmissionRateStatus() {
		return submissionRateStatus;
	}

	public void setSubmissionRateStatus(SubmissionRateStatus submissionRateStatus) {
		this.submissionRateStatus = submissionRateStatus;
	}

	public String getSubmissionRateComment() {
		return submissionRateComment;
	}

	public void setSubmissionRateComment(String submissionRateComment) {
		this.submissionRateComment = submissionRateComment;
	}

	public byte[] getSubmissionRateDocFile() {
		return submissionRateDocFile;
	}

	public void setSubmissionRateDocFile(byte[] submissionRateDocFile) {
		this.submissionRateDocFile = submissionRateDocFile;
	}

	public String getSubmissionRateFileContentType() {
		return submissionRateFileContentType;
	}

	public void setSubmissionRateFileContentType(String submissionRateFileContentType) {
		this.submissionRateFileContentType = submissionRateFileContentType;
	}

	public String getSubmissionRequestComment() {
		return submissionRequestComment;
	}

	public void setSubmissionRequestComment(String submissionRequestComment) {
		this.submissionRequestComment = submissionRequestComment;
	}

	public String getSubmissionRequestUser() {
		return submissionRequestUser;
	}

	public void setSubmissionRequestUser(String submissionRequestUser) {
		this.submissionRequestUser = submissionRequestUser;
	}

	public byte[] getSubmissionRequestDocFile() {
		return submissionRequestDocFile;
	}

	public void setSubmissionRequestDocFile(byte[] submissionRequestDocFile) {
		this.submissionRequestDocFile = submissionRequestDocFile;
	}

	public String getSubmissionRequestFileContentType() {
		return submissionRequestFileContentType;
	}

	public void setSubmissionRequestFileContentType(String submissionRequestFileContentType) {
		this.submissionRequestFileContentType = submissionRequestFileContentType;
	}

	public String getSubmissionRequestFileName() {
		return submissionRequestFileName;
	}

	public void setSubmissionRequestFileName(String submissionRequestFileName) {
		this.submissionRequestFileName = submissionRequestFileName;
	}

	public String getSubmissionRateFileName() {
		return submissionRateFileName;
	}

	public void setSubmissionRateFileName(String submissionRateFileName) {
		this.submissionRateFileName = submissionRateFileName;
	}

	public String getFinalizedRate() {
		return finalizedRate;
	}

	public void setFinalizedRate(String finalizedRate) {
		this.finalizedRate = finalizedRate;
	}

	public String getFinalizedRateType() {
		return finalizedRateType;
	}

	public void setFinalizedRateType(String finalizedRateType) {
		this.finalizedRateType = finalizedRateType;
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
	
	public List<RequirementInterviewHistory> getRequirementInterviewHistoryList() {
		return requirementInterviewHistoryList;
	}

	public void setRequirementInterviewHistoryList(List<RequirementInterviewHistory> requirementInterviewHistoryList) {
		this.requirementInterviewHistoryList = requirementInterviewHistoryList;
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

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

	public int getNumOfSubmissions() {
		return numOfSubmissions;
	}

	public void setNumOfSubmissions(int numOfSubmissions) {
		this.numOfSubmissions = numOfSubmissions;
	}

	public int getNumOfShortList() {
		return numOfShortList;
	}

	public void setNumOfShortList(int numOfShortList) {
		this.numOfShortList = numOfShortList;
	}

	public int getNumOfInterview() {
		return numOfInterview;
	}

	public void setNumOfInterview(int numOfInterview) {
		this.numOfInterview = numOfInterview;
	}

	public int getNumOfSubmitToClient() {
		return numOfSubmitToClient;
	}

	public void setNumOfSubmitToClient(int numOfSubmitToClient) {
		this.numOfSubmitToClient = numOfSubmitToClient;
	}

	public int getNumOfOffers() {
		return numOfOffers;
	}

	public void setNumOfOffers(int numOfOffers) {
		this.numOfOffers = numOfOffers;
	}

	public int getNumOfRejected() {
		return numOfRejected;
	}

	public void setNumOfRejected(int numOfRejected) {
		this.numOfRejected = numOfRejected;
	}

	public int getTotalNoOfInterviews() {
		return totalNoOfInterviews;
	}

	public void setTotalNoOfInterviews(int totalNoOfInterviews) {
		this.totalNoOfInterviews = totalNoOfInterviews;
	}

	public List<RequirementInterview> getRequirementInterviewList() {
		return requirementInterviewList;
	}

	public void setRequirementInterviewList(List<RequirementInterview> requirementInterviewList) {
		this.requirementInterviewList = requirementInterviewList;
	}

	public String getOfferJoinDate() {
		return offerJoinDate;
	}

	public void setOfferJoinDate(String offerJoinDate) {
		this.offerJoinDate = offerJoinDate;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getOfferRate() {
		return offerRate;
	}

	public void setOfferRate(String offerRate) {
		this.offerRate = offerRate;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getProposedSubmissionRate() {
		return proposedSubmissionRate;
	}

	public void setProposedSubmissionRate(String proposedSubmissionRate) {
		this.proposedSubmissionRate = proposedSubmissionRate;
	}

	public String getClientSubmissionId() {
		return clientSubmissionId;
	}

	public void setClientSubmissionId(String clientSubmissionId) {
		this.clientSubmissionId = clientSubmissionId;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public ApplicantSubmissionStatus getPreviousSubmissionStatus() {
		return previousSubmissionStatus;
	}

	public void setPreviousSubmissionStatus(ApplicantSubmissionStatus previousSubmissionStatus) {
		this.previousSubmissionStatus = previousSubmissionStatus;
	}

	public String getUpDatedStr() {
		return upDatedStr;
	}

	public void setUpDatedStr(String upDatedStr) {
		this.upDatedStr = upDatedStr;
	}

	public int getNumofrequestsent() {
		return numofrequestsent;
	}

	public void setNumofrequestsent(int numofrequestsent) {
		this.numofrequestsent = numofrequestsent;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getDuplicateCount() {
		return duplicateCount;
	}

	public void setDuplicateCount(int duplicateCount) {
		this.duplicateCount = duplicateCount;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSubmitToClientRate() {
		return submitToClientRate;
	}

	public void setSubmitToClientRate(String submitToClientRate) {
		this.submitToClientRate = submitToClientRate;
	}

	public RequirementSubmissionTemplateFields getRequirementSubmissionTemplateFields() {
		return requirementSubmissionTemplateFields;
	}

	public void setRequirementSubmissionTemplateFields(
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields) {
		this.requirementSubmissionTemplateFields = requirementSubmissionTemplateFields;
	}

	public String getCandidateScore() {
		return candidateScore;
	}

	public void setCandidateScore(String candidateScore) {
		this.candidateScore = candidateScore;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
}
