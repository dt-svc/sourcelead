package com.bis.sourcelead.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "WORK_PHONE")
	private String workPhone;
	
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	
    @Column(name = "EXTENSION_NUMBER")
	private String extensionNumber;

	@Column(name = "USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status userState;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@Column(name = "USER_NAME",unique = true, nullable = false)
	private String userName;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "USER_CODE",unique = true, nullable = false)
	private String userCode;
	
	@Column(name = "STATUS")
	private String status;
	
	
	@Column(name = "SSN")
	private String ssn;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTING_MANAGER_ID")
	private User reportingManager;
	
	/*@Column(name = "IS_MAIL_SENT")
	@Enumerated(EnumType.STRING)
	private Status isMailSent;*/
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "LOCATION")
	@Type(type="text")
	private String location;
	
	@Column(name = "LINKEDIN")
	@Type(type="text")
	private String linkedIn;
	
	
	@Column(name = "FACEBOOK")
	@Type(type="text")
	private String facebook;
	
	@Column(name = "TWITTER")
	@Type(type="text")
	private String twitter;
	
	@Column(name = "GOOGLE_PLUS")
	@Type(type="text")
	private String googlePlus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "USER_UUID")
	private String userUUID;
	
	@Column(name = "PROFESSIONAL_REGISTER_COUNTRY")
	private String professionalRegisterCountry;
	
	@Column(name = "RECRUITER_REGISTER_COUNTRY")
	private String recruiterRegisterCountry;
	
	// Immigration columns Start
	@Column(name = "MIDDLE_NAME")
	private String middelName ;
		
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
		
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINK_USER_ID")
	@JsonIgnore
	private User linkUserId;
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<Employees> employee = new HashSet<Employees>(0);
	
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<PasswordResets> passwordResets;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "consultantUser")
	private Set<Candidate> candidateSet;
	
	public Set<Candidate> getCandidateSet() {
		return candidateSet;
	}

	public void setCandidateSet(Set<Candidate> candidateSet) {
		this.candidateSet = candidateSet;
	}

	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "TERMINATE_REASON", columnDefinition = "TEXT default NULL")
	private String terminateReason;
	
	@Column(name = "PREVIOUS_USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status previousUserState;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name ="REGISTER_OPTIONAL_SERVICE_ID")
	private String registerOptionalServiceId;
	
	@Column(name = "VERIFICATION_UUID")
	private String verificationUUID;
    
    @Column(name = "IS_VERIFIED")
	private String isVerified;
    
    @Column(name = "URL_SENTTIME")
	private Date urlSentTime;
    
    @Column(name = "LOGIN_SECURITY")
	private String loginSecurity;
    
    @Column(name = "ALLOW_EMPLOYEE_TO_FILL")
	@Enumerated(EnumType.STRING)
	private Status allowEmployeeToFill;
	
	@Transient
	private String isPrimaryAdmin;
	
	@Transient
	private String locationCode;
	
	@Transient
	private Long orgLocationId;

	@Transient
    private String tempPwd;
	
	@Transient
	@JsonProperty
    private int assignJobCount;
	
	@Transient
	private String role;
	
	@Transient
	@JsonProperty
	private String assignedTeamId;
	
	@Transient
	@JsonProperty
	private String assignedTeam;
	
	@Transient
	@JsonProperty
	private String isAssignedOwner;
	
	@Transient
    private long userRequirementsCount;
	
	@Transient
    private long userApplicantsCount;
	
	@Transient
    private long userCandidatesCount;
	
	@Transient
    private long userShortlisteApplicantsCount;
	
	@Transient
    private long userSubmittedApplicantsCount;
	
	@Transient
    private long userInterviewedApplicantsCount;
	
	@Transient
    private long userOfferedApplicantsCount;
	
	@Transient
    private long userRejectedApplicantsCount;
	
	@Transient
    private long userApplicantsBackOutCount;
	
	@Transient
	private String dateOfJoin;
	
	@Transient
	private String noticePeriod;
	
	@Transient
	private String employmentType;
	
	@Transient
	private String vendorName;
	
	@Transient
	private String vendorContactName;
	
	@Transient
	private String registrationType;
	
	@Transient
	private String isMailSent;
	
	@Transient
	private String associateUserCode;
	
	@Transient
	private Long userId;
	
	@Transient
	private Long candidateId;
	
	@Transient
    @JsonProperty
    private String associateTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;
	
	
	@Transient
    @JsonProperty
    private String isPayRollApprover;
	

	public Status getPreviousUserState() {
		return previousUserState;
	}

	public void setPreviousUserState(Status previousUserState) {
		this.previousUserState = previousUserState;
	}

	@Transient
	@JsonProperty
    private String fullName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getExtensionNumber()
	{
		return extensionNumber;
	}

	public void setExtensionNumber(String extensionNumber)
	{
		this.extensionNumber = extensionNumber;
	}

	public Status getUserState() {
		return userState;
	}

	public void setUserState(Status userState) {
		this.userState = userState;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	
	public Set<Employees> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<Employees> employee) {
		this.employee = employee;
	}

	public Set<PasswordResets> getPasswordResets() {
		return passwordResets;
	}

	public void setPasswordResets(Set<PasswordResets> passwordResets) {
		this.passwordResets = passwordResets;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTempPwd() {
		return tempPwd;
	}

	public void setTempPwd(String tempPwd) {
		this.tempPwd = tempPwd;
	}

	public String getFullName() {
		if(StringUtils.isNotEmpty(fullName))
			return fullName;
		else{
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

	public User getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(User reportingManager) {
		this.reportingManager = reportingManager;
	}

	public int getAssignJobCount() {
		return assignJobCount;
	}

	public void setAssignJobCount(int assignJobCount) {
		this.assignJobCount = assignJobCount;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}
	
	public String getIsPrimaryAdmin() {
		return isPrimaryAdmin;
	}

	public void setIsPrimaryAdmin(String isPrimaryAdmin) {
		this.isPrimaryAdmin = isPrimaryAdmin;
	}

	public String getAssignedTeamId() {
		return assignedTeamId;
	}

	public void setAssignedTeamId(String assignedTeamId) {
		this.assignedTeamId = assignedTeamId;
	}

	public String getAssignedTeam() {
		return assignedTeam;
	}

	public void setAssignedTeam(String assignedTeam) {
		this.assignedTeam = assignedTeam;
	}

	public String getIsAssignedOwner() {
		return isAssignedOwner;
	}

	public void setIsAssignedOwner(String isAssignedOwner) {
		this.isAssignedOwner = isAssignedOwner;
	}

	public long getUserRequirementsCount() {
		return userRequirementsCount;
	}

	public void setUserRequirementsCount(long userRequirementsCount) {
		this.userRequirementsCount = userRequirementsCount;
	}

	public long getUserCandidatesCount() {
		return userCandidatesCount;
	}

	public void setUserCandidatesCount(long userCandidatesCount) {
		this.userCandidatesCount = userCandidatesCount;
	}

	public long getUserApplicantsCount() {
		return userApplicantsCount;
	}

	public void setUserApplicantsCount(long userApplicantsCount) {
		this.userApplicantsCount = userApplicantsCount;
	}

	public long getUserShortlisteApplicantsCount() {
		return userShortlisteApplicantsCount;
	}

	public void setUserShortlisteApplicantsCount(long userShortlisteApplicantsCount) {
		this.userShortlisteApplicantsCount = userShortlisteApplicantsCount;
	}

	public long getUserSubmittedApplicantsCount() {
		return userSubmittedApplicantsCount;
	}

	public void setUserSubmittedApplicantsCount(long userSubmittedApplicantsCount) {
		this.userSubmittedApplicantsCount = userSubmittedApplicantsCount;
	}

	public long getUserInterviewedApplicantsCount() {
		return userInterviewedApplicantsCount;
	}

	public void setUserInterviewedApplicantsCount(long userInterviewedApplicantsCount) {
		this.userInterviewedApplicantsCount = userInterviewedApplicantsCount;
	}

	public long getUserOfferedApplicantsCount() {
		return userOfferedApplicantsCount;
	}

	public void setUserOfferedApplicantsCount(long userOfferedApplicantsCount) {
		this.userOfferedApplicantsCount = userOfferedApplicantsCount;
	}

	public long getUserRejectedApplicantsCount() {
		return userRejectedApplicantsCount;
	}

	public void setUserRejectedApplicantsCount(long userRejectedApplicantsCount) {
		this.userRejectedApplicantsCount = userRejectedApplicantsCount;
	}

	public String getRegisterOptionalServiceId() {
		return registerOptionalServiceId;
	}

	public void setRegisterOptionalServiceId(String registerOptionalServiceId) {
		this.registerOptionalServiceId = registerOptionalServiceId;
	}

	public String getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(String dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public long getUserApplicantsBackOutCount() {
		return userApplicantsBackOutCount;
	}

	public void setUserApplicantsBackOutCount(long userApplicantsBackOutCount) {
		this.userApplicantsBackOutCount = userApplicantsBackOutCount;
	}

	public String getEmploymentType()
	{
		return employmentType;
	}

	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}

	public String getRegistrationType()
	{
		return registrationType;
	}

	public void setRegistrationType(String registrationType)
	{
		this.registrationType = registrationType;
	}

	public String getProfessionalRegisterCountry()
	{
		return professionalRegisterCountry;
	}

	public void setProfessionalRegisterCountry(String professionalRegisterCountry)
	{
		this.professionalRegisterCountry = professionalRegisterCountry;
	}

	public String getRecruiterRegisterCountry()
	{
		return recruiterRegisterCountry;
	}

	public void setRecruiterRegisterCountry(String recruiterRegisterCountry)
	{
		this.recruiterRegisterCountry = recruiterRegisterCountry;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getSsn()
	{
		return ssn;
	}

	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}

	public String getVerificationUUID()
	{
		return verificationUUID;
	}

	public void setVerificationUUID(String verificationUUID)
	{
		this.verificationUUID = verificationUUID;
	}

	public String getIsVerified()
	{
		return isVerified;
	}

	public void setIsVerified(String isVerified)
	{
		this.isVerified = isVerified;
	}

	public Date getUrlSentTime()
	{
		return urlSentTime;
	}

	public void setUrlSentTime(Date urlSentTime)
	{
		this.urlSentTime = urlSentTime;
	}

	public String getLoginSecurity()
	{
		return loginSecurity;
	}

	public void setLoginSecurity(String loginSecurity)
	{
		this.loginSecurity = loginSecurity;
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

	public User getLinkUserId() {
		return linkUserId;
	}

	public void setLinkUserId(User linkUserId) {
		this.linkUserId = linkUserId;
	}

	public String getAssociateUserCode() {
		return associateUserCode;
	}

	public void setAssociateUserCode(String associateUserCode) {
		this.associateUserCode = associateUserCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAssociateTags() {
		return associateTags;
	}

	public void setAssociateTags(String associateTags) {
		this.associateTags = associateTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getIsPayRollApprover() {
		return isPayRollApprover;
	}

	public void setIsPayRollApprover(String isPayRollApprover) {
		this.isPayRollApprover = isPayRollApprover;
	}

	public Status getAllowEmployeeToFill() {
		return allowEmployeeToFill;
	}

	public void setAllowEmployeeToFill(Status allowEmployeeToFill) {
		this.allowEmployeeToFill = allowEmployeeToFill;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	
}
