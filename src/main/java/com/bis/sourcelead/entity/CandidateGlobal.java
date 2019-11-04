package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CANDIDATE_GLOBAL")
public class CandidateGlobal {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASTER_CANDIDATE_ID", nullable = false)
	private MasterCandidate masterCandidateId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_CANDIDATE_ID")
	private CandidateGlobal parentCandidateId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSULTANT_USER_ID")
	private User consultantUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIFT_ID")
	private Shift shift;	
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WORK_LOCATION")
	private OrgLocations workLocation;
 	
	@Column(name = "RESIDENCE_ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String residenceAddress;
	
	@Column(name = "RESIDENCE_STREET_ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String residenceStreetAddress;
	
	@Column(name = "RESIDENCE_COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String residenceCountry;
	
	@Column(name = "RESIDENCE_STATE", columnDefinition = "LONGTEXT default NULL")
	private String residenceState;
	
	@Column(name = "RESIDENCE_CITY", columnDefinition = "LONGTEXT default NULL")
	private String residenceCity;
	
	@Column(name = "RESIDENCE_ZIP_CODE")
	private String residenceZipCode;
	
	@Column(name = "RESIDENCE_STATE_CODE")
	private String residenceStateCode;
	
	@Column(name = "RESIDENCE_LATITUDE")
	private String residenceLatitude;
	
	@Column(name = "RESIDENCE_LONGITUDE")
	private String residenceLongitude;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID", nullable = false)
	private Org orgId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECRUITER_USER_ID")
	private User recruiterUserId;

	@Column(name = "CANDIDATE_TYPE")
	private String candidateType;

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

	@Column(name = "COUNTRY", columnDefinition = "TEXT default NULL")
	private String country;

	@Column(name = "STATE", columnDefinition = "TEXT default NULL")
	private String state;

	@Column(name = "CITY", columnDefinition = "TEXT default NULL")
	private String city;

	
	@Column(name = "ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String address;
	
	@Column(name = "LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String location;

	@Column(name = "ZIPCODE")
	private String zipCode;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "EXPERIENCE_YEARS")
	private String experienceYears;

	@Column(name = "EXPERIENCE_MONTHS")
	private String experienceMonths;

	@Column(name = "NOTICE_PERIOD")
	private String noticePeriod;
	
	@Column(name = "OTHER_NOTICE_PERIOD")
	private String otherNoticePeriod;
	
	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "DESIGNATION_TYPE")
	private String designationType;

	@Column(name = "DESIGNATION_OTHER_TYPE")
	private String designationOtherType;

	@Column(name = "CURRENT_RATE")
	private String currentRate;

	@Column(name = "CURRENT_RATE_TYPE")
	private String currentRateType;

	@Column(name = "CURRENT_RATE_CURRENCY")
	private String currentRateCurrency;

	@Column(name = "EXPECTED_RATE")
	private String expectedRate;

	@Column(name = "RATE_TYPE")
	private String rateType;

	@Column(name = "EXPECTED_RATE_CURRENCY")
	private String expectedRateCurrencey;

	@Column(name = "SUMMARY", columnDefinition = "TEXT default NULL")
	private String summary;

	@Column(name = "VISA_TYPE", columnDefinition = "TEXT default NULL")
	private String visaType;

	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "INVITATION_STATUS")
	private String invitationstatus;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "FILE_URL")
	private String fileUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Team teamId;

	@Column(name = "IS_FAVOURITE")
	private Integer isFavourite;

	@Column(name = "IS_FAVOURITE_ASSOCIATE")
	private Integer isFavouriteAssociate;

	@Column(name = "TERMINATE_REASON")
	private String terminateReason;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "IS_PROFILE_PUBLIC")
	private String isProfilePublic;

	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;

	@Column(name = "IS_ASSOCIATE")
	private String isAssociate;
	
	@Column(name = "IS_SHARED_IN_MARKETPLACE")
	private String isSharedInMarketplace;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_RELATION_ID")
	private OrgRelations orgRelationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "SPOC_ID")
	private OrgSpoc spocId;

	@Transient
	@JsonProperty
	private String skills;

	@Transient
	@JsonProperty
	private String totalExp;

	@Transient
	@JsonProperty
	private String rate;

	@Transient
	@JsonProperty
	private String userRole;

	@Transient
	@JsonProperty
	private String candidateName;

	@Transient
	@JsonProperty
	private String candidateLocation;

	@Transient
	@JsonProperty
	private String systemGeneratedId;

	@Column(name = "SKILLS_NAME", columnDefinition = "TEXT default NULL")
	private String skillsName;

	@Column(name = "FACEBOOK_PROFILE_URL")
	private String facebookUrl;

	@Column(name = "PRIMARY_SKILL")
	private String primarySkill;

	@Column(name = "CANDIDATE_SOURCE")
	private String candidateSource;
	
	@Column(name = "LINKEDIN_PROFILE_URL")
	private String linkedUrl;

	@Column(name = "TWITTER_PROFILE_URL")
	private String twitterUrl;

	// Immigration columns Start
	@Column(name = "MIDDLE_NAME")
	private String middelName;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "PAN_NUMBER")
	private String panNumber;
	
	@Column(name = "AADHAR_NUMBER")
	private String aadharNumber;

	@Column(name = "PLACE_OF_BIRTH")
	private String placeOfBirth;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "NATIONALITY")
	private String nationality;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "RESIDENCE_START_DATE")
	private Date residenceStartDate;

	@Column(name = "RESIDENCE_END_DATE")
	private Date residenceEndDate;

	@Column(name = "MILITARY_SERVICE_COUNTRY")
	private String militaryServiceCountry;

	@Column(name = "SERVICE_START_DATE")
	private Date serviceStartDate;

	@Column(name = "SERVICE_END_DATE")
	private Date serviceEndDate;

	@Column(name = "I_94_NO")
	private String i94No;

	@Column(name = "I_20_SEVIS_NO")
	private String i20ServiceNo;

	@Column(name = "EAD_NO")
	private String eadNo;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "RANK")
	private String rank;
	
	@Column(name = "PROFILE_PERCENTAGE")
	private Integer candidateProfilePercentage;

	// Immigration columns End
	@JsonProperty
	@Transient
	private String fullName;

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateEducation> candidateEducation = new HashSet<CandidateEducation>(0);

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateResumes> candidateResumes = new HashSet<CandidateResumes>(0);

	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateEmployers> candidateEmployers = new HashSet<CandidateEmployers>(0);
	
	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateHistory> candidateHistory = new HashSet<CandidateHistory>(0);
	
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateTimelineActivity> candidateTimeLineActivity = new HashSet<CandidateTimelineActivity>(0);
	
	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateCertifications> candidateCertifications = new HashSet<CandidateCertifications>();

	@Transient
	@JsonProperty
	private String candidateEdu;
	
	@Transient
	@JsonProperty
	private String createdDateStr;

	@Transient
	@JsonProperty
	private String skillsList;

	@Transient
	@JsonProperty
	private Long candidateId;

	@Transient
	@JsonProperty
	private String candidateUpdateDateToString;
	
	 @Transient
	 @JsonProperty
	 private String candidateScore;
	 
	 @Transient
	 @JsonProperty
	 private String isCandidateSubmitted;
	    
	 @Transient
	 @JsonProperty
	 private int noOfReq;
	 
	 @Transient
	 @JsonProperty
	 private String candidateResume;
	 
	 @Transient
	 @JsonProperty
	 private boolean isCandidateAdedd;
	 
	 @Transient
	 @JsonProperty
	 private String reqSubmissionId;
	 
	 @Transient
	 @JsonProperty
	 private float skillPercentage;
	 
	 @Transient
	 @JsonProperty
	 private float expPercentage;
	 
	 @Transient
	 @JsonProperty
	 private float locationPercentage;
	 
	 @Transient
	 @JsonProperty
	 private float ratePercentage;
	 
	 @Transient
	 @JsonProperty
	 private float noticePeriodPercentage;
	 
	 @Transient
	 @JsonProperty
	 private float totalKeyWordPerPercentage;
	 
	 @Transient
	 @JsonProperty
	 private Long currentResumeId;
	 
	 @Transient
	 @JsonProperty
	 private String linkedIn;
		
	 @Transient
	 @JsonProperty
	 private String facebook;
		
	 @Transient
	 @JsonProperty
	 private String twitter;
		
	 @Transient
	 @JsonProperty
	 private String googlePlus;
	 
	 @Transient
	 @JsonProperty
	 private String postedDateStr;
	 
	 @Transient
	 @JsonProperty
	 private String vendorName;
	 
	 @Transient
	 @JsonProperty
	 private String vendorContactName;
	 
	 @Transient
	 @JsonProperty
	 private Integer assignedProjectsCount;
	 

	@Transient
	@JsonProperty
	private String vendorOrgRelationId;

	@Transient
	@JsonProperty
	private String employeList;

	@Transient
	@JsonProperty
	private String vendorSpocId;

	 
	 @Transient
	 @JsonProperty
	 private List<RequirementSubmission> requirementSubmisstions;
	 
	 @Transient
	 @JsonProperty
	 private LinkedHashSet<String> allAndCandidateSkills;
	 
	 @Transient
	 @JsonProperty
	 private LinkedHashSet<String> allAndCandidateEducation;
	 
	 @Transient
	 @JsonProperty
	 private Integer candidateRequiementCount;
	 
	 @Transient
	 @JsonProperty
	 private  List<String> candidateSkillsList;
	 
	 @Transient
	 @JsonProperty
	 private String employer;
	 
	 @Transient
	 @JsonProperty
	 private String sowContractName;
	 
	 @Transient
	 @JsonProperty
	 private LinkedHashSet<String> allAndCandidateVisa;
	 
	 @Transient
	 @JsonProperty
	 private String candidateResumeName;
	 
	 @Transient
	 @JsonProperty
	 private String candidateResumeFileType;
	 
	 @Transient
	 @JsonProperty
	 private List<Contracts> contractsList;
	 

	 @Transient
	 @JsonProperty
	 private String reportingManager;
	 
	 @Transient
	 @JsonProperty
	 private String resumeUUID;
	 
	 @Transient
	 @JsonProperty
	 private String isResumeExists;
	 

	 @Column(name = "GENERATED_ID")
	 private String generatedId;

	 @Transient
	 @JsonProperty
	 private int profilePercentage;
	 
	 @Transient
	 @JsonProperty
	 private String candLocation;
	 
	 @Transient
	 @JsonProperty
	 private Long relationId;
	 
	 @Transient
	 @JsonProperty
	 private Long venSpocId;
	 
	 @Transient
	 @JsonProperty
	 private String commentsCount;
	 
	 @Transient
	 @JsonProperty
	 private String dateofBirthString;
	 
	 
     @Transient
	 @JsonProperty
	 private String candidateTags;
     
     @Transient
	 @JsonProperty
     private Set<String> tagsList;
	 
     @Transient
	 @JsonProperty
	 private Set<String> candidateTagsList;
     
     @Transient
 	 @JsonProperty
 	 private String candidateAssignedJobsCount;
     
     @Transient
 	 @JsonProperty
     private Long masterCandId;
     
     @Column(name = "SSN_Number")
  	 private String ssnNumber;
	 
	public int getProfilePercentage()
	{

		return profilePercentage;
	}

	public void setProfilePercentage(int profilePercentage) {
		this.profilePercentage = profilePercentage;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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

	public String getDesignationOtherType() {
		return designationOtherType;
	}

	public void setDesignationOtherType(String designationOtherType) {
		this.designationOtherType = designationOtherType;
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


	@JsonIgnore

	public Set<CandidateEducation> getCandidateEducation() {
		return candidateEducation;
	}

	public void setCandidateEducation(Set<CandidateEducation> candidateEducation) {
		this.candidateEducation = candidateEducation;
	}



	/*public Set<CandidateSkills> getCandidateSkills() {
		return candidateSkills;
	}


	public void setCandidateSkills(Set<CandidateSkills> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}
*/
	@JsonIgnore

	public Set<CandidateResumes> getCandidateResumes() {
		return candidateResumes;
	}

	public void setCandidateResumes(Set<CandidateResumes> candidateResumes) {
		this.candidateResumes = candidateResumes;
	}


	@JsonIgnore

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

	public String getCandidateEdu() {
		return candidateEdu;
	}

	public void setCandidateEdu(String candidateEdu) {
		this.candidateEdu = candidateEdu;
	}

	public String getCandidateResume() {
		return candidateResume;
	}

	public void setCandidateResume(String candidateResume) {
		this.candidateResume = candidateResume;
	}

	public String getVendorOrgRelationId() {
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(String vendorOrgRelationId) {
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	public String getVendorSpocId() {
		return vendorSpocId;
	}

	public void setVendorSpocId(String vendorSpocId) {
		this.vendorSpocId = vendorSpocId;
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

	public LinkedHashSet<String> getAllAndCandidateSkills() {
		return allAndCandidateSkills;
	}

	public void setAllAndCandidateSkills(LinkedHashSet<String> allAndCandidateSkills) {
		this.allAndCandidateSkills = allAndCandidateSkills;
	}

	public LinkedHashSet<String> getAllAndCandidateEducation() {
		return allAndCandidateEducation;
	}

	public void setAllAndCandidateEducation(LinkedHashSet<String> allAndCandidateEducation) {
		this.allAndCandidateEducation = allAndCandidateEducation;
	}

	public String getReqSubmissionId() {
		return reqSubmissionId;
	}

	public void setReqSubmissionId(String reqSubmissionId) {
		this.reqSubmissionId = reqSubmissionId;
	}

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public User getConsultantUser() {
		return consultantUser;
	}

	public void setConsultantUser(User consultantUser) {
		this.consultantUser = consultantUser;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

	public Team getTeamId() {
		return teamId;
	}

	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}

	public Integer getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Integer isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	
	public String getOtherNoticePeriod() {
		return otherNoticePeriod;
	}

	public void setOtherNoticePeriod(String otherNoticePeriod) {
		this.otherNoticePeriod = otherNoticePeriod;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public void setCandidateAdedd(boolean isCandidateAdedd) {
		this.isCandidateAdedd = isCandidateAdedd;
	}

	public String getCandidateLocation() {
		return candidateLocation;
	}

	public void setCandidateLocation(String candidateLocation) {
		this.candidateLocation = candidateLocation;
	}

	public float getSkillPercentage() {
		return skillPercentage;
	}

	public void setSkillPercentage(float skillPercentage) {
		this.skillPercentage = skillPercentage;
	}

	public float getExpPercentage() {
		return expPercentage;
	}

	public void setExpPercentage(float expPercentage) {
		this.expPercentage = expPercentage;
	}

	public float getLocationPercentage() {
		return locationPercentage;
	}

	public void setLocationPercentage(float locationPercentage) {
		this.locationPercentage = locationPercentage;
	}

	public float getRatePercentage() {
		return ratePercentage;
	}

	public void setRatePercentage(float ratePercentage) {
		this.ratePercentage = ratePercentage;
	}

	public float getNoticePeriodPercentage() {
		return noticePeriodPercentage;
	}

	public void setNoticePeriodPercentage(float noticePeriodPercentage) {
		this.noticePeriodPercentage = noticePeriodPercentage;
	}

	public float getTotalKeyWordPerPercentage() {
		return totalKeyWordPerPercentage;
	}

	public void setTotalKeyWordPerPercentage(float totalKeyWordPerPercentage) {
		this.totalKeyWordPerPercentage = totalKeyWordPerPercentage;
	}

	public Long getCurrentResumeId() {
		return currentResumeId;
	}

	public void setCurrentResumeId(Long currentResumeId) {
		this.currentResumeId = currentResumeId;
	}

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public User getRecruiterUserId() {
		return recruiterUserId;
	}

	public void setRecruiterUserId(User recruiterUserId) {
		this.recruiterUserId = recruiterUserId;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getGooglePlus() {
		return googlePlus;
	}

	public void setGooglePlus(String googlePlus) {
		this.googlePlus = googlePlus;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public String getEmployeList() {
		return employeList;
	}

	public void setEmployeList(String employeList) {
		this.employeList = employeList;
	}

	public String getIsProfilePublic() {
		return isProfilePublic;
	}

	public void setIsProfilePublic(String isProfilePublic) {
		this.isProfilePublic = isProfilePublic;
	}

	public String getPostedDateStr() {
		return postedDateStr;
	}

	public void setPostedDateStr(String postedDateStr) {
		this.postedDateStr = postedDateStr;
	}

	public Integer getCandidateRequiementCount() {
		return candidateRequiementCount;
	}

	public void setCandidateRequiementCount(Integer candidateRequiementCount) {
		this.candidateRequiementCount = candidateRequiementCount;
	}

	public String getIsAssociate() {
		return isAssociate;
	}

	public void setIsAssociate(String isAssociate) {
		this.isAssociate = isAssociate;
	}
	
	public String getIsSharedInMarketplace() {
		return isSharedInMarketplace;
	}

	public void setIsSharedInMarketplace(String isSharedInMarketplace) {
		this.isSharedInMarketplace = isSharedInMarketplace;
	}

	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorContactName() {
		return vendorContactName;
	}

	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}

	public Integer getAssignedProjectsCount() {
		return assignedProjectsCount;
	}

	public void setAssignedProjectsCount(Integer assignedProjectsCount) {
		this.assignedProjectsCount = assignedProjectsCount;
	}

	public OrgSpoc getSpocId() {
		return spocId;
	}

	public void setSpocId(OrgSpoc spocId) {
		this.spocId = spocId;
	}

	public Integer getIsFavouriteAssociate() {
		return isFavouriteAssociate;
	}

	public void setIsFavouriteAssociate(Integer isFavouriteAssociate) {
		this.isFavouriteAssociate = isFavouriteAssociate;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public List<String> getCandidateSkillsList() {
		return candidateSkillsList;
	}

	public void setCandidateSkillsList(List<String> candidateSkillsList) {
		this.candidateSkillsList = candidateSkillsList;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getLinkedUrl() {
		return linkedUrl;
	}

	public void setLinkedUrl(String linkedUrl) {
		this.linkedUrl = linkedUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public String getMiddelName() {
		return middelName;
	}

	public void setMiddelName(String middelName) {
		this.middelName = middelName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMilitaryServiceCountry() {
		return militaryServiceCountry;
	}

	public void setMilitaryServiceCountry(String militaryServiceCountry) {
		this.militaryServiceCountry = militaryServiceCountry;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getI94No() {
		return i94No;
	}

	public void setI94No(String i94No) {
		this.i94No = i94No;
	}

	public String getI20ServiceNo() {
		return i20ServiceNo;
	}

	public void setI20ServiceNo(String i20ServiceNo) {
		this.i20ServiceNo = i20ServiceNo;
	}

	public String getEadNo() {
		return eadNo;
	}

	public void setEadNo(String eadNo) {
		this.eadNo = eadNo;
	}

	public String getFullName() {
		if (StringUtils.isNotEmpty(fullName))
			return fullName;
		else {
			if(StringUtils.isNotEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(middelName)){
				fullName = firstName;
			}else if(StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName) && StringUtils.isEmpty(middelName)){
				fullName = firstName +" "+lastName;
			}else if(StringUtils.isNotEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isNotEmpty(middelName)){
				fullName = firstName +" "+middelName;
			}else if(StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(middelName)){
				fullName = firstName+" "+middelName+" "+lastName;
			}
			return fullName;
		}
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSowContractName() {
		return sowContractName;
	}

	public void setSowContractName(String sowContractName) {
		this.sowContractName = sowContractName;
	}

	public List<Contracts> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<Contracts> contractsList) {
		this.contractsList = contractsList;
	}

	public MasterCandidate getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(MasterCandidate masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public LinkedHashSet<String> getAllAndCandidateVisa() {
		return allAndCandidateVisa;
	}

	public void setAllAndCandidateVisa(LinkedHashSet<String> allAndCandidateVisa) {
		this.allAndCandidateVisa = allAndCandidateVisa;
	}

	public String getCandidateResumeName() {
		return candidateResumeName;
	}

	public void setCandidateResumeName(String candidateResumeName) {
		this.candidateResumeName = candidateResumeName;
	}

	public String getCandidateResumeFileType() {
		return candidateResumeFileType;
	}

	public void setCandidateResumeFileType(String candidateResumeFileType) {
		this.candidateResumeFileType = candidateResumeFileType;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getResumeUUID() {
		return resumeUUID;
	}

	public void setResumeUUID(String resumeUUID) {
		this.resumeUUID = resumeUUID;
	}

	public String getIsResumeExists() {
		return isResumeExists;
	}

	public void setIsResumeExists(String isResumeExists) {
		this.isResumeExists = isResumeExists;
	}

	public String getGeneratedId() {
		return generatedId;
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

	public String getCandLocation() {
		StringBuilder loc = new StringBuilder();
		if (city != null && !city.isEmpty()) {
			candLocation = loc.append(city).append(", ").append(state).append(", ").append(country).toString();
		} else if (state != null && !state.isEmpty() && city == null) {
			candLocation = loc.append(state).append(", ").append(country).toString();
		} else {
			candLocation = loc.append(country).toString();
		}
		return candLocation;
	}

	public void setCandLocation(String candLocation) {
		this.candLocation = candLocation;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getVenSpocId() {
		return venSpocId;
	}

	public void setVenSpocId(Long venSpocId) {
		this.venSpocId = venSpocId;
	}

	public String getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(String commentsCount) {
		this.commentsCount = commentsCount;
	}

	public CandidateGlobal getParentCandidateId() {
		return parentCandidateId;
	}

	public void setParentCandidateId(CandidateGlobal parentCandidateId) {
		this.parentCandidateId = parentCandidateId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getIsCandidateSubmitted() {
		return isCandidateSubmitted;
	}

	public void setIsCandidateSubmitted(String isCandidateSubmitted) {
		this.isCandidateSubmitted = isCandidateSubmitted;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDateofBirthString() {
		return dateofBirthString;
	}

	public void setDateofBirthString(String dateofBirthString) {
		this.dateofBirthString = dateofBirthString;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Date getResidenceStartDate() {
		return residenceStartDate;
	}

	public void setResidenceStartDate(Date residenceStartDate) {
		this.residenceStartDate = residenceStartDate;
	}

	public Date getResidenceEndDate() {
		return residenceEndDate;
	}

	public void setResidenceEndDate(Date residenceEndDate) {
		this.residenceEndDate = residenceEndDate;
	}
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public String getCandidateTags() {
		return candidateTags;
	}

	public void setCandidateTags(String candidateTags) {
		this.candidateTags = candidateTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public Set<String> getCandidateTagsList() {
		return candidateTagsList;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public void setCandidateTagsList(Set<String> candidateTagsList) {
		this.candidateTagsList = candidateTagsList;
	}

	public String getResidenceAddress()
	{
		return residenceAddress;
	}

	public String getResidenceStreetAddress()
	{
		return residenceStreetAddress;
	}

	public String getResidenceCountry()
	{
		return residenceCountry;
	}

	public String getResidenceState()
	{
		return residenceState;
	}

	public String getResidenceCity()
	{
		return residenceCity;
	}

	public String getResidenceZipCode()
	{
		return residenceZipCode;
	}

	public String getResidenceStateCode()
	{
		return residenceStateCode;
	}

	public String getResidenceLatitude()
	{
		return residenceLatitude;
	}

	public String getResidenceLongitude()
	{
		return residenceLongitude;
	}

	public void setResidenceAddress(String residenceAddress)
	{
		this.residenceAddress = residenceAddress;
	}

	public void setResidenceStreetAddress(String residenceStreetAddress)
	{
		this.residenceStreetAddress = residenceStreetAddress;
	}

	public void setResidenceCountry(String residenceCountry)
	{
		this.residenceCountry = residenceCountry;
	}

	public void setResidenceState(String residenceState)
	{
		this.residenceState = residenceState;
	}

	public void setResidenceCity(String residenceCity)
	{
		this.residenceCity = residenceCity;
	}

	public void setResidenceZipCode(String residenceZipCode)
	{
		this.residenceZipCode = residenceZipCode;
	}

	public void setResidenceStateCode(String residenceStateCode)
	{
		this.residenceStateCode = residenceStateCode;
	}

	public void setResidenceLatitude(String residenceLatitude)
	{
		this.residenceLatitude = residenceLatitude;
	}

	public void setResidenceLongitude(String residenceLongitude)
	{
		this.residenceLongitude = residenceLongitude;
	}

	public OrgLocations getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(OrgLocations workLocation) {
		this.workLocation = workLocation;
	}

	public Integer getCandidateProfilePercentage() {
		return candidateProfilePercentage;
	}

	public void setCandidateProfilePercentage(Integer candidateProfilePercentage) {
		this.candidateProfilePercentage = candidateProfilePercentage;
	}

	public Set<CandidateHistory> getCandidateHistory() {
		return candidateHistory;
	}

	public void setCandidateHistory(Set<CandidateHistory> candidateHistory) {
		this.candidateHistory = candidateHistory;
	}

	public Set<CandidateTimelineActivity> getCandidateTimeLineActivity() {
		return candidateTimeLineActivity;
	}

	public void setCandidateTimeLineActivity(Set<CandidateTimelineActivity> candidateTimeLineActivity) {
		this.candidateTimeLineActivity = candidateTimeLineActivity;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getCandidateAssignedJobsCount() {
		return candidateAssignedJobsCount;
	}

	public void setCandidateAssignedJobsCount(String candidateAssignedJobsCount) {
		this.candidateAssignedJobsCount = candidateAssignedJobsCount;
	}

	public Set<CandidateCertifications> getCandidateCertifications() {
		return candidateCertifications;
	}

	public void setCandidateCertifications(Set<CandidateCertifications> candidateCertifications) {
		this.candidateCertifications = candidateCertifications;
	}

	public Long getMasterCandId() {
		return masterCandId;
	}

	public void setMasterCandId(Long masterCandId) {
		this.masterCandId = masterCandId;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getCandidateSource() {
		return candidateSource;
	}

	public void setCandidateSource(String candidateSource) {
		this.candidateSource = candidateSource;
	}
	
}
