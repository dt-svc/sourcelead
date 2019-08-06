package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REQUIREMENT_SUBMISSION")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementSubmission")
public class RequirementSubmission implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "REQUIREMENT_ID")
   	private Requirement requirement;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
   	private Candidate candidate;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "VENDOR_CANDIDATE_ID")
   	private Candidate vendorCandidateId;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "SOURCE_SUBMISSION_ID") 
   	private RequirementSubmission sourceSubmission;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_RESUEME_ID")
   	private CandidateResumes candidateResumes;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
   	private OrgLocations orgLocations;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "VENDOR_LOCATION_ID")
   	private OrgLocations vendorLocationId;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonString;
	
	@Column(name = "SUBMISSION_TEMPLATE_JSON_STRING_VALUES", columnDefinition = "TEXT default NULL")
    private String submissionTemplateJsonStringValue;
	
/*	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private ApplicantSubmissionStatus submissionStatus;*/
	
	@Column(name = "SUBMISSION_STATUS")
	private String submissionStatus;
	
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
	
	/*@Column(name = "PREVIOUS_SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
    private ApplicantSubmissionStatus previousSubmissionStatus;
	*/
	@Column(name = "PREVIOUS_SUBMISSION_STATUS")
    private String previousSubmissionStatus;
	
	@Column(name = "REJECTED_REASON", columnDefinition = "TEXT default NULL")
    private String rejectedReason;
	
	@Column(name = "REASON_TYPE")
    private String rejectedCategoryType;
	
	@Column(name = "BACKOUT_REASON", columnDefinition = "TEXT default NULL")
    private String backOutReason;
	
	@Column(name = "BACKOUT_TYPE")
    private String backOutCategoryType;
	
    
	@Column(name = "FINALIZED_RATE")
	private String finalizedRate;
	
	@Column(name = "FINALIZED_RATE_TYPE")
	private String finalizedRateType;
		
	@Column(name = "STATUS")
    private Integer status;
	
	@Column(name = "FILE_URL")
    private String docURL;
	
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
    
    
    @NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="RECRUITER_USER_ID")        
    private User recruiterUserId;
    
    @Column(name = "JOINED_DATE")
    private Date joinedDate;
    
    @Column(name = "INVOICE_GENERATED")
    private String invoiceGenerated;
	
    @Transient
    @JsonProperty
	private String userName;
    
    @Transient
    @JsonProperty
	private String candidateName;
	
    @Transient
    @JsonProperty
	private String candidateEmail;
	
    @Transient
    @JsonProperty
	private String phoneNumber;
    
    @Transient
    @JsonProperty
    private String currentCtc;
    
    @Transient
    @JsonProperty
	private String expectedCTC;
    
    // start  WORKFLOW related columns added by SRINIVAS 
    @Transient
    @JsonProperty
    private String alternateNumber;
    
    @Transient
    @JsonProperty
   	private String location;
    
    @Transient
    @JsonProperty
   	private String dateOfBirthStr;
    
    @Transient
    @JsonProperty
    private String education;
    
    @Transient
    @JsonProperty
	private String noticePeriod;
	
    @Transient
    @JsonProperty
	private List<String> workflowStages;
	
    
    // END  WORKFLOW related columns added by SRINIVAS 
    
    @Transient
    @JsonProperty
	private String workExperience;
    
    @Transient
    @JsonProperty
   	private String createdDateStr;
    
    @Transient
    @JsonProperty
   	private String upDatedStr;
    
    @Transient
    @JsonProperty
   	private String joinedDateStr;
    
    @Transient
    @JsonProperty
   	private String createdTimeStr;
    
    @Transient
    @JsonProperty
   	private String updatedTimeStr;
    
    @Transient
    @JsonProperty
	private String skillsList;
    
    @Transient
    @JsonProperty
   	private String candidateId;
    
    @Transient
    @JsonProperty
   	private String resumeName;
    
    @Transient
    @JsonProperty
   	private String requirementName;
    
    @Transient
    @JsonProperty
   	private Long resumeId;
    
    @Transient
   	private String candidateSummary;
    
    @Transient
    @JsonProperty
   	private String designationType;
    
    @Transient
    @JsonProperty
   	private String resumeUUID;
    
    @Transient
    @JsonProperty
   	private String requirementJobType;
    
    @Transient
    @JsonProperty
   	private String requirementStage;
    
	@Transient
	@JsonProperty
   	private String score;
	
	@Transient
	@JsonProperty
	private int numOfSubmissions;
	
	@Transient
	@JsonProperty
	private int numOfShortList;
	
	@Transient
	@JsonProperty
	private int numOfInterview;
	
	@Transient
	@JsonProperty
	private int numOfSubmitToClient;
	
	@Transient
	@JsonProperty
	private int numOfOffers;
	
	@Transient
	@JsonProperty
	private int numOfRejected;
	
	@Transient
	@JsonProperty
	private int totalNoOfInterviews;
	
	@Transient
	@JsonProperty
	private int numofrequestsent;
	
	@Transient
	List<RequirementInterview> requirementInterviewList;
	
	@Transient
	List<RequirementInterviewHistory> requirementInterviewHistoryList;
	
	@Transient
	@JsonProperty
	private String offerJoinDate;
	
	@Transient
	@JsonProperty
	private String positionType;
	
	@Transient
	@JsonProperty
	private String offerRate;
	
	@Transient
	@JsonProperty
	private String attachment;
	
	@Transient
	@JsonProperty
	private Long offerId;
	
	@Transient
	@JsonProperty
	private String submittedBy;
	
	@Transient
	@JsonProperty
	private String proposedSubmissionRate;
	
	@Transient
	@JsonProperty
	private String expectedRate;
	
	@Transient
	@JsonProperty
	private String clientSubmissionId;
	
	@Transient
	@JsonProperty
	private String submittedOn;
	
	@Transient
	@JsonProperty
	private String state;
	
	@Transient
	@JsonProperty
	private String city;
	
	@Transient
	@JsonProperty
	private String country;
	
	@Transient
	@JsonProperty
	private String ownerName;
	
	@Transient
	@JsonProperty
	private String clientName;
	
	@Transient
	@JsonProperty
	private String rate;
	
	@Transient
	@JsonProperty
	private String employeeType;
	
	@Transient
	@JsonProperty
	private String orgLocationId;
	
	@Transient
	@JsonProperty
	private String filename;

	@Transient
	@JsonProperty
	private int duplicateCount;
	
	@Transient
    @JsonProperty
   	private Long reqManagerId;
	
	
	@Transient
    @JsonProperty
   	private String reqReleaseAllJobUserStatus;
	
	@Transient
	@JsonProperty
	private Long requirementId;
	
	@Transient
	@JsonProperty
	private String orgName;
	
	@Transient
	@JsonProperty
	private String submitToClientRate;
	
	@Transient
	@JsonProperty
	private RequirementSubmissionTemplateFields requirementSubmissionTemplateFields;
	
	@Transient
	@JsonProperty
	private String requirementUUID;
	
	@Transient
	@JsonProperty
	private String loginOrgLOcationName;
	
	@Transient
	@JsonProperty
	private String requirementSubmissionStatus;
	
	@Transient
	@JsonProperty
	private String requirementJobId;

	@Transient
	@JsonProperty
	private String submitToClientRateType;
	
	@Transient
	@JsonProperty
	private String rateType;
	
	@Transient
	@JsonProperty
	private String referredBy;
	
	@Transient
	@JsonProperty
	private String referredOn;
	
	@Transient
	private List<RequirementSubmission> requirementSubmissionList;
	
	@Transient
	@JsonProperty
	private String candidateSource;

    @Transient
    @JsonProperty
	private String candidateUUID;
    
    @Transient
    @JsonProperty
	private String paymentType;
    
    @Transient
    @JsonProperty
	private String isClientSubmission;
    
    @Transient
    @JsonProperty
   	private String offerUUID;
    
    @Transient
    @JsonProperty
   	private String onboardUUId;
    
    @Transient
    @JsonProperty
   	private String onboardStatus;
    
    @Transient
    @JsonProperty
	private String lastRateStage;
    
    @Transient
    @JsonProperty
	private String lastRateIniUserCode;
    
    @Transient
    @JsonProperty
	private String lastRateIniLocId;
    
    @Transient
    @JsonProperty
	private String lastRateIniType;
    
    @Transient
	private String facebookUrl;
    
	@Transient
	private String linkedInUrl;
    
    @Transient
	private String twitterUrl;
    
    @Transient
    @JsonProperty
  	private String ratePer;
    
    @Transient
    @JsonProperty
	private String vendorLocationCode;
    
    @Column(name = "REASON_OTHER_TYPE")
    private String rejectedCategoryOther;
    
    @Column(name = "BACKOUT_OTHER_TYPE")
    private String backOutCategoryOther;
    
    @Transient
    @JsonProperty
   	private String referUUID;
    
    @Transient
    @JsonProperty
	 private int profilePercentage;
    
    @Transient
    @JsonProperty
	private String fileContentType;
    
    @Transient
    @JsonProperty
	private String fileName;
    
    @Transient
    @JsonProperty
	private String fileURL;
    
    @Transient
    @JsonProperty
	private String submissionDisplayStatus;
    
    @Transient
    @JsonProperty
	private String candidateCommentsCount;
    
    @Transient
    @JsonProperty
	private REQUIREMENT_SUBMISSION_STATUS requirementCurrentStatus;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requirementSubmission")
	private Set<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submissionId")
   	private Set<RequirementSubmissionHistory> requirementSubmissionHistoryList;
    
    @JsonBackReference
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submissionId")
   	private Set<RequirementInterviewHistory> requirementInterviewHistory;
    
    @JsonBackReference
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submissionId")
   	private Set<RequirementOffers> requirementOffersList;
    
    @JsonBackReference
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submissionId")
   	private Set<RequirementOfferHistory> requirementOfferHistoryList;
    
    @Transient
    @JsonProperty
	private Long orgRelationId;
    
    @Transient
	@JsonProperty
	private List<String> locationsList;
    
    @Transient
	@JsonProperty
	private String invoiceGenerateDate;

    
    
	public int getProfilePercentage()
	{
		return profilePercentage;
	}

	public void setProfilePercentage(int profilePercentage)
	{
		this.profilePercentage = profilePercentage;
	}
	
	public String getRatePer()
	{
		return ratePer;
	}

	public void setRatePer(String ratePer)
	{
		this.ratePer = ratePer;
	}

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

	public String getCurrentCtc() {
		return currentCtc;
	}

	public void setCurrentCtc(String currentCtc) {
		this.currentCtc = currentCtc;
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

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
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

	public String getRequirementStage() {
		return requirementStage;
	}

	public void setRequirementStage(String requirementStage) {
		this.requirementStage = requirementStage;
	}

	public String getRequirementJobType() {
		return requirementJobType;
	}

	public void setRequirementJobType(String requirementJobType) {
		this.requirementJobType = requirementJobType;
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
	
	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getLastRateStage() {
		return lastRateStage;
	}

	public void setLastRateStage(String lastRateStage) {
		this.lastRateStage = lastRateStage;
	}

	public String getLastRateIniUserCode() {
		return lastRateIniUserCode;
	}

	public void setLastRateIniUserCode(String lastRateIniUserCode) {
		this.lastRateIniUserCode = lastRateIniUserCode;
	}

	public String getLastRateIniLocId() {
		return lastRateIniLocId;
	}

	public void setLastRateIniLocId(String lastRateIniLocId) {
		this.lastRateIniLocId = lastRateIniLocId;
	}

	public String getLastRateIniType() {
		return lastRateIniType;
	}

	public void setLastRateIniType(String lastRateIniType) {
		this.lastRateIniType = lastRateIniType;
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

	public String getPreviousSubmissionStatus() {
		return previousSubmissionStatus;
	}

	public void setPreviousSubmissionStatus(String previousSubmissionStatus) {
		this.previousSubmissionStatus = previousSubmissionStatus;
	}

	public String getUpDatedStr() {
		return upDatedStr;
	}

	public void setUpDatedStr(String upDatedStr) {
		this.upDatedStr = upDatedStr;
	}

	public String getJoinedDateStr() {
		return joinedDateStr;
	}

	public void setJoinedDateStr(String joinedDateStr) {
		this.joinedDateStr = joinedDateStr;
	}

	public String getCreatedTimeStr() {
		return createdTimeStr;
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	public String getUpdatedTimeStr() {
		return updatedTimeStr;
	}

	public void setUpdatedTimeStr(String updatedTimeStr) {
		this.updatedTimeStr = updatedTimeStr;
	}

	public int getNumofrequestsent() {
		return numofrequestsent;
	}

	public void setNumofrequestsent(int numofrequestsent) {
		this.numofrequestsent = numofrequestsent;
	}

	public String getCandidateSummary() {
		return candidateSummary;
	}

	public void setCandidateSummary(String candidateSummary) {
		this.candidateSummary = candidateSummary;
	}

	public String getDesignationType() {
		return designationType;
	}

	public void setDesignationType(String designationType) {
		this.designationType = designationType;
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

	public String getResumeUUID() {
		return resumeUUID;
	}

	public void setResumeUUID(String resumeUUID) {
		this.resumeUUID = resumeUUID;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public User getRecruiterUserId() {
		return recruiterUserId;
	}

	public void setRecruiterUserId(User recruiterUserId) {
		this.recruiterUserId = recruiterUserId;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getLoginOrgLOcationName() {
		return loginOrgLOcationName;
	}

	public void setLoginOrgLOcationName(String loginOrgLOcationName) {
		this.loginOrgLOcationName = loginOrgLOcationName;
	}

	public String getRequirementSubmissionStatus() {
		return requirementSubmissionStatus;
	}

	public void setRequirementSubmissionStatus(String requirementSubmissionStatus) {
		this.requirementSubmissionStatus = requirementSubmissionStatus;
	}

	public String getRequirementJobId() {
		return requirementJobId;
	}

	public void setRequirementJobId(String requirementJobId) {
		this.requirementJobId = requirementJobId;
	}

	public String getSubmitToClientRateType() {
		return submitToClientRateType;
	}

	public void setSubmitToClientRateType(String submitToClientRateType) {
		this.submitToClientRateType = submitToClientRateType;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getReferredOn() {
		return referredOn;
	}

	public void setReferredOn(String referredOn) {
		this.referredOn = referredOn;
	}

	public List<RequirementSubmission> getRequirementSubmissionList() {
		return requirementSubmissionList;
	}

	public void setRequirementSubmissionList(List<RequirementSubmission> requirementSubmissionList) {
		this.requirementSubmissionList = requirementSubmissionList;
	}

	public String getCandidateSource() {
		return candidateSource;
	}

	public void setCandidateSource(String candidateSource) {
		this.candidateSource = candidateSource;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getIsClientSubmission() {
		return isClientSubmission;
	}

	public void setIsClientSubmission(String isClientSubmission) {
		this.isClientSubmission = isClientSubmission;
	}

	public String getOfferUUID() {
		return offerUUID;
	}

	public void setOfferUUID(String offerUUID) {
		this.offerUUID = offerUUID;
	}

	public String getOnboardUUId() {
		return onboardUUId;
	}

	public void setOnboardUUId(String onboardUUId) {
		this.onboardUUId = onboardUUId;
	}

	public String getOnboardStatus() {
		return onboardStatus;
	}

	public void setOnboardStatus(String onboardStatus) {
		this.onboardStatus = onboardStatus;
	}
	
	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public String getVendorLocationCode() {
		return vendorLocationCode;
	}

	public void setVendorLocationCode(String vendorLocationCode) {
		this.vendorLocationCode = vendorLocationCode;
	}

	public String getRejectedCategoryType() {
		return rejectedCategoryType;
	}

	public void setRejectedCategoryType(String rejectedCategoryType) {
		this.rejectedCategoryType = rejectedCategoryType;
	}

	public String getRejectedCategoryOther()
	{
		return rejectedCategoryOther;
	}

	public void setRejectedCategoryOther(String rejectedCategoryOther)
	{
		this.rejectedCategoryOther = rejectedCategoryOther;
	}

	public String getReferUUID() {
		return referUUID;
	}

	public void setReferUUID(String referUUID) {
		this.referUUID = referUUID;
	}

	public Set<RequirementSubmissionTemplateFields> getRequirementSubmissionTemplateFieldsList() {
		return requirementSubmissionTemplateFieldsList;
	}

	public void setRequirementSubmissionTemplateFieldsList(
			Set<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList) {
		this.requirementSubmissionTemplateFieldsList = requirementSubmissionTemplateFieldsList;
	}

	public Set<RequirementSubmissionHistory> getRequirementSubmissionHistoryList() {
		return requirementSubmissionHistoryList;
	}

	public void setRequirementSubmissionHistoryList(Set<RequirementSubmissionHistory> requirementSubmissionHistoryList) {
		this.requirementSubmissionHistoryList = requirementSubmissionHistoryList;
	}

	public Set<RequirementInterviewHistory> getRequirementInterviewHistory() {
		return requirementInterviewHistory;
	}

	public void setRequirementInterviewHistory(Set<RequirementInterviewHistory> requirementInterviewHistory) {
		this.requirementInterviewHistory = requirementInterviewHistory;
	}

	public Set<RequirementOffers> getRequirementOffersList() {
		return requirementOffersList;
	}

	public void setRequirementOffersList(Set<RequirementOffers> requirementOffersList) {
		this.requirementOffersList = requirementOffersList;
	}

	public Set<RequirementOfferHistory> getRequirementOfferHistoryList() {
		return requirementOfferHistoryList;
	}

	public void setRequirementOfferHistoryList(Set<RequirementOfferHistory> requirementOfferHistoryList) {
		this.requirementOfferHistoryList = requirementOfferHistoryList;
	}

	public Candidate getVendorCandidateId() {
		return vendorCandidateId;
	}

	public void setVendorCandidateId(Candidate vendorCandidateId) {
		this.vendorCandidateId = vendorCandidateId;
	}

	public REQUIREMENT_SUBMISSION_STATUS getRequirementCurrentStatus() {
		return requirementCurrentStatus;
	}

	public void setRequirementCurrentStatus(REQUIREMENT_SUBMISSION_STATUS requirementCurrentStatus) {
		this.requirementCurrentStatus = requirementCurrentStatus;
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

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public String getDocURL() {
		return docURL;
	}

	public void setDocURL(String docURL) {
		this.docURL = docURL;
	}

	public String getBackOutReason() {
		return backOutReason;
	}

	public void setBackOutReason(String backOutReason) {
		this.backOutReason = backOutReason;
	}

	public String getBackOutCategoryType() {
		return backOutCategoryType;
	}

	public void setBackOutCategoryType(String backOutCategoryType) {
		this.backOutCategoryType = backOutCategoryType;
	}

	public String getBackOutCategoryOther() {
		return backOutCategoryOther;
	}

	public void setBackOutCategoryOther(String backOutCategoryOther) {
		this.backOutCategoryOther = backOutCategoryOther;
	}

	public String getSubmissionDisplayStatus() {
		return submissionDisplayStatus;
	}

	public void setSubmissionDisplayStatus(String submissionDisplayStatus) {
		this.submissionDisplayStatus = submissionDisplayStatus;
	}

	public String getCandidateCommentsCount()
	{
		return candidateCommentsCount;
	}

	public void setCandidateCommentsCount(String candidateCommentsCount)
	{
		this.candidateCommentsCount = candidateCommentsCount;
	}

	public Long getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public Long getReqManagerId()
	{
		return reqManagerId;
	}

	public void setReqManagerId(Long reqManagerId)
	{
		this.reqManagerId = reqManagerId;
	}

	public String getReqReleaseAllJobUserStatus()
	{
		return reqReleaseAllJobUserStatus;
	}

	public void setReqReleaseAllJobUserStatus(String reqReleaseAllJobUserStatus)
	{
		this.reqReleaseAllJobUserStatus = reqReleaseAllJobUserStatus;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}
	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

	public String getInvoiceGenerated() {
		return invoiceGenerated;
	}

	public void setInvoiceGenerated(String invoiceGenerated) {
		this.invoiceGenerated = invoiceGenerated;
	}

	public String getInvoiceGenerateDate() {
		return invoiceGenerateDate;
	}

	public void setInvoiceGenerateDate(String invoiceGenerateDate) {
		this.invoiceGenerateDate = invoiceGenerateDate;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getDateOfBirthStr() {
		return dateOfBirthStr;
	}

	public void setDateOfBirthStr(String dateOfBirthStr) {
		this.dateOfBirthStr = dateOfBirthStr;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public List<String> getWorkflowStages() {
		return workflowStages;
	}

	public void setWorkflowStages(List<String> workflowStages) {
		this.workflowStages = workflowStages;
	}

	
}
