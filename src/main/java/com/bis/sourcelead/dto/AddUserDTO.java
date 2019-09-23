package com.bis.sourcelead.dto;

	import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

    @JsonIgnoreProperties(ignoreUnknown=true)
	public class AddUserDTO extends CommonDTO {

		@Required(desc = "first name")
		private String firstName;
		
		private String lastName;
		
		@Required(desc = "email")
		@Email
		private String email;
		
		private String workPhone;
		
		private String userState;
		
		private String userType;
		
		private String userName;
		
		private String password;
		
		private String userCode;
		
		private String status;
		
		private String isGlobalAccess;
		
		private String grade;
		
		@Required(desc = "reportingManager")
		private String reportingManager;
		
		@Required(desc = "resourceManager")
		private String resourceManager;
		
		@Required(desc = "shift")
		private String shiftId;
		
		private String isMailSent;
		
		private String jobTitle;
		
		private String location;
		
		private String linkedIn;

		private String facebook;
		
		private String twitter;
		
		private String googlePlus;
		
		private String createdDate;
		
		private String createdBy;
		
		private String updatedDate;
		
		private String updatedBy;
		
		private String userUUID;
		
		private String professionalRegisterCountry;
		
		private String recruiterRegisterCountry;
		
		private String ssn;
		
		private Set<UserRole> userRole = new HashSet<UserRole>(0);
		
		private Set<Employees> employee = new HashSet<Employees>(0);
		
		/*@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	    @Fetch(FetchMode.SELECT)
	    private Set<PasswordResets> passwordResets;*/
		
		private Set<Candidate> candidateSet;
		
		private String fileContentType;
		
		private String terminateReason;
		
		private String previousUserState;
		
		private String country;
		
		private String registerOptionalServiceId;
		
		private String employeeId;
		
		private String rate;
		
		private String RatePer;
		
		private String DayHours;
		
		private String currentCurrency;
		
		private String designationType;
		
		private String designationOtherType;
		
		private String department;
		
		private String vendors;
		
		private String vendorcontact;
		
		private String dateofJoin;
		
		private String middleName;
		
		private String gender;
		
		private String maritalStatus;
		
		private String dateofBirth;

		private String isPrimary;

		private String roleChangeReason;

		private String designation;

		private String newlyLoginUser;

		@Required(desc = "roleType")
		private String roleType;
		
		private String roleTypeId;
		
		private String EmailId;
		
		private String panNumber;
		
		private String aadharNumber;
		
		private String phoneNumber;
		
		private String extensionNumber;
		
		private String assignReportingManagers;
		
		private String assignResourceManagers;
		
		private String managerUsers;
		
		private String clientUsers;
		
		private String vendorUsers;
		
		private String approverUser;
		
		private String projectApproverUser;
		
		private String clientDocsOwnerUser;
		
		private String vendorDocsOwnerUser;
		
		private String userChangeRole;
		
		private String teamOwnerUser;
		
		private String reason;
		
		private String terminateDate;
		
		private String registeredOrgLocationId;
		
		private String registredOrgId;
		
		private String userId;
		
		private String residenceAddress;

		private String residenceStreetAddress;
		
		private String residenceState;
		
		private String residenceCountry;
		
		private String residenceCity;
		
		private String residenceZipCode;
		
		private String residenceStateCode;
		
		private String residenceLatitude;
		
		private String residenceLongitude;
		
		private String workLocation;

		private List<CustomFormFieldDTO> customFormData;
		
		private String associateId;
		
		private Long candidateId;
		
		private String allowEmployeeToFill;

		private String probationPeriod;
		
		private String confirmationDate;
		
		private String probationStatus;
		
		
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


		public String getUserState() {
			return userState;
		}

		public void setUserState(String userState) {
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

		public String getReportingManager() {
			return reportingManager;
		}

		public void setReportingManager(String reportingManager) {
			this.reportingManager = reportingManager;
		}

		public String getIsMailSent() {
			return isMailSent;
		}

		public void setIsMailSent(String isMailSent) {
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

		public String getTerminateDate() {
			return terminateDate;
		}

		public void setTerminateDate(String terminateDate) {
			this.terminateDate = terminateDate;
		}

		public void setTerminateReason(String terminateReason) {
			this.terminateReason = terminateReason;
		}

		public String getPreviousUserState() {
			return previousUserState;
		}

		public void setPreviousUserState(String previousUserState) {
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

		public String getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

		public String getRatePer() {
			return RatePer;
		}

		public void setRatePer(String ratePer) {
			RatePer = ratePer;
		}

		public String getDayHours() {
			return DayHours;
		}

		public void setDayHours(String dayHours) {
			DayHours = dayHours;
		}

		public String getCurrentCurrency() {
			return currentCurrency;
		}

		public void setCurrentCurrency(String currentCurrency) {
			this.currentCurrency = currentCurrency;
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

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
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

		public String getDateofJoin() {
			return dateofJoin;
		}

		public void setDateofJoin(String dateofJoin) {
			this.dateofJoin = dateofJoin;
		}

		public String getIsPrimary() {
			return isPrimary;
		}

		public void setIsPrimary(String isPrimary) {
			this.isPrimary = isPrimary;
		}

		public String getRoleChangeReason() {
			return roleChangeReason;
		}

		public void setRoleChangeReason(String roleChangeReason) {
			this.roleChangeReason = roleChangeReason;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getNewlyLoginUser() {
			return newlyLoginUser;
		}

		public void setNewlyLoginUser(String newlyLoginUser) {
			this.newlyLoginUser = newlyLoginUser;
		}

		public String getRoleType() {
			return roleType;
		}

		public void setRoleType(String roleType) {
			this.roleType = roleType;
		}

		public String getRoleTypeId() {
			return roleTypeId;
		}

		public void setRoleTypeId(String roleTypeId) {
			this.roleTypeId = roleTypeId;
		}

		public String getEmailId() {
			return EmailId;
		}

		public void setEmailId(String emailId) {
			EmailId = emailId;
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

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		
		public String getExtensionNumber()
		{
			return extensionNumber;
		}

		public void setExtensionNumber(String extensionNumber)
		{
			this.extensionNumber = extensionNumber;
		}

		public String getAssignReportingManagers() {
			return assignReportingManagers;
		}

		public void setAssignReportingManagers(String assignReportingManagers) {
			this.assignReportingManagers = assignReportingManagers;
		}

		public String getManagerUsers() {
			return managerUsers;
		}

		public void setManagerUsers(String managerUsers) {
			this.managerUsers = managerUsers;
		}

		public String getClientUsers() {
			return clientUsers;
		}

		public void setClientUsers(String clientUsers) {
			this.clientUsers = clientUsers;
		}

		public String getVendorUsers() {
			return vendorUsers;
		}

		public void setVendorUsers(String vendorUsers) {
			this.vendorUsers = vendorUsers;
		}

		public String getApproverUser() {
			return approverUser;
		}

		public void setApproverUser(String approverUser) {
			this.approverUser = approverUser;
		}

		public String getProjectApproverUser() {
			return projectApproverUser;
		}

		public void setProjectApproverUser(String projectApproverUser) {
			this.projectApproverUser = projectApproverUser;
		}

		public String getClientDocsOwnerUser() {
			return clientDocsOwnerUser;
		}

		public void setClientDocsOwnerUser(String clientDocsOwnerUser) {
			this.clientDocsOwnerUser = clientDocsOwnerUser;
		}

		public String getVendorDocsOwnerUser() {
			return vendorDocsOwnerUser;
		}

		public void setVendorDocsOwnerUser(String vendorDocsOwnerUser) {
			this.vendorDocsOwnerUser = vendorDocsOwnerUser;
		}

		public String getUserChangeRole() {
			return userChangeRole;
		}

		public void setUserChangeRole(String userChangeRole) {
			this.userChangeRole = userChangeRole;
		}

		public String getTeamOwnerUser() {
			return teamOwnerUser;
		}

		public void setTeamOwnerUser(String teamOwnerUser) {
			this.teamOwnerUser = teamOwnerUser;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getRegisteredOrgLocationId() {
			return registeredOrgLocationId;
		}

		public void setRegisteredOrgLocationId(String registeredOrgLocationId) {
			this.registeredOrgLocationId = registeredOrgLocationId;
		}

		public String getRegistredOrgId() {
			return registredOrgId;
		}

		public void setRegistredOrgId(String registredOrgId) {
			this.registredOrgId = registredOrgId;
		}

		public String getSsn() {
			return ssn;
		}

		public void setSsn(String ssn) {
			this.ssn = ssn;
		}
		public String getUserId(){
			return userId;
		}

		public void setUserId(String userId){
			this.userId = userId;

		}

		public List<CustomFormFieldDTO> getCustomFormData() {
			return customFormData;
		}

		public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
			this.customFormData = customFormData;
		}

		public String getResidenceAddress()
		{
			return residenceAddress;
		}

		public void setResidenceAddress(String residenceAddress)
		{
			this.residenceAddress = residenceAddress;
		}

		public String getResidenceStreetAddress()
		{
			return residenceStreetAddress;
		}

		public void setResidenceStreetAddress(String residenceStreetAddress)
		{
			this.residenceStreetAddress = residenceStreetAddress;
		}

		public String getResidenceState()
		{
			return residenceState;
		}

		public void setResidenceState(String residenceState)
		{
			this.residenceState = residenceState;
		}

		public String getResidenceCity()
		{
			return residenceCity;
		}

		public void setResidenceCity(String residenceCity)
		{
			this.residenceCity = residenceCity;
		}

		public String getResidenceZipCode()
		{
			return residenceZipCode;
		}

		public void setResidenceZipCode(String residenceZipCode)
		{
			this.residenceZipCode = residenceZipCode;
		}

		public String getResidenceStateCode()
		{
			return residenceStateCode;
		}

		public void setResidenceStateCode(String residenceStateCode)
		{
			this.residenceStateCode = residenceStateCode;
		}

		public String getResidenceLatitude()
		{
			return residenceLatitude;
		}

		public void setResidenceLatitude(String residenceLatitude)
		{
			this.residenceLatitude = residenceLatitude;
		}

		public String getResidenceLongitude()
		{
			return residenceLongitude;
		}

		public void setResidenceLongitude(String residenceLongitude)
		{
			this.residenceLongitude = residenceLongitude;
		}

		public String getResidenceCountry()
		{
			return residenceCountry;
		}

		public void setResidenceCountry(String residenceCountry)
		{
			this.residenceCountry = residenceCountry;
		}

		public String getWorkLocation()
		{
			return workLocation;
		}

		public void setWorkLocation(String workLocation)
		{
			this.workLocation = workLocation;
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

		public String getDateofBirth() {
			return dateofBirth;
		}

		public void setDateofBirth(String dateofBirth) {
			this.dateofBirth = dateofBirth;
		}

		public String getMaritalStatus() {
			return maritalStatus;
		}

		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}	

		public String getIsGlobalAccess() {
			return isGlobalAccess;
		}

		public void setIsGlobalAccess(String isGlobalAccess) {
			this.isGlobalAccess = isGlobalAccess;
		}

		public String getResourceManager() {
			return resourceManager;
		}

		public void setResourceManager(String resourceManager) {
			this.resourceManager = resourceManager;
		}

		public String getAssignResourceManagers() {
			return assignResourceManagers;
		}

		public void setAssignResourceManagers(String assignResourceManagers) {
			this.assignResourceManagers = assignResourceManagers;
		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}

		public String getShiftId() {
			return shiftId;
		}

		public void setShiftId(String shiftId) {
			this.shiftId = shiftId;
		}

		public String getAssociateId() {
			return associateId;
		}

		public void setAssociateId(String associateId) {
			this.associateId = associateId;
		}

		public Long getCandidateId() {
			return candidateId;
		}

		public void setCandidateId(Long candidateId) {
			this.candidateId = candidateId;
		}

		public String getAllowEmployeeToFill() {
			return allowEmployeeToFill;
		}

		public void setAllowEmployeeToFill(String allowEmployeeToFill) {
			this.allowEmployeeToFill = allowEmployeeToFill;
		}
		
		public String getProbationPeriod() {
			return probationPeriod;
		}

		public void setProbationPeriod(String probationPeriod) {
			this.probationPeriod = probationPeriod;
		}

		public String getConfirmationDate() {
			return confirmationDate;
		}

		public void setConfirmationDate(String confirmationDate) {
			this.confirmationDate = confirmationDate;
		}

		public String getProbationStatus() {
			return probationStatus;
		}

		public void setProbationStatus(String probationStatus) {
			this.probationStatus = probationStatus;
		}
		
}
