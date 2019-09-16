package com.bis.sourcelead.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "CANDIDATE_HISTORY")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateHistory")
public class CandidateHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_GLOBAL_ID", nullable = false)
	private CandidateGlobal candidateGlobalId;
	
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
	@Column(name = "IS_PROFILE_PUBLIC")
	private String isProfilePublic;
	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;
	@Column(name = "IS_ASSOCIATE")
	private String isAssociate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	@Transient
	private String skills;
	@Transient
	private String totalExp;
	@Transient
	private String rate;
	@Transient
	private String candidateName;
	@Transient
	private String candidateLocation;
	@Column(name = "SKILLS_NAME", columnDefinition = "TEXT default NULL")
	private String skillsName;
	@Column(name = "FACEBOOK_PROFILE_URL")
	private String facebookUrl;
	@Column(name = "PRIMARY_SKILL")
	private String primarySkill;
	@Column(name = "LINKEDIN_PROFILE_URL")
	private String linkedUrl;
	@Column(name = "TWITTER_PROFILE_URL")
	private String twitterUrl;
	
	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateEducation> candidateEducation = new HashSet<CandidateEducation>(0);
	
	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateResumes> candidateResumes = new HashSet<CandidateResumes>(0);
	
	@OneToMany(mappedBy = "candidateId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<CandidateEmployers> candidateEmployers = new HashSet<CandidateEmployers>(0);
	@Transient
	private String candidateEdu;
	@Transient
	private String skillsList;
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
	private float skillPercentage;
	@Transient
	private float expPercentage;
	@Transient
	private float locationPercentage;
	@Transient
	private float ratePercentage;
	@Transient
	private float noticePeriodPercentage;
	@Transient
	private float totalKeyWordPerPercentage;
	@Transient
	private Long currentResumeId;
	@Transient
	private String linkedIn;
	@Transient
	private String facebook;
	@Transient
	private String twitter;
	@Transient
	private String googlePlus;
	@Transient
	private String postedDateStr;
	@Transient
	private String vendorName;
	@Transient
	private String vendorContactName;
	@Transient
	private Integer assignedProjectsCount;
	@Transient
	private String vendorOrgRelationId;
	@Transient
	private String employeList;
	@Transient
	private String vendorSpocId;
	@Transient
	private List<RequirementSubmission> requirementSubmisstions;
	@Transient
	private LinkedHashSet<String> allAndCandidateSkills;
	@Transient
	private LinkedHashSet<String> allAndCandidateEducation;
	@Transient
	private Integer candidateRequiementCount;
	@Transient
	private List<String> candidateSkillsList;
	@Transient
	private String employer;
	@Transient
	private String sowContractName;
	@Transient
	private LinkedHashSet<String> allAndCandidateVisa;
	@Transient
	private String candidateResumeName;
	@Transient
	private String candidateResumeFileType;
	@Transient
	private List<Contracts> contractsList;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getAlternateEmail()
	{
		return alternateEmail;
	}
	
	public void setAlternateEmail(String alternateEmail)
	{
		this.alternateEmail = alternateEmail;
	}
	
	public String getAlternateMobilePhone()
	{
		return alternateMobilePhone;
	}
	
	public void setAlternateMobilePhone(String alternateMobilePhone)
	{
		this.alternateMobilePhone = alternateMobilePhone;
	}
	
	public String getMobilePhone()
	{
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public String getZipCode()
	{
		return zipCode;
	}
	
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	
	public String getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}
	
	public String getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	
	public String getExperienceYears()
	{
		return experienceYears;
	}
	
	public void setExperienceYears(String experienceYears)
	{
		this.experienceYears = experienceYears;
	}
	
	public String getExperienceMonths()
	{
		return experienceMonths;
	}
	
	public void setExperienceMonths(String experienceMonths)
	{
		this.experienceMonths = experienceMonths;
	}
	
	public String getDesignation()
	{
		return designation;
	}
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	public String getDesignationType()
	{
		return designationType;
	}
	
	public void setDesignationType(String designationType)
	{
		this.designationType = designationType;
	}
	
	public String getDesignationOtherType()
	{
		return designationOtherType;
	}
	
	public void setDesignationOtherType(String designationOtherType)
	{
		this.designationOtherType = designationOtherType;
	}
	
	public String getCurrentRate()
	{
		return currentRate;
	}
	
	public void setCurrentRate(String currentRate)
	{
		this.currentRate = currentRate;
	}
	
	public String getCurrentRateCurrency()
	{
		return currentRateCurrency;
	}
	
	public void setCurrentRateCurrency(String currentRateCurrency)
	{
		this.currentRateCurrency = currentRateCurrency;
	}
	
	public String getExpectedRate()
	{
		return expectedRate;
	}
	
	public void setExpectedRate(String expectedRate)
	{
		this.expectedRate = expectedRate;
	}
	
	public String getExpectedRateCurrencey()
	{
		return expectedRateCurrencey;
	}
	
	public void setExpectedRateCurrencey(String expectedRateCurrencey)
	{
		this.expectedRateCurrencey = expectedRateCurrencey;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public void setSummary(String summary)
	{
		this.summary = summary;
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
	
	public String getInvitationstatus()
	{
		return invitationstatus;
	}
	
	public void setInvitationstatus(String invitationstatus)
	{
		this.invitationstatus = invitationstatus;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public String getTerminateReason()
	{
		return terminateReason;
	}
	
	public void setTerminateReason(String terminateReason)
	{
		this.terminateReason = terminateReason;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public Set<CandidateEducation> getCandidateEducation()
	{
		return candidateEducation;
	}
	
	public void setCandidateEducation(Set<CandidateEducation> candidateEducation)
	{
		this.candidateEducation = candidateEducation;
	}
	
	public Set<CandidateResumes> getCandidateResumes()
	{
		return candidateResumes;
	}
	
	public void setCandidateResumes(Set<CandidateResumes> candidateResumes)
	{
		this.candidateResumes = candidateResumes;
	}
	
	public Set<CandidateEmployers> getCandidateEmployers()
	{
		return candidateEmployers;
	}
	
	public void setCandidateEmployers(Set<CandidateEmployers> candidateEmployers)
	{
		this.candidateEmployers = candidateEmployers;
	}
	
	public String getSkillsList()
	{
		return skillsList;
	}
	
	public void setSkillsList(String skillsList)
	{
		this.skillsList = skillsList;
	}
	
	public String getCandidateUpdateDateToString()
	{
		return candidateUpdateDateToString;
	}
	
	public void setCandidateUpdateDateToString(String candidateUpdateDateToString)
	{
		this.candidateUpdateDateToString = candidateUpdateDateToString;
	}
	
	public String getRateType()
	{
		return rateType;
	}
	
	public void setRateType(String rateType)
	{
		this.rateType = rateType;
	}
	
	public String getCandidateScore()
	{
		return candidateScore;
	}
	
	public void setCandidateScore(String candidateScore)
	{
		this.candidateScore = candidateScore;
	}
	
	public int getNoOfReq()
	{
		return noOfReq;
	}
	
	public void setNoOfReq(int noOfReq)
	{
		this.noOfReq = noOfReq;
	}
	
	public String getCandidateEdu()
	{
		return candidateEdu;
	}
	
	public void setCandidateEdu(String candidateEdu)
	{
		this.candidateEdu = candidateEdu;
	}
	
	public String getCandidateResume()
	{
		return candidateResume;
	}
	
	public void setCandidateResume(String candidateResume)
	{
		this.candidateResume = candidateResume;
	}
	
	public String getVendorOrgRelationId()
	{
		return vendorOrgRelationId;
	}
	
	public void setVendorOrgRelationId(String vendorOrgRelationId)
	{
		this.vendorOrgRelationId = vendorOrgRelationId;
	}
	
	public String getVendorSpocId()
	{
		return vendorSpocId;
	}
	
	public void setVendorSpocId(String vendorSpocId)
	{
		this.vendorSpocId = vendorSpocId;
	}
	
	public boolean getIsCandidateAdedd()
	{
		return isCandidateAdedd;
	}
	
	public void setIsCandidateAdedd(boolean isCandidateAdedd)
	{
		this.isCandidateAdedd = isCandidateAdedd;
	}
	
	public List<RequirementSubmission> getRequirementSubmisstions()
	{
		return requirementSubmisstions;
	}
	
	public void setRequirementSubmisstions(List<RequirementSubmission> requirementSubmisstions)
	{
		this.requirementSubmisstions = requirementSubmisstions;
	}
	
	public LinkedHashSet<String> getAllAndCandidateSkills()
	{
		return allAndCandidateSkills;
	}
	
	public void setAllAndCandidateSkills(LinkedHashSet<String> allAndCandidateSkills)
	{
		this.allAndCandidateSkills = allAndCandidateSkills;
	}
	
	public LinkedHashSet<String> getAllAndCandidateEducation()
	{
		return allAndCandidateEducation;
	}
	
	public void setAllAndCandidateEducation(LinkedHashSet<String> allAndCandidateEducation)
	{
		this.allAndCandidateEducation = allAndCandidateEducation;
	}
	
	public String getReqSubmissionId()
	{
		return reqSubmissionId;
	}
	
	public void setReqSubmissionId(String reqSubmissionId)
	{
		this.reqSubmissionId = reqSubmissionId;
	}
	
	public String getCurrentRateType()
	{
		return currentRateType;
	}
	
	public void setCurrentRateType(String currentRateType)
	{
		this.currentRateType = currentRateType;
	}
	
	public String getCandidateType()
	{
		return candidateType;
	}
	
	public void setCandidateType(String candidateType)
	{
		this.candidateType = candidateType;
	}
	
	public Integer getIsFavourite()
	{
		return isFavourite;
	}
	
	public void setIsFavourite(Integer isFavourite)
	{
		this.isFavourite = isFavourite;
	}
	
	public String getNoticePeriod()
	{
		return noticePeriod;
	}
	
	public void setNoticePeriod(String noticePeriod)
	{
		this.noticePeriod = noticePeriod;
	}
	
	public String getSkills()
	{
		return skills;
	}
	
	public void setSkills(String skills)
	{
		this.skills = skills;
	}
	
	public String getTotalExp()
	{
		return totalExp;
	}
	
	public void setTotalExp(String totalExp)
	{
		this.totalExp = totalExp;
	}
	
	public String getRate()
	{
		return rate;
	}
	
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	
	public String getCandidateName()
	{
		return candidateName;
	}
	
	public void setCandidateName(String candidateName)
	{
		this.candidateName = candidateName;
	}
	
	public void setCandidateAdedd(boolean isCandidateAdedd)
	{
		this.isCandidateAdedd = isCandidateAdedd;
	}
	
	public String getCandidateLocation()
	{
		return candidateLocation;
	}
	
	public void setCandidateLocation(String candidateLocation)
	{
		this.candidateLocation = candidateLocation;
	}
	
	public float getSkillPercentage()
	{
		return skillPercentage;
	}
	
	public void setSkillPercentage(float skillPercentage)
	{
		this.skillPercentage = skillPercentage;
	}
	
	public float getExpPercentage()
	{
		return expPercentage;
	}
	
	public void setExpPercentage(float expPercentage)
	{
		this.expPercentage = expPercentage;
	}
	
	public float getLocationPercentage()
	{
		return locationPercentage;
	}
	
	public void setLocationPercentage(float locationPercentage)
	{
		this.locationPercentage = locationPercentage;
	}
	
	public float getRatePercentage()
	{
		return ratePercentage;
	}
	
	public void setRatePercentage(float ratePercentage)
	{
		this.ratePercentage = ratePercentage;
	}
	
	public float getNoticePeriodPercentage()
	{
		return noticePeriodPercentage;
	}
	
	public void setNoticePeriodPercentage(float noticePeriodPercentage)
	{
		this.noticePeriodPercentage = noticePeriodPercentage;
	}
	
	public float getTotalKeyWordPerPercentage()
	{
		return totalKeyWordPerPercentage;
	}
	
	public void setTotalKeyWordPerPercentage(float totalKeyWordPerPercentage)
	{
		this.totalKeyWordPerPercentage = totalKeyWordPerPercentage;
	}
	
	public Long getCurrentResumeId()
	{
		return currentResumeId;
	}
	
	public void setCurrentResumeId(Long currentResumeId)
	{
		this.currentResumeId = currentResumeId;
	}
	
	public String getSkillsName()
	{
		return skillsName;
	}
	
	public void setSkillsName(String skillsName)
	{
		this.skillsName = skillsName;
	}
	
	public String getLinkedIn()
	{
		return linkedIn;
	}
	
	public void setLinkedIn(String linkedIn)
	{
		this.linkedIn = linkedIn;
	}
	
	public String getFacebook()
	{
		return facebook;
	}
	
	public void setFacebook(String facebook)
	{
		this.facebook = facebook;
	}
	
	public String getTwitter()
	{
		return twitter;
	}
	
	public void setTwitter(String twitter)
	{
		this.twitter = twitter;
	}
	
	public String getGooglePlus()
	{
		return googlePlus;
	}
	
	public void setGooglePlus(String googlePlus)
	{
		this.googlePlus = googlePlus;
	}
	
	public String getPrimarySkill()
	{
		return primarySkill;
	}
	
	public void setPrimarySkill(String primarySkill)
	{
		this.primarySkill = primarySkill;
	}
	
	public String getEmployeList()
	{
		return employeList;
	}
	
	public void setEmployeList(String employeList)
	{
		this.employeList = employeList;
	}
	
	public String getIsProfilePublic()
	{
		return isProfilePublic;
	}
	
	public void setIsProfilePublic(String isProfilePublic)
	{
		this.isProfilePublic = isProfilePublic;
	}
	
	public String getPostedDateStr()
	{
		return postedDateStr;
	}
	
	public void setPostedDateStr(String postedDateStr)
	{
		this.postedDateStr = postedDateStr;
	}
	
	public Integer getCandidateRequiementCount()
	{
		return candidateRequiementCount;
	}
	
	public void setCandidateRequiementCount(Integer candidateRequiementCount)
	{
		this.candidateRequiementCount = candidateRequiementCount;
	}
	
	public String getIsAssociate()
	{
		return isAssociate;
	}
	
	public void setIsAssociate(String isAssociate)
	{
		this.isAssociate = isAssociate;
	}
	
	public String getVendorName()
	{
		return vendorName;
	}
	
	public void setVendorName(String vendorName)
	{
		this.vendorName = vendorName;
	}
	
	public String getVendorContactName()
	{
		return vendorContactName;
	}
	
	public void setVendorContactName(String vendorContactName)
	{
		this.vendorContactName = vendorContactName;
	}
	
	public Integer getAssignedProjectsCount()
	{
		return assignedProjectsCount;
	}
	
	public void setAssignedProjectsCount(Integer assignedProjectsCount)
	{
		this.assignedProjectsCount = assignedProjectsCount;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
	
	public Integer getIsFavouriteAssociate()
	{
		return isFavouriteAssociate;
	}
	
	public void setIsFavouriteAssociate(Integer isFavouriteAssociate)
	{
		this.isFavouriteAssociate = isFavouriteAssociate;
	}
	
	public String getCandidateUUID()
	{
		return candidateUUID;
	}
	
	public void setCandidateUUID(String candidateUUID)
	{
		this.candidateUUID = candidateUUID;
	}
	
	public List<String> getCandidateSkillsList()
	{
		return candidateSkillsList;
	}
	
	public void setCandidateSkillsList(List<String> candidateSkillsList)
	{
		this.candidateSkillsList = candidateSkillsList;
	}
	
	public String getEmployer()
	{
		return employer;
	}
	
	public void setEmployer(String employer)
	{
		this.employer = employer;
	}
	
	public String getFacebookUrl()
	{
		return facebookUrl;
	}
	
	public void setFacebookUrl(String facebookUrl)
	{
		this.facebookUrl = facebookUrl;
	}
	
	public String getLinkedUrl()
	{
		return linkedUrl;
	}
	
	public void setLinkedUrl(String linkedUrl)
	{
		this.linkedUrl = linkedUrl;
	}
	
	public String getTwitterUrl()
	{
		return twitterUrl;
	}
	
	public void setTwitterUrl(String twitterUrl)
	{
		this.twitterUrl = twitterUrl;
	}
	
	public String getSowContractName()
	{
		return sowContractName;
	}
	
	public void setSowContractName(String sowContractName)
	{
		this.sowContractName = sowContractName;
	}
	
	public List<Contracts> getContractsList()
	{
		return contractsList;
	}
	
	public void setContractsList(List<Contracts> contractsList)
	{
		this.contractsList = contractsList;
	}
	
	public Candidate getCandidateId()
	{
		return candidateId;
	}
	
	public void setCandidateId(Candidate candidateId)
	{
		this.candidateId = candidateId;
	}
	
	public String getVisaType()
	{
		return visaType;
	}
	
	public void setVisaType(String visaType)
	{
		this.visaType = visaType;
	}
	
	public LinkedHashSet<String> getAllAndCandidateVisa()
	{
		return allAndCandidateVisa;
	}
	
	public void setAllAndCandidateVisa(LinkedHashSet<String> allAndCandidateVisa)
	{
		this.allAndCandidateVisa = allAndCandidateVisa;
	}
	
	public String getCandidateResumeName()
	{
		return candidateResumeName;
	}
	
	public void setCandidateResumeName(String candidateResumeName)
	{
		this.candidateResumeName = candidateResumeName;
	}
	
	public String getCandidateResumeFileType()
	{
		return candidateResumeFileType;
	}
	
	public void setCandidateResumeFileType(String candidateResumeFileType)
	{
		this.candidateResumeFileType = candidateResumeFileType;
	}

	public CandidateGlobal getCandidateGlobalId() {
		return candidateGlobalId;
	}

	public void setCandidateGlobalId(CandidateGlobal candidateGlobalId) {
		this.candidateGlobalId = candidateGlobalId;
	}
	
	
}
