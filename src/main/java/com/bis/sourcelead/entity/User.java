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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "user")
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
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REPORTING_MANAGER_ID")
	private User reportingManager;
	
	@Column(name = "IS_MAIL_SENT")
	@Enumerated(EnumType.STRING)
	private Status isMailSent;
	
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
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<Employees> employee = new HashSet<Employees>(0);
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<PasswordResets> passwordResets;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "consultantUser")
	private Set<Candidate> candidateSet;
	
	public Set<Candidate> getCandidateSet() {
		return candidateSet;
	}

	public void setCandidateSet(Set<Candidate> candidateSet) {
		this.candidateSet = candidateSet;
	}

	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
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
	
	@Transient
	private String isPrimaryAdmin;

	@Transient
    private String tempPwd;
	
	@Transient
    private int assignJobCount;
	
	@Transient
	private String role;
	
	@Transient
	private String assignedTeamId;
	
	@Transient
	private String assignedTeam;
	
	@Transient
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
	private String registrationType;
	
	

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
	
	public Status getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Status isMailSent) {
		this.isMailSent = isMailSent;
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
			if(StringUtils.isNotEmpty(firstName) && StringUtils.isEmpty(lastName)){
				fullName = firstName;
			}
			else if(StringUtils.isEmpty(firstName) && StringUtils.isNotEmpty(lastName)){
				fullName = lastName;
			}
			else if(StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)){
				fullName = firstName+" "+lastName;
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

	
}
