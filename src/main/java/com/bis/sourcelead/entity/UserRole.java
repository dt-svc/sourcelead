package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author bis113
 *
 */
@Entity
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employees employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTING_MANAGER_ID")
	private User reportingManager;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_MANAGER_ID")
	private User resourceManager;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Roles roles;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATIONS_ID", nullable = false)
	private OrgLocations orgLocations;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SHIFT_ID")
	private Shift shift;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID", nullable = false)
	private Org orgId;

	@Column(name = "IS_PRIMARY")
	private Integer isPrimary;

	@Column(name = "USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status userState;

	@Column(name = "PREVIOUS_USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status previousUserState;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "ROLE_CHANGE_REASON")
	@Type(type = "text")
	private String roleChangeReason;

	@Column(name = "TERMINATE_REASON", columnDefinition = "TEXT default NULL")
	private String terminateReason;
	
	@Column(name = "TERMINATE_DATE")
	private Date terminateDate;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "NEWLY_LOGIN_USER")
	private String newlyLoginUser;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "IS_GLOBAL")
	private String isGlobal;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "IS_MAIL_SENT")
	@Enumerated(EnumType.STRING)
	private Status isMailSent;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
    @Column(name = "EXTENSION_NUMBER")
	private String extensionNumber;
    
    @Column(name = "LOGIN_SECURITY")
	private String loginSecurity;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE_ID")
	private AssociateGrades gradeId;

	@Transient
	@JsonProperty
	private String userRoleName;

	@Transient
	@JsonProperty
	private String currencyConversionWithCountry;

	@Transient
	@JsonProperty
	private String employeeId;
	
	@Transient
	@JsonProperty
	private String orgName;


	@Transient
	@JsonProperty
	private String userEmploymentType;

	@Transient
	@JsonProperty
	private Integer projectsCount;

	@Transient
	@JsonProperty
	private Integer openTimeSheetsCount;

	@Transient
	@JsonProperty
	private Integer approvedTimeSheetsCount;

	@Transient
	@JsonProperty
	private Integer rejectedTimeSheetsCount;

	@Transient
	@JsonProperty
	private Integer waitingTimeSheetsCount;

	@Transient
	@JsonProperty
	private String rate;

	@Transient
	@JsonProperty
	private String ratePer;

	@Transient
	@JsonProperty
	private String InvoiceFrequency;

	@Transient
	@JsonProperty
	private String hireDateStr;
	
	@Transient
	@JsonProperty
	private String terminateDateStr;
	
	@Transient
	@JsonProperty
	private String dateofBirthStr;
	
	@Transient
	@JsonProperty
	private String panNumber;
	
	@Transient
	@JsonProperty
	private String aadharNumber;

	@Transient
	@JsonProperty
	private String dailyHours;

	@Transient
	@JsonProperty
	private String currentCurrency;

	@Transient
	@JsonProperty
	List<User> allReportingManagers;
	
	@Transient
	@JsonProperty
	List<AssociateGrades> gradesList;
	
	@Transient
	@JsonProperty
	List<User> allResourceManagers;

	@Transient
	@JsonProperty
	private String vendorName;

	@Transient
	@JsonProperty
	private String vendorContactName;

	@Transient
	@JsonProperty
	private String departmentName;

	@Transient
	@JsonProperty
	private String userName;
	
	@Transient
	@JsonProperty
	private String fileUrl;

	@Transient
	@JsonProperty
	List<User> reportingManagers;
	
	@Transient
	@JsonProperty
	List<User> resourceManagers;

	@Transient
	@JsonProperty
	List<User> clientUsersList;

	@Transient
	@JsonProperty
	List<User> vendorUsersList;

	@Transient
	@JsonProperty
	List<User> requirementManagersList;

	@Transient
	List<User> requirementApproversList;

	@Transient
	@JsonProperty
	List<User> teamOwnersList;

	@Transient
	@JsonProperty
	List<User> projectApproversList;

	@Transient
	@JsonProperty
	private String userEmail;

	@Transient
	@JsonProperty
	private String mobilePhone;
	
	@Transient
	@JsonProperty
	List<Roles> rolesList;
	
	@Transient
	private Integer assignedUsersCountToReportingManager;
	
	@Transient
	private Integer assignedUsersCountToResourceManager;

	@Transient
	private Integer assignedAsVendorOwnerOrSpocCount;

	@Transient
	private Integer assignedAsClientOwnerOrSpocCount;

	@Transient
	private Integer requirementsCountAsManager;

	@Transient
	private Integer requirementsCountAsApprover;

	@Transient
	private Integer teamsCountAsOwner;

	@Transient
	private Integer projectApproverCount;
	
	@Transient
	private Integer clientDocumentsOwnerCount;
	
	@Transient
	private Integer vendorDocumentsOwnerCount;
	
	@Transient
	List<User> clientDocumentsOwnerList;

	@Transient
	List<User> vendorDocumentsOwnerList;

	@Transient
	@JsonProperty
	private Long userId;
	
	
	@Transient
	@JsonProperty
	private String userCode;

	@Transient
	@JsonProperty
	private String vendorOrgRelationId;

	@Transient
	private String designationOtherType;

	@Transient
	@JsonProperty
	private String vendorSpocId;

	@Transient
	@JsonProperty
	private String department;
	
	@Transient
	private String reportingManagerName; 
	
	@Transient
	private String employeMentType; 
	
	@Transient
	@JsonProperty
	private String candidateDesignation;

	@Transient
	@JsonProperty
	private Map<Long,String> userCustomFieldlabelNameAndValue;
	
	
	@Transient
	@JsonProperty
	private Long shiftId;
	
	@Transient
	@JsonProperty
	private String shiftName;
	
	@Transient
	@JsonProperty
	private BigInteger orgLocId;
	
	@Transient
	@JsonProperty
	private BigInteger userRoleShiftId;
	
	@Transient
	@JsonProperty
	private BigInteger userRoleId;
	
	@Transient
	@JsonProperty
	private String isExistedOrgGlobalUser;
	
	@Transient
	@JsonProperty
	private String ssn;
	
	@Transient
	@JsonProperty
	private String workLocation;
	
	@Transient
	@JsonProperty
	private String residenceAddress;
	
	@Transient
	@JsonProperty
	private List<OrgLocations> locationList;
	
	@Transient
	@JsonProperty
	private Long workLocationId;
	
	@Transient
	@JsonProperty
	private String residenceCountry;
	
	@Transient
	@JsonProperty
	private String residenceState;
	
	
	@Transient
	@JsonProperty
	private String residenceCity;
	
	@Transient
	@JsonProperty
	private String residenceZipCode;
	
	@Transient
	@JsonProperty
	private String residenceLatitude;
	
	@Transient
	@JsonProperty
	private String residenceLongitude;
	
	@Transient
	@JsonProperty
	private String confirmationDateStr;
	
	@Transient
	@JsonProperty
	private String probationPeriod;
	
	@Transient
	@JsonProperty
	private String probationStatus;
	
	public BigInteger getUserRoleId() {
		if(id != null) {
			userRoleId = BigInteger.valueOf(id);
		}
		return userRoleId;
	}

	public void setUserRoleId(BigInteger userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<User> getAllReportingManagers() {
		return allReportingManagers;
	}

	public void setAllReportingManagers(List<User> allReportingManagers) {
		this.allReportingManagers = allReportingManagers;
	}

	public String getDailyHours() {
		return dailyHours;
	}

	public void setDailyHours(String dailyHours) {
		this.dailyHours = dailyHours;
	}

	public String getHireDateStr() {
		return hireDateStr;
	}

	public void setHireDateStr(String hireDateStr) {
		this.hireDateStr = hireDateStr;
	}

	public String getInvoiceFrequency() {
		return InvoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		InvoiceFrequency = invoiceFrequency;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRatePer() {
		return ratePer;
	}

	public void setRatePer(String ratePer) {
		this.ratePer = ratePer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(User reportingManager) {
		this.reportingManager = reportingManager;
	}

	public Status getUserState() {
		return userState;
	}

	public void setUserState(Status userState) {
		this.userState = userState;
	}

	public Status getPreviousUserState() {
		return previousUserState;
	}

	public void setPreviousUserState(Status previousUserState) {
		this.previousUserState = previousUserState;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
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

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public Integer getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(Integer projectsCount) {
		this.projectsCount = projectsCount;
	}

	public Integer getOpenTimeSheetsCount() {
		return openTimeSheetsCount;
	}

	public void setOpenTimeSheetsCount(Integer openTimeSheetsCount) {
		this.openTimeSheetsCount = openTimeSheetsCount;
	}

	public Integer getApprovedTimeSheetsCount() {
		return approvedTimeSheetsCount;
	}

	public void setApprovedTimeSheetsCount(Integer approvedTimeSheetsCount) {
		this.approvedTimeSheetsCount = approvedTimeSheetsCount;
	}

	public Integer getRejectedTimeSheetsCount() {
		return rejectedTimeSheetsCount;
	}

	public void setRejectedTimeSheetsCount(Integer rejectedTimeSheetsCount) {
		this.rejectedTimeSheetsCount = rejectedTimeSheetsCount;
	}

	public Integer getWaitingTimeSheetsCount() {
		return waitingTimeSheetsCount;
	}

	public void setWaitingTimeSheetsCount(Integer waitingTimeSheetsCount) {
		this.waitingTimeSheetsCount = waitingTimeSheetsCount;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<User> getReportingManagers() {
		return reportingManagers;
	}

	public void setReportingManagers(List<User> reportingManagers) {
		this.reportingManagers = reportingManagers;
	}

	public Integer getAssignedUsersCountToReportingManager() {
		return assignedUsersCountToReportingManager;
	}

	public void setAssignedUsersCountToReportingManager(Integer assignedUsersCountToReportingManager) {
		this.assignedUsersCountToReportingManager = assignedUsersCountToReportingManager;
	}

	public List<User> getClientUsersList() {
		return clientUsersList;
	}

	public void setClientUsersList(List<User> clientUsersList) {
		this.clientUsersList = clientUsersList;
	}

	public List<User> getVendorUsersList() {
		return vendorUsersList;
	}

	public void setVendorUsersList(List<User> vendorUsersList) {
		this.vendorUsersList = vendorUsersList;
	}

	public Integer getAssignedAsVendorOwnerOrSpocCount() {
		return assignedAsVendorOwnerOrSpocCount;
	}

	public void setAssignedAsVendorOwnerOrSpocCount(Integer assignedAsVendorOwnerOrSpocCount) {
		this.assignedAsVendorOwnerOrSpocCount = assignedAsVendorOwnerOrSpocCount;
	}

	public Integer getAssignedAsClientOwnerOrSpocCount() {
		return assignedAsClientOwnerOrSpocCount;
	}

	public void setAssignedAsClientOwnerOrSpocCount(Integer assignedAsClientOwnerOrSpocCount) {
		this.assignedAsClientOwnerOrSpocCount = assignedAsClientOwnerOrSpocCount;
	}

	public List<User> getRequirementManagersList() {
		return requirementManagersList;
	}

	public void setRequirementManagersList(List<User> requirementManagersList) {
		this.requirementManagersList = requirementManagersList;
	}

	public Integer getRequirementsCountAsManager() {
		return requirementsCountAsManager;
	}

	public void setRequirementsCountAsManager(Integer requirementsCountAsManager) {
		this.requirementsCountAsManager = requirementsCountAsManager;
	}

	public Integer getRequirementsCountAsApprover() {
		return requirementsCountAsApprover;
	}

	public void setRequirementsCountAsApprover(Integer requirementsCountAsApprover) {
		this.requirementsCountAsApprover = requirementsCountAsApprover;
	}

	public List<User> getRequirementApproversList() {
		return requirementApproversList;
	}

	public void setRequirementApproversList(List<User> requirementApproversList) {
		this.requirementApproversList = requirementApproversList;
	}

	public List<User> getTeamOwnersList() {
		return teamOwnersList;
	}

	public void setTeamOwnersList(List<User> teamOwnersList) {
		this.teamOwnersList = teamOwnersList;
	}

	public Integer getTeamsCountAsOwner() {
		return teamsCountAsOwner;
	}

	public void setTeamsCountAsOwner(Integer teamsCountAsOwner) {
		this.teamsCountAsOwner = teamsCountAsOwner;
	}

	public List<User> getProjectApproversList() {
		return projectApproversList;
	}

	public void setProjectApproversList(List<User> projectApproversList) {
		this.projectApproversList = projectApproversList;
	}

	public Integer getProjectApproverCount() {
		return projectApproverCount;
	}

	public void setProjectApproverCount(Integer projectApproverCount) {
		this.projectApproverCount = projectApproverCount;
	}

	public List<Roles> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Roles> rolesList) {
		this.rolesList = rolesList;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public String getUserEmploymentType() {
		return userEmploymentType;
	}

	public void setUserEmploymentType(String userEmploymentType) {
		this.userEmploymentType = userEmploymentType;
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

	public String getDesignationOtherType() {
		return designationOtherType;
	}

	public void setDesignationOtherType(String designationOtherType) {
		this.designationOtherType = designationOtherType;
	}

	public String getCurrentCurrency() {
		return currentCurrency;
	}

	public void setCurrentCurrency(String currentCurrency) {
		this.currentCurrency = currentCurrency;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getNewlyLoginUser() {
		return newlyLoginUser;
	}

	public void setNewlyLoginUser(String newlyLoginUser) {
		this.newlyLoginUser = newlyLoginUser;
	}

	public String getCurrencyConversionWithCountry() {
		return currencyConversionWithCountry;
	}

	public void setCurrencyConversionWithCountry(String currencyConversionWithCountry) {
		this.currencyConversionWithCountry = currencyConversionWithCountry;

	}

	public Integer getClientDocumentsOwnerCount() {
		return clientDocumentsOwnerCount;
	}

	public void setClientDocumentsOwnerCount(Integer clientDocumentsOwnerCount) {
		this.clientDocumentsOwnerCount = clientDocumentsOwnerCount;
	}

	public Integer getVendorDocumentsOwnerCount() {
		return vendorDocumentsOwnerCount;
	}

	public void setVendorDocumentsOwnerCount(Integer vendorDocumentsOwnerCount) {
		this.vendorDocumentsOwnerCount = vendorDocumentsOwnerCount;
	}

	public List<User> getClientDocumentsOwnerList() {
		return clientDocumentsOwnerList;
	}

	public void setClientDocumentsOwnerList(List<User> clientDocumentsOwnerList) {
		this.clientDocumentsOwnerList = clientDocumentsOwnerList;
	}

	public List<User> getVendorDocumentsOwnerList() {
		return vendorDocumentsOwnerList;
	}

	public void setVendorDocumentsOwnerList(List<User> vendorDocumentsOwnerList) {
		this.vendorDocumentsOwnerList = vendorDocumentsOwnerList;
	}

	public String getReportingManagerName() {
		return reportingManagerName;
	}

	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmployeMentType() {
		return employeMentType;
	}

	public void setEmployeMentType(String employeMentType) {
		this.employeMentType = employeMentType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public Status getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(Status isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
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

	public String getLoginSecurity()
	{
		return loginSecurity;
	}

	public void setLoginSecurity(String loginSecurity)
	{
		this.loginSecurity = loginSecurity;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Map<Long, String> getUserCustomFieldlabelNameAndValue() {
		return userCustomFieldlabelNameAndValue;
	}

	public void setUserCustomFieldlabelNameAndValue(Map<Long, String> userCustomFieldlabelNameAndValue) {
		this.userCustomFieldlabelNameAndValue = userCustomFieldlabelNameAndValue;
	}


	public String getDateofBirthStr() {
		return dateofBirthStr;
	}

	public void setDateofBirthStr(String dateofBirthStr) {
		this.dateofBirthStr = dateofBirthStr;
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

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;

	}

	public String getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(String isGlobal) {
		this.isGlobal = isGlobal;
	}

	public User getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(User resourceManager) {
		this.resourceManager = resourceManager;
	}

	public List<User> getAllResourceManagers() {
		return allResourceManagers;
	}

	public void setAllResourceManagers(List<User> allResourceManagers) {
		this.allResourceManagers = allResourceManagers;
	}

	public List<User> getResourceManagers() {
		return resourceManagers;
	}

	public void setResourceManagers(List<User> resourceManagers) {
		this.resourceManagers = resourceManagers;
	}

	public Integer getAssignedUsersCountToResourceManager() {
		return assignedUsersCountToResourceManager;
	}

	public void setAssignedUsersCountToResourceManager(Integer assignedUsersCountToResourceManager) {
		this.assignedUsersCountToResourceManager = assignedUsersCountToResourceManager;
	}

	public String getCandidateDesignation() {
		return candidateDesignation;
	}

	public void setCandidateDesignation(String candidateDesignation) {
		this.candidateDesignation = candidateDesignation;
	}

	public AssociateGrades getGradeId() {
		return gradeId;
	}

	public void setGradeId(AssociateGrades gradeId) {
		this.gradeId = gradeId;
	}

	public List<AssociateGrades> getGradesList() {
		return gradesList;
	}

	public void setGradesList(List<AssociateGrades> gradesList) {
		this.gradesList = gradesList;
	}

	public Date getTerminateDate() {
		return terminateDate;
	}

	public void setTerminateDate(Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	public String getTerminateDateStr() {
		return terminateDateStr;
	}

	public void setTerminateDateStr(String terminateDateStr) {
		this.terminateDateStr = terminateDateStr;
	}

	public Shift getShift() {
		return shift;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public BigInteger getOrgLocId() {
		return orgLocId;
	}

	public void setOrgLocId(BigInteger orgLocId) {
		this.orgLocId = orgLocId;
	}

	public BigInteger getUserRoleShiftId() {
		return userRoleShiftId;
	}

	public void setUserRoleShiftId(BigInteger userRoleShiftId) {
		this.userRoleShiftId = userRoleShiftId;

	}

	public String getIsExistedOrgGlobalUser() {
		return isExistedOrgGlobalUser;
	}

	public void setIsExistedOrgGlobalUser(String isExistedOrgGlobalUser) {
		this.isExistedOrgGlobalUser = isExistedOrgGlobalUser;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public List<OrgLocations> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<OrgLocations> locationList) {
		this.locationList = locationList;
	}

	public Long getWorkLocationId() {
		return workLocationId;
	}

	public void setWorkLocationId(Long workLocationId) {
		this.workLocationId = workLocationId;
	}

	public String getResidenceCountry() {
		return residenceCountry;
	}

	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}

	public String getResidenceState() {
		return residenceState;
	}

	public void setResidenceState(String residenceState) {
		this.residenceState = residenceState;
	}

	public String getResidenceCity() {
		return residenceCity;
	}

	public void setResidenceCity(String residenceCity) {
		this.residenceCity = residenceCity;
	}

	public String getResidenceZipCode() {
		return residenceZipCode;
	}

	public void setResidenceZipCode(String residenceZipCode) {
		this.residenceZipCode = residenceZipCode;
	}

	public String getResidenceLatitude() {
		return residenceLatitude;
	}

	public void setResidenceLatitude(String residenceLatitude) {
		this.residenceLatitude = residenceLatitude;
	}

	public String getResidenceLongitude() {
		return residenceLongitude;
	}

	public void setResidenceLongitude(String residenceLongitude) {
		this.residenceLongitude = residenceLongitude;
	}

	public String getConfirmationDateStr() {
		return confirmationDateStr;
	}

	public void setConfirmationDateStr(String confirmationDateStr) {
		this.confirmationDateStr = confirmationDateStr;
	}

	public String getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(String probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public String getProbationStatus() {
		return probationStatus;
	}

	public void setProbationStatus(String probationStatus) {
		this.probationStatus = probationStatus;
	}

}