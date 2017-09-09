package com.bis.sourcelead.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Phone;
import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;

public class CandidateDTO {

	private String candidateId;
	
	private String staticPath;

	//private MasterCandidate masterCandidateId;

	private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;

	//private OrgLocations orgLocationId;

	private String consultantUser;

	//private Org orgId;

	//private User recruiterUserId;

	private String candidateType;
	
	@Required(desc = "first name")
	private String firstName;

	private String lastName;

	@Required(desc = "email")
	@Email
	private String email;

	@Email
	private String alternateEmail;

	@Phone
	private String alternateMobilePhone;

	@Phone
	private String mobilePhone;

	@Required(desc = "country")
	private String country;

	private String state;

	private String city;

	private String address;

	private String location;

	private String zipCode;

	private String latitude;

	private String longitude;

	private String experienceYears;

	private String experienceMonths;

	private String noticePeriod;

	private String designation;

	private String designationType;

	private String designationOtherType;

	private String currentRate;

	private String currentRateType;

	private String currentRateCurrency;

	private String expectedRate;

	private String rateType;

	private String expectedRateCurrencey;

	private String summary;

	private String visaType;

	private String fileContentType;

	private String fileName;

	private String invitationstatus;

	private String status;

	//private Team teamId;

	private String isFavourite;

	private String isFavouriteAssociate;

	private String terminateReason;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;

	private String isProfilePublic;

	private String candidateUUID;

	private String isAssociate;

	//private OrgRelations orgRelationId;

	//private OrgSpoc spocId;

	private String skillsName;

	private String facebookUrl;

	private String primarySkill;

	private String linkedUrl;

	private String twitterUrl;
	
	private String candidateSkills;
	
	private String vendors;
	
	private String vendorcontact;
	
	private String inviteEmail;
	
	private String phoneNumber;
	
	private String visa;
	
	private String associateId;
	
	private String projectId;
	
	private String isMailSent;
	
	private String userEmail;
	
	private String EmailId;
	
	private String candidateEducation;
	
	private String candidateEmployers;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	/*public MasterCandidate getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(MasterCandidate masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}*/

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	/*public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}*/

	public String getConsultantUser() {
		return consultantUser;
	}

	public void setConsultantUser(String consultantUser) {
		this.consultantUser = consultantUser;
	}

	/*public Org getOrgId() {
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
	}*/

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
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

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
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

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
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

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

/*	public Team getTeamId() {
		return teamId;
	}

	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}
*/
	public String getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getIsFavouriteAssociate() {
		return isFavouriteAssociate;
	}

	public void setIsFavouriteAssociate(String isFavouriteAssociate) {
		this.isFavouriteAssociate = isFavouriteAssociate;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
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

	public String getIsProfilePublic() {
		return isProfilePublic;
	}

	public void setIsProfilePublic(String isProfilePublic) {
		this.isProfilePublic = isProfilePublic;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getIsAssociate() {
		return isAssociate;
	}

	public void setIsAssociate(String isAssociate) {
		this.isAssociate = isAssociate;
	}

	/*public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public OrgSpoc getSpocId() {
		return spocId;
	}

	public void setSpocId(OrgSpoc spocId) {
		this.spocId = spocId;
	}*/

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
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

	public String getCandidateSkills() {
		return candidateSkills;
	}

	public void setCandidateSkills(String candidateSkills) {
		this.candidateSkills = candidateSkills;
	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	public String getVendorcontact() {
		return vendorcontact;
	}

	public void setVendorcontact(String vendorcontact) {
		this.vendorcontact = vendorcontact;
	}

	public String getInviteEmail() {
		return inviteEmail;
	}

	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVisa() {
		return visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public String getCandidateEducation() {
		return candidateEducation;
	}

	public void setCandidateEducation(String candidateEducation) {
		this.candidateEducation = candidateEducation;
	}

	public String getCandidateEmployers() {
		return candidateEmployers;
	}

	public void setCandidateEmployers(String candidateEmployers) {
		this.candidateEmployers = candidateEmployers;
	}

	
	
}
