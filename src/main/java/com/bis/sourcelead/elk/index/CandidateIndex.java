package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.ArrayList;

public class CandidateIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long candidateId;
	
	private Long userId;
	
	private Long masterCandidateId;

	private Long orgLocationId;

	private String firstName;

	private String lastName;
	
	private String fullName;

	private String email;

	private String alternateEmail;

	private String alternateMobilePhone;

	private String mobilePhone;

	private String country;

	private String state;

	private String city;
	
	private String location;

	private String zipCode;
	
	private String latitude;
	
	private String longitude;
	
	private String address;

	private Integer experienceYears;

	private Integer experienceMonths;

	private String designation;

	private String designationType;
	
	private String designationOtherType;

	private String currentRate;

	private String currentRateType;

	private String currentRateCurrency;

	private Float expectedRate;

	private String rateType;

	private String expectedRateCurrencey;

	private String summary;
	
	
	private String[] visaType;

	private String invitationstatus;

	private String terminateReason;
	
	private String skillsName ;
	
	private String primarySkill ;

	private ArrayList<String> education;

	/*private ArrayList<String> skills;*/

	private ArrayList<String> employer;

	private ArrayList<String> certifications;

	private String candidateType;

	private Integer status;

	private String createdDate;

	private String createdBy;
	
	private String createdByUserCode;

	private String updatedDate;

	private String updatedBy;
	
    private String noticePriod;
	
	private Long currentResumeId;
	
	private String currentResumeName;
	
	private String resumeContent;

	private Long teamId;
	
	private Integer isFavourite;
	
	private Integer isFavouriteAssociate;
	
	private String candidateName;
	
	private String candidateUpdateDateToString;
	
	private String totalExp;
	
	private String skillSet;
	
	private String searchType;
	
	private String candidateScore;
	
	private float skillPercentage;
	
	private float visaTypePercentage;
	
	private float educationPercentage;
	 
	private float expPercentage;
	 
	private float locationPercentage;
	 
	private float ratePercentage;
	 
	private float noticePeriodPercentage;
	 
	private float totalKeyWordPerPercentage;
	
	private int noOfReq;
	
	private int totalExpPercentage;
	
	private int totalSkillPercentage;
	
	private int totalLocationPercentage;
	       
	private int totalRatePercentage;
	       
	private int totalNoticePeriodPercentage;
	
	private int totalVisaTypePercentage;
	
	private int totalEducationPercentage;
	       
	private int keyWordPerPercentage;
	
	private String reqSubmissionId;
	
	private boolean isCandidateAdedd;
	
	private Long recruiterUserId;
	
	private double finalScore;
	
	private String isProfilePublic;
	
	private String isCandidateSubmitted;
	
	private Long orgRelationId;
	
	private Long spocId;

	private String candidateUUID;
		
	private String isAssociate;
	
	private String isSharedInMarketplace;
	
	private int profilePercentage;
	
	private String currentResumeUUID;
	
	private String generatedId;
	
	private String fileUrl;
	
	private String fileContentType;
	
	private String action;
	
	private String resumeName;
	
	private String middleName;
	
	private String gender;
	
	private String maritalStatus;
	
	private String dateofBirth;
	
	private String panNumber;
	
	private String aadharNumber;
	
	private String tags;
	
	private Integer submittedJobsCount;
	
	private Integer candidateProfilePercentage;
	
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Integer getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(Integer experienceMonths) {
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

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}

	public Float getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(Float expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
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

	public String getInvitationstatus() {
		return invitationstatus;
	}

	public void setInvitationstatus(String invitationstatus) {
		this.invitationstatus = invitationstatus;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public ArrayList<String> getEducation() {
		return education;
	}

	public void setEducation(ArrayList<String> education) {
		this.education = education;
	}

	/*public ArrayList<String> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}*/

	public ArrayList<String> getEmployer() {
		return employer;
	}

	public void setEmployer(ArrayList<String> employer) {
		this.employer = employer;
	}

	public ArrayList<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(ArrayList<String> certifications) {
		this.certifications = certifications;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getNoticePriod() {
		return noticePriod;
	}

	public void setNoticePriod(String noticePriod) {
		this.noticePriod = noticePriod;
	}

	public Long getCurrentResumeId() {
		return currentResumeId;
	}

	public void setCurrentResumeId(Long currentResumeId) {
		this.currentResumeId = currentResumeId;
	}

	public String getCurrentResumeName() {
		return currentResumeName;
	}

	public void setCurrentResumeName(String currentResumeName) {
		this.currentResumeName = currentResumeName;
	}

	public String getResumeContent() {
		return resumeContent;
	}

	public void setResumeContent(String resumeContent) {
		this.resumeContent = resumeContent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Integer getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Integer isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getCandidateUpdateDateToString() {
		return candidateUpdateDateToString;
	}

	public void setCandidateUpdateDateToString(String candidateUpdateDateToString) {
		this.candidateUpdateDateToString = candidateUpdateDateToString;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}
	public String getCandidateScore() {
		return candidateScore;
	}

	public void setCandidateScore(String candidateScore) {
		this.candidateScore = candidateScore;
	}

	public float getSkillPercentage() {
		return skillPercentage;
	}

	public void setSkillPercentage(float skillPercentage) {
		this.skillPercentage = skillPercentage;
	}
	
	public float getVisaTypePercentage() {
		return visaTypePercentage;
	}

	public void setVisaTypePercentage(float visaTypePercentage) {
		this.visaTypePercentage = visaTypePercentage;
	}

	public float getEducationPercentage() {
		return educationPercentage;
	}

	public void setEducationPercentage(float educationPercentage) {
		this.educationPercentage = educationPercentage;
	}

	public int getTotalVisaTypePercentage() {
		return totalVisaTypePercentage;
	}

	public void setTotalVisaTypePercentage(int totalVisaTypePercentage) {
		this.totalVisaTypePercentage = totalVisaTypePercentage;
	}

	public int getTotalEducationPercentage() {
		return totalEducationPercentage;
	}

	public void setTotalEducationPercentage(int totalEducationPercentage) {
		this.totalEducationPercentage = totalEducationPercentage;
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

	public int getNoOfReq() {
		return noOfReq;
	}

	public void setNoOfReq(int noOfReq) {
		this.noOfReq = noOfReq;
	}

	public String getReqSubmissionId() {
		return reqSubmissionId;
	}

	public void setReqSubmissionId(String reqSubmissionId) {
		this.reqSubmissionId = reqSubmissionId;
	}

	public boolean getIsCandidateAdedd() {
		return isCandidateAdedd;
	}

	public void setIsCandidateAdedd(boolean isCandidateAdedd) {
		this.isCandidateAdedd = isCandidateAdedd;
	}
	
	public void setCandidateAdedd(boolean isCandidateAdedd) {
		this.isCandidateAdedd = isCandidateAdedd;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}
	
	

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}

	public Long getRecruiterUserId() {
		return recruiterUserId;
	}

	public void setRecruiterUserId(Long recruiterUserId) {
		this.recruiterUserId = recruiterUserId;
	}

	public int getTotalSkillPercentage() {
		return totalSkillPercentage;
	}

	public void setTotalSkillPercentage(int totalSkillPercentage) {
		this.totalSkillPercentage = totalSkillPercentage;
	}

	public int getTotalLocationPercentage() {
		return totalLocationPercentage;
	}

	public void setTotalLocationPercentage(int totalLocationPercentage) {
		this.totalLocationPercentage = totalLocationPercentage;
	}

	public int getTotalRatePercentage() {
		return totalRatePercentage;
	}

	public void setTotalRatePercentage(int totalRatePercentage) {
		this.totalRatePercentage = totalRatePercentage;
	}

	public int getTotalNoticePeriodPercentage() {
		return totalNoticePeriodPercentage;
	}

	public void setTotalNoticePeriodPercentage(int totalNoticePeriodPercentage) {
		this.totalNoticePeriodPercentage = totalNoticePeriodPercentage;
	}

	public int getKeyWordPerPercentage() {
		return keyWordPerPercentage;
	}

	public void setKeyWordPerPercentage(int keyWordPerPercentage) {
		this.keyWordPerPercentage = keyWordPerPercentage;
	}

	public int getTotalExpPercentage() {
		return totalExpPercentage;
	}

	public void setTotalExpPercentage(int totalExpPercentage) {
		this.totalExpPercentage = totalExpPercentage;
	}

	public String getIsProfilePublic() {
		return isProfilePublic;
	}

	public void setIsProfilePublic(String isProfilePublic) {
		this.isProfilePublic = isProfilePublic;
	}

	public String getIsCandidateSubmitted() {
		return isCandidateSubmitted;
	}

	public void setIsCandidateSubmitted(String isCandidateSubmitted) {
		this.isCandidateSubmitted = isCandidateSubmitted;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public Long getSpocId() {
		return spocId;
	}

	public void setSpocId(Long spocId) {
		this.spocId = spocId;
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

	public String[] getVisaType() {
		return visaType;
	}

	public void setVisaType(String[] visaType) {
		this.visaType = visaType;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Long getMasterCandidateId()
	{
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId)
	{
		this.masterCandidateId = masterCandidateId;
	}

	public int getProfilePercentage()
	{
		return profilePercentage;
	}

	public void setProfilePercentage(int profilePercentage)
	{
		this.profilePercentage = profilePercentage;
	}

	public String getCurrentResumeUUID()
	{
		return currentResumeUUID;
	}

	public void setCurrentResumeUUID(String currentResumeUUID)
	{
		this.currentResumeUUID = currentResumeUUID;
	}

	public String getGeneratedId()
	{
		return generatedId;
	}

	public void setGeneratedId(String generatedId)
	{
		this.generatedId = generatedId;
	}

	public String getFileUrl()
	{
		return fileUrl;
	}

	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
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

	public String getCreatedByUserCode() {
		return createdByUserCode;
	}

	public void setCreatedByUserCode(String createdByUserCode) {
		this.createdByUserCode = createdByUserCode;

	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getSubmittedJobsCount() {
		return submittedJobsCount;
	}

	public void setSubmittedJobsCount(Integer submittedJobsCount) {
		this.submittedJobsCount = submittedJobsCount;
	}

	public Integer getCandidateProfilePercentage() {
		return candidateProfilePercentage;
	}

	public void setCandidateProfilePercentage(Integer candidateProfilePercentage) {
		this.candidateProfilePercentage = candidateProfilePercentage;
	}

	
	
}

