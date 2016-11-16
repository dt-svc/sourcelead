package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "CANDIDATE", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidate")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ALTERNATE_EMAIL")
	private String alternateEmail;
	
	@Column(name = "ALTERNATE_MOBILE_PHONE")
	private String alternateMobilePhone;
	
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "EXPERIENCE_YEARS")
	private String experienceYears;
	
	@Column(name = "EXPERIENCE_MONTHS")
	private String experienceMonths;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "DESIGNATION_TYPE")
	private String designationType;
	
	@Column(name = "CURRENT_RATE")
	private String currentRate;
	
	@Column(name = "CURRENT_RATE_CURRENCY")
	private String currentRateCurrency;
	
	@Column(name = "EXPECTED_RATE")
	private String expectedRate;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "EXPECTED_RATE_CURRENCY")
	private String expectedRateCurrencey;
	
	@Column(name = "SUMMARY")
	private String summary;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name="INVITATION_STATUS")
	private String invitationstatus;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="TERMINATE_REASON")
	private String terminateReason;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@OneToMany(mappedBy = "candidateId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateEducation> candidateEducation = new HashSet<CandidateEducation>(0);
	
	@OneToMany(mappedBy = "candidateId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateSkills> candidateSkills = new HashSet<CandidateSkills>(0);
	
	
	@OneToMany(mappedBy = "candidateId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateResumes> candidateResumes = new HashSet<CandidateResumes>(0);
	
	
	@OneToMany(mappedBy = "candidateId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateEmployers> candidateEmployers = new HashSet<CandidateEmployers>(0);
	
	@Transient
	private String skillsList;
	
	@Transient
	private Long candidateId;
	
	@Transient
	private String candidateUpdateDateToString;
	
	 @Transient
	 private String candidateScore;
	    
	 @Transient
	 private int noOfReq;
	 
	 @Transient
	 private String candidateResume;
	 
	 @Transient
	 private boolean isCandidateAdedd;
	 
	 @Transient
	 private String reqSubmissionId;
	 
	 
	 
	 @Transient
	 private List<RequirementSubmission> requirementSubmisstions;
	 

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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAlternateEmail() {
		return alternateEmail;
	}


	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}


	public String getAlternateMobilePhone() {
		return alternateMobilePhone;
	}


	public void setAlternateMobilePhone(String alternateMobilePhone) {
		this.alternateMobilePhone = alternateMobilePhone;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getExperienceYears() {
		return experienceYears;
	}


	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}


	public String getExperienceMonths() {
		return experienceMonths;
	}


	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDesignationType() {
		return designationType;
	}


	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}


	public String getCurrentRate() {
		return currentRate;
	}


	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}


	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}


	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}


	public String getExpectedRate() {
		return expectedRate;
	}


	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}


	public String getExpectedRateCurrencey() {
		return expectedRateCurrencey;
	}


	public void setExpectedRateCurrencey(String expectedRateCurrencey) {
		this.expectedRateCurrencey = expectedRateCurrencey;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
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


	public String getInvitationstatus() {
		return invitationstatus;
	}


	public void setInvitationstatus(String invitationstatus) {
		this.invitationstatus = invitationstatus;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getTerminateReason() {
		return terminateReason;
	}


	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
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


	public Set<CandidateEducation> getCandidateEducation() {
		return candidateEducation;
	}


	public void setCandidateEducation(Set<CandidateEducation> candidateEducation) {
		this.candidateEducation = candidateEducation;
	}


	public Set<CandidateSkills> getCandidateSkills() {
		return candidateSkills;
	}


	public void setCandidateSkills(Set<CandidateSkills> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}


	public Set<CandidateResumes> getCandidateResumes() {
		return candidateResumes;
	}


	public void setCandidateResumes(Set<CandidateResumes> candidateResumes) {
		this.candidateResumes = candidateResumes;
	}


	public Set<CandidateEmployers> getCandidateEmployers() {
		return candidateEmployers;
	}


	public void setCandidateEmployers(Set<CandidateEmployers> candidateEmployers) {
		this.candidateEmployers = candidateEmployers;
	}


	public String getSkillsList() {
		return skillsList;
	}


	public void setSkillsList(String skillsList) {
		this.skillsList = skillsList;
	}


	public Long getCandidateId() {
		return candidateId;
	}


	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}


	public String getCandidateUpdateDateToString() {
		return candidateUpdateDateToString;
	}


	public void setCandidateUpdateDateToString(String candidateUpdateDateToString) {
		this.candidateUpdateDateToString = candidateUpdateDateToString;
	}



	public String getRateType() {
		return rateType;
	}


	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getCandidateScore() {
		return candidateScore;
	}


	public void setCandidateScore(String candidateScore) {
		this.candidateScore = candidateScore;
	}


	public int getNoOfReq() {
		return noOfReq;
	}


	public void setNoOfReq(int noOfReq) {
		this.noOfReq = noOfReq;
	}


	public String getCandidateResume() {
		return candidateResume;
	}


	public void setCandidateResume(String candidateResume) {
		this.candidateResume = candidateResume;
	}


	public boolean getIsCandidateAdedd() {
		return isCandidateAdedd;
	}


	public void setIsCandidateAdedd(boolean isCandidateAdedd) {
		this.isCandidateAdedd = isCandidateAdedd;
	}


	public List<RequirementSubmission> getRequirementSubmisstions() {
		return requirementSubmisstions;
	}


	public void setRequirementSubmisstions(List<RequirementSubmission> requirementSubmisstions) {
		this.requirementSubmisstions = requirementSubmisstions;
	}


	public String getReqSubmissionId() {
		return reqSubmissionId;
	}
	public void setReqSubmissionId(String reqSubmissionId) {
		this.reqSubmissionId = reqSubmissionId;
	}
}
