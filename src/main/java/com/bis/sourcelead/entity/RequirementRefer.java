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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REQUIREMENT_REFER")
public class RequirementRefer implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_FROM_USER_ID")        
    private User referFromUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_TO_USER_ID")        
    private User referToUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_ID")        
    private Requirement requirementId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_ID")        
    private RequirementRefer referId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID")        
    private OrgLocations orgLocationId;
	
	
	@Column(name = "REFER_STATUS")
	private String referStatus;
	
	@Column(name = "REFER_UUID")
	private String referUUID;
	
	@Column(name = "REFER_FROM_EMAIL")
	private String referFromEmail;
	
	@Column(name = "REFER_TO_EMAIL")
	private String referToEmail;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "REFERED_USER_FIRSTNAME")
	private String referedUserFirstName;
	
	@Column(name = "REFERED_USER_LASTNAME")
	private String referedUserLastName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private Integer appliedJobs;
	
	@Transient
	private Integer referToMeJobs;
	
	@Transient
	private Integer referByMeJobs;
	
	@Transient
	private Integer shortlistJobs;
	
	@Transient
	private Integer offeredJobs;
	
	@Transient
	private Integer hiredJobs;
	
	@Transient
	private Integer rejectedJobs;
	
	@Transient
	private Integer interviews;
	
	@Transient
	private Integer referedToMeCount;
	
	@Transient
	private Integer submittedByMeJobs;
	
	@Transient
	private List<RequirementSubmission> crowdSourcingPortLetList;
	
	@Transient
	private List<RequirementRefer> referToMeList;
	
	@Transient
	private List<RequirementSubmission> referredByMeList;
	
	@Transient
	private List<RequirementRefer> referByMeJobsList;
	
	@Transient
	private  List<RequirementSubmission> submittedList;
	
	@Transient
	private String requirementName;
	
	@Transient
	private String requirementJobId;
	
	@Transient
	private String requirementUUID;
	
	@Transient
	private String requirementOrgName;
	
	@Transient
	private String requirementCountry;
	
	@Transient
	private String requirementState;
	
	@Transient
	private String requirementCity;
	
	@Transient
	private String requirementJobType;
	
	@Transient
	private String requirementRate;
	
	@Transient
	private String requirementRateType;
	
	@Transient
	private String requirementStatus;
	
	@Transient
	private String requirementSubmissionStatus;
	
	@Transient
	private String referredBy;
	
	@Transient
	private String referredOn;
	
	@Transient
	private String candidateName;
	
	@Transient
	private String candidateEmail;
	
	@Transient
	private String candidatePhoneNumber;
	
    @Transient
	private String dateString;
    
    @Transient
   	private String rejectedReason;
    
    @Transient
   	private String submissionStatus;
    
    @Transient
   	private String userName;
    
    @Transient
   	@JsonProperty
   	private List<String> locationsList;
    
    
	
		public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

		public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getReferFromUser() {
		return referFromUser;
	}

	public void setReferFromUser(User referFromUser) {
		this.referFromUser = referFromUser;
	}

	public User getReferToUser() {
		return referToUser;
	}

	public void setReferToUser(User referToUser) {
		this.referToUser = referToUser;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public RequirementRefer getReferId() {
		return referId;
	}

	public void setReferId(RequirementRefer referId) {
		this.referId = referId;
	}

	public String getReferFromEmail() {
		return referFromEmail;
	}

	public void setReferFromEmail(String referFromEmail) {
		this.referFromEmail = referFromEmail;
	}

	public String getReferToEmail() {
		return referToEmail;
	}

	public void setReferToEmail(String referToEmail) {
		this.referToEmail = referToEmail;
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

	public String getReferStatus() {
		return referStatus;
	}

	public void setReferStatus(String referStatus) {
		this.referStatus = referStatus;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(Integer appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

	public Integer getReferToMeJobs() {
		return referToMeJobs;
	}

	public void setReferToMeJobs(Integer referToMeJobs) {
		this.referToMeJobs = referToMeJobs;
	}

	public Integer getReferByMeJobs() {
		return referByMeJobs;
	}

	public void setReferByMeJobs(Integer referByMeJobs) {
		this.referByMeJobs = referByMeJobs;
	}

	public Integer getShortlistJobs() {
		return shortlistJobs;
	}

	public void setShortlistJobs(Integer shortlistJobs) {
		this.shortlistJobs = shortlistJobs;
	}

	public Integer getOfferedJobs() {
		return offeredJobs;
	}

	public void setOfferedJobs(Integer offeredJobs) {
		this.offeredJobs = offeredJobs;
	}


	public Integer getHiredJobs() {
		return hiredJobs;
	}

	public void setHiredJobs(Integer hiredJobs) {
		this.hiredJobs = hiredJobs;
	}

	public Integer getRejectedJobs() {
		return rejectedJobs;
	}

	public void setRejectedJobs(Integer rejectedJobs) {
		this.rejectedJobs = rejectedJobs;
	}

	public Integer getInterviews() {
		return interviews;
	}

	public void setInterviews(Integer interviews) {
		this.interviews = interviews;
	}

	public List<RequirementSubmission> getCrowdSourcingPortLetList() {
		return crowdSourcingPortLetList;
	}

	public void setCrowdSourcingPortLetList(List<RequirementSubmission> crowdSourcingPortLetList) {
		this.crowdSourcingPortLetList = crowdSourcingPortLetList;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Integer getReferedToMeCount() {
		return referedToMeCount;
	}

	public void setReferedToMeCount(Integer referedToMeCount) {
		this.referedToMeCount = referedToMeCount;
	}


	public List<RequirementRefer> getReferToMeList() {
		return referToMeList;
	}

	public void setReferToMeList(List<RequirementRefer> referToMeList) {
		this.referToMeList = referToMeList;
	}

	public Integer getSubmittedByMeJobs() {
		return submittedByMeJobs;
	}

	public void setSubmittedByMeJobs(Integer submittedByMeJobs) {
		this.submittedByMeJobs = submittedByMeJobs;
	}
	
	public List<RequirementSubmission> getReferredByMeList() {
		return referredByMeList;
	}

	public void setReferredByMeList(List<RequirementSubmission> referredByMeList) {
		this.referredByMeList = referredByMeList;
	}

	public List<RequirementRefer> getReferByMeJobsList() {
		return referByMeJobsList;
	}

	public void setReferByMeJobsList(List<RequirementRefer> referByMeJobsList) {
		this.referByMeJobsList = referByMeJobsList;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getRequirementJobId() {
		return requirementJobId;
	}

	public void setRequirementJobId(String requirementJobId) {
		this.requirementJobId = requirementJobId;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public String getRequirementOrgName() {
		return requirementOrgName;
	}

	public void setRequirementOrgName(String requirementOrgName) {
		this.requirementOrgName = requirementOrgName;
	}

	public String getRequirementCountry() {
		return requirementCountry;
	}

	public void setRequirementCountry(String requirementCountry) {
		this.requirementCountry = requirementCountry;
	}

	public String getRequirementState() {
		return requirementState;
	}

	public void setRequirementState(String requirementState) {
		this.requirementState = requirementState;
	}

	public String getRequirementCity() {
		return requirementCity;
	}

	public void setRequirementCity(String requirementCity) {
		this.requirementCity = requirementCity;
	}

	public String getRequirementRate() {
		return requirementRate;
	}

	public void setRequirementRate(String requirementRate) {
		this.requirementRate = requirementRate;
	}

	public String getRequirementRateType() {
		return requirementRateType;
	}

	public void setRequirementRateType(String requirementRateType) {
		this.requirementRateType = requirementRateType;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getRequirementSubmissionStatus() {
		return requirementSubmissionStatus;
	}

	public void setRequirementSubmissionStatus(String requirementSubmissionStatus) {
		this.requirementSubmissionStatus = requirementSubmissionStatus;
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

	public String getCandidatePhoneNumber() {
		return candidatePhoneNumber;
	}

	public void setCandidatePhoneNumber(String candidatePhoneNumber) {
		this.candidatePhoneNumber = candidatePhoneNumber;
	}

	public List<RequirementSubmission> getSubmittedList() {
		return submittedList;
	}

	public void setSubmittedList(List<RequirementSubmission> submittedList) {
		this.submittedList = submittedList;
	}
	

	public String getReferedUserFirstName() {
		return referedUserFirstName;
	}

	public void setReferedUserFirstName(String referedUserFirstName) {
		this.referedUserFirstName = referedUserFirstName;
	}

	public String getReferedUserLastName() {
		return referedUserLastName;
	}

	public void setReferedUserLastName(String referedUserLastName) {
		this.referedUserLastName = referedUserLastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRequirementJobType() {
		return requirementJobType;
	}

	public void setRequirementJobType(String requirementJobType) {
		this.requirementJobType = requirementJobType;
	}

	public String getReferUUID()
	{
		return referUUID;
	}

	public void setReferUUID(String referUUID)
	{
		this.referUUID = referUUID;
	}

	public String getRejectedReason()
	{
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason)
	{
		this.rejectedReason = rejectedReason;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}
}
