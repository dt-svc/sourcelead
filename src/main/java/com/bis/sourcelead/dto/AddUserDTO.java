package com.bis.sourcelead.dto;

	import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;

	public class AddUserDTO {

		private Long id;
		
		private String firstName;
		
		private String lastName;
		
		private String email;
		
		private String workPhone;
		
		private String mobilePhone;
		
		private Status userState;
		
		private String userType;
		
		private String userName;
		
		private String password;
		
		private String userCode;
		
		private String status;
		
		private Long reportingManager;
		
		private Status isMailSent;
		
		private String jobTitle;
		
		private String location;
		
		private String linkedIn;

		private String facebook;
		
		private String twitter;
		
		private String googlePlus;
		
		private Date createdDate;
		
		private String createdBy;
		
		private Date updatedDate;
		
		private String updatedBy;
		
		private String userUUID;
		
		private String professionalRegisterCountry;
		
		private String recruiterRegisterCountry;
		
		private Set<UserRole> userRole = new HashSet<UserRole>(0);
		
		private Set<Employees> employee = new HashSet<Employees>(0);
		
		/*@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	    @Fetch(FetchMode.SELECT)
	    private Set<PasswordResets> passwordResets;*/
		
		private Set<Candidate> candidateSet;
		
		private String fileContentType;
		
		private String terminateReason;
		
		private Status previousUserState;
		
		private String country;
		
		private String registerOptionalServiceId;

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

		public Long getReportingManager() {
			return reportingManager;
		}

		public void setReportingManager(Long reportingManager) {
			this.reportingManager = reportingManager;
		}

		public Status getIsMailSent() {
			return isMailSent;
		}

		public void setIsMailSent(Status isMailSent) {
			this.isMailSent = isMailSent;
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

		public String getUserUUID() {
			return userUUID;
		}

		public void setUserUUID(String userUUID) {
			this.userUUID = userUUID;
		}

		public String getProfessionalRegisterCountry() {
			return professionalRegisterCountry;
		}

		public void setProfessionalRegisterCountry(String professionalRegisterCountry) {
			this.professionalRegisterCountry = professionalRegisterCountry;
		}

		public String getRecruiterRegisterCountry() {
			return recruiterRegisterCountry;
		}

		public void setRecruiterRegisterCountry(String recruiterRegisterCountry) {
			this.recruiterRegisterCountry = recruiterRegisterCountry;
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

		public Set<Candidate> getCandidateSet() {
			return candidateSet;
		}

		public void setCandidateSet(Set<Candidate> candidateSet) {
			this.candidateSet = candidateSet;
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

		public Status getPreviousUserState() {
			return previousUserState;
		}

		public void setPreviousUserState(Status previousUserState) {
			this.previousUserState = previousUserState;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getRegisterOptionalServiceId() {
			return registerOptionalServiceId;
		}

		public void setRegisterOptionalServiceId(String registerOptionalServiceId) {
			this.registerOptionalServiceId = registerOptionalServiceId;
		}
		
		
	
}
