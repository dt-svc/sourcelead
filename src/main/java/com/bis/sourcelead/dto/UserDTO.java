package com.bis.sourcelead.dto;

import java.io.Serializable;

import java.math.BigDecimal;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Transient;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.UserRole;

public class UserDTO extends SearchDTO implements Serializable {
	private long id;
	private String name;
	private String designation;
	private String profileImage;
	private long parentId;
	private String userType;
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String userRole;
	private String searchByName;
	private String userState;
	private String loginUserRole;
	private String roleId;
	private String fileURL;
	private String userName;
	private String userEmail;
	private String createdOn;
	private List<UserRole> enrolledOrgs;
	private int activeCount;
	private int inactiveCount;
	private int notLoggedinCount;
	private List<Long> orgId;
	private String noOfActivities;
	private String lastActivity;
	private long userRoleId;
	private long locationId;
	private long multipleIps;
	private String customDates;
	private String email;
	private String fullName;
	private String unSubscribeTabIds;
	private String fromDate;
	private String toDate;
	private String isPrimaryAdmin;
	private String isSuperAdmin;
	private String role;
	private String subscribedOrgLocationRoles;
	private String userloggedInStatus;
	
	private String employeeId;
	private String dateofJoin;
	private String dateOfTermination;
	private String employmentType;
	
	private String userCode;
	
	private String linkedUserName;
	
	private String linkedUserPwd;

	private BigInteger totalNoOfUsers;
	
    private BigDecimal userRequirementsCount = new BigDecimal(0);
	
    private BigDecimal userApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userCandidatesCount = new BigDecimal(0);
	
    private BigDecimal userShortlistedApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userSubmittedApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userInterviewedApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userOfferedApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userRejectedApplicantsCount = new BigDecimal(0);
	
    private BigDecimal userApplicantsBackOutCount = new BigDecimal(0);
    
    private BigDecimal userApplicantsJoinedCount = new BigDecimal(0);
    
    private BigDecimal userOnHoldApplicantsCount = new BigDecimal(0);
    
    private BigDecimal userNoResponseApplicantsCount = new BigDecimal(0);
    
    private String isGlobalUser;
    
    private String isPayRollApprover;

    private BigInteger userId;

    private String tags;
    
    private String joinersAmount;
    
    private List<String> empTypes;
    
    private List<Long> roles;
    
    private Integer assignedJobsCount;
	
	public String getUnSubscribeTabIds()
	{
		return unSubscribeTabIds;
	}

	public void setUnSubscribeTabIds(String unSubscribeTabIds)
	{
		this.unSubscribeTabIds = unSubscribeTabIds;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDesignation()
	{
		return designation;
	}
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	public String getProfileImage()
	{
		return profileImage;
	}
	
	public void setProfileImage(String profileImage)
	{
		this.profileImage = profileImage;
	}
	
	public long getParentId()
	{
		return parentId;
	}
	
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSearchByName() {
		return searchByName;
	}

	public String getUserState() {
		return userState;
	}

	public String getLoginUserRole() {
		return loginUserRole;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public String getCreatedOn()
	{
		return createdOn;
	}

	public void setCreatedOn(String createdOn)
	{
		this.createdOn = createdOn;
	}

	public List<UserRole> getEnrolledOrgs()
	{
		return enrolledOrgs;
	}

	public void setEnrolledOrgs(List<UserRole> enrolledOrgs)
	{
		this.enrolledOrgs = enrolledOrgs;
	}

	public int getActiveCount()
	{
		return activeCount;
	}

	public void setActiveCount(int activeCount)
	{
		this.activeCount = activeCount;
	}

	public int getInactiveCount()
	{
		return inactiveCount;
	}

	public void setInactiveCount(int inactiveCount)
	{
		this.inactiveCount = inactiveCount;
	}

	public int getNotLoggedinCount()
	{
		return notLoggedinCount;
	}

	public void setNotLoggedinCount(int notLoggedinCount)
	{
		this.notLoggedinCount = notLoggedinCount;
	}

	public List<Long> getOrgId()
	{
		return orgId;
	}

	public void setOrgId(List<Long> orgId)
	{
		this.orgId = orgId;
	}

	public String getNoOfActivities()
	{
		return noOfActivities;
	}

	public void setNoOfActivities(String noOfActivities)
	{
		this.noOfActivities = noOfActivities;
	}

	public String getLastActivity()
	{
		return lastActivity;
	}

	public void setLastActivity(String lastActivity)
	{
		this.lastActivity = lastActivity;
	}

	public long getUserRoleId()
	{
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId)
	{
		this.userRoleId = userRoleId;
	}

	public long getLocationId()
	{
		return locationId;
	}

	public void setLocationId(long locationId)
	{
		this.locationId = locationId;
	}

	public long getMultipleIps()
	{
		return multipleIps;
	}

	public void setMultipleIps(long multipleIps)
	{
		this.multipleIps = multipleIps;
	}

	public String getCustomDates()
	{
		return customDates;
	}

	public void setCustomDates(String customDates)
	{
		this.customDates = customDates;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getIsPrimaryAdmin() {
		return isPrimaryAdmin;
	}

	public void setIsPrimaryAdmin(String isPrimaryAdmin) {
		this.isPrimaryAdmin = isPrimaryAdmin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSubscribedOrgLocationRoles() {
		return subscribedOrgLocationRoles;
	}

	public void setSubscribedOrgLocationRoles(String subscribedOrgLocationRoles) {
		this.subscribedOrgLocationRoles = subscribedOrgLocationRoles;
	}

	public String getUserloggedInStatus() {
		return userloggedInStatus;
	}

	public void setUserloggedInStatus(String userloggedInStatus) {
		this.userloggedInStatus = userloggedInStatus;

	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDateofJoin() {
		return dateofJoin;
	}

	public void setDateofJoin(String dateofJoin) {
		this.dateofJoin = dateofJoin;
	}

	public String getDateOfTermination() {
		return dateOfTermination;
	}

	public void setDateOfTermination(String dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getLinkedUserName() {
		return linkedUserName;
	}

	public void setLinkedUserName(String linkedUserName) {
		this.linkedUserName = linkedUserName;
	}

	public String getLinkedUserPwd() {
		return linkedUserPwd;
	}

	public void setLinkedUserPwd(String linkedUserPwd) {
		this.linkedUserPwd = linkedUserPwd;
	}


	public BigInteger getTotalNoOfUsers()
	{
		return totalNoOfUsers;
	}

	public void setTotalNoOfUsers(BigInteger totalNoOfUsers)
	{
		this.totalNoOfUsers = totalNoOfUsers;
	}

	public BigDecimal getUserRequirementsCount() {
		return userRequirementsCount;
	}

	public void setUserRequirementsCount(BigDecimal userRequirementsCount) {
		this.userRequirementsCount = userRequirementsCount;
	}

	

	public BigDecimal getUserApplicantsCount() {
		return userApplicantsCount;
	}

	public void setUserApplicantsCount(BigDecimal userApplicantsCount) {
		this.userApplicantsCount = userApplicantsCount;
	}

	public BigDecimal getUserCandidatesCount() {
		return userCandidatesCount;
	}

	public void setUserCandidatesCount(BigDecimal userCandidatesCount) {
		this.userCandidatesCount = userCandidatesCount;
	}

	public BigDecimal getUserShortlistedApplicantsCount() {
		return userShortlistedApplicantsCount;
	}

	public void setUserShortlistedApplicantsCount(BigDecimal userShortlistedApplicantsCount) {
		this.userShortlistedApplicantsCount = userShortlistedApplicantsCount;
	}

	public BigDecimal getUserSubmittedApplicantsCount() {
		return userSubmittedApplicantsCount;
	}

	public void setUserSubmittedApplicantsCount(BigDecimal userSubmittedApplicantsCount) {
		this.userSubmittedApplicantsCount = userSubmittedApplicantsCount;
	}

	public BigDecimal getUserInterviewedApplicantsCount() {
		return userInterviewedApplicantsCount;
	}

	public void setUserInterviewedApplicantsCount(BigDecimal userInterviewedApplicantsCount) {
		this.userInterviewedApplicantsCount = userInterviewedApplicantsCount;
	}

	public BigDecimal getUserOfferedApplicantsCount() {
		return userOfferedApplicantsCount;
	}

	public void setUserOfferedApplicantsCount(BigDecimal userOfferedApplicantsCount) {
		this.userOfferedApplicantsCount = userOfferedApplicantsCount;
	}

	public BigDecimal getUserRejectedApplicantsCount() {
		return userRejectedApplicantsCount;
	}

	public void setUserRejectedApplicantsCount(BigDecimal userRejectedApplicantsCount) {
		this.userRejectedApplicantsCount = userRejectedApplicantsCount;
	}

	public BigDecimal getUserApplicantsBackOutCount() {
		return userApplicantsBackOutCount;
	}

	public void setUserApplicantsBackOutCount(BigDecimal userApplicantsBackOutCount) {
		this.userApplicantsBackOutCount = userApplicantsBackOutCount;
	}


	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigDecimal getUserApplicantsJoinedCount() {
		return userApplicantsJoinedCount;
	}

	public void setUserApplicantsJoinedCount(BigDecimal userApplicantsJoinedCount) {
		this.userApplicantsJoinedCount = userApplicantsJoinedCount;
	}

	public String getIsGlobalUser() {
		return isGlobalUser;
	}

	public void setIsGlobalUser(String isGlobalUser) {
		this.isGlobalUser = isGlobalUser;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getIsPayRollApprover() {
		return isPayRollApprover;
	}

	public void setIsPayRollApprover(String isPayRollApprover) {
		this.isPayRollApprover = isPayRollApprover;
	}

	public String getJoinersAmount()
	{
		return joinersAmount;
	}

	public void setJoinersAmount(String joinersAmount)
	{
		this.joinersAmount = joinersAmount;
	}

	public List<String> getEmpTypes() {
		return empTypes;
	}

	public void setEmpTypes(List<String> empTypes) {
		this.empTypes = empTypes;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

	public Integer getAssignedJobsCount() {
		return assignedJobsCount;
	}

	public void setAssignedJobsCount(Integer assignedJobsCount) {
		this.assignedJobsCount = assignedJobsCount;
	}

	public BigDecimal getUserOnHoldApplicantsCount()
	{
		return userOnHoldApplicantsCount;
	}

	public void setUserOnHoldApplicantsCount(BigDecimal userOnHoldApplicantsCount)
	{
		this.userOnHoldApplicantsCount = userOnHoldApplicantsCount;
	}

	public BigDecimal getUserNoResponseApplicantsCount()
	{
		return userNoResponseApplicantsCount;
	}

	public void setUserNoResponseApplicantsCount(BigDecimal userNoResponseApplicantsCount)
	{
		this.userNoResponseApplicantsCount = userNoResponseApplicantsCount;
	}
	
}
