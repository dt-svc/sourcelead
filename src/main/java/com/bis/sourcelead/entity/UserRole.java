package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Status;

/**
 * @author bis113
 *
 */
@Entity
@Table(name = "USER_ROLE", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "userRole")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REPORTING_MANAGER_ID")
	private User reportingManager;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Roles roles;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_LOCATIONS_ID", nullable = false)
	private OrgLocations orgLocations;

	@ManyToOne(fetch = FetchType.EAGER)
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

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "NEWLY_LOGIN_USER")
	private String newlyLoginUser;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
	private String userRoleName;

	@Transient
	private String currencyConversionWithCountry;

	@Transient
	private String employeeId;

	@Transient
	private String userEmploymentType;

	@Transient
	private Integer projectsCount;

	@Transient
	private Integer openTimeSheetsCount;

	@Transient
	private Integer approvedTimeSheetsCount;

	@Transient
	private Integer rejectedTimeSheetsCount;

	@Transient
	private Integer waitingTimeSheetsCount;

	@Transient
	private String rate;

	@Transient
	private String ratePer;

	@Transient
	private String InvoiceFrequency;

	@Transient
	private String hireDateStr;

	@Transient
	private String dailyHours;

	@Transient
	private String currentCurrency;

	@Transient
	List<User> allReportingManagers;

	@Transient
	private String vendorName;

	@Transient
	private String vendorContactName;

	@Transient
	private String departmentName;

	@Transient
	private String userName;

	@Transient
	List<User> reportingManagers;

	@Transient
	List<User> clientUsersList;

	@Transient
	List<User> vendorUsersList;

	@Transient
	List<User> requirementManagersList;

	@Transient
	List<User> requirementApproversList;

	@Transient
	List<User> teamOwnersList;

	@Transient
	List<User> projectApproversList;

	@Transient
	private String userEmail;

	@Transient
	List<Roles> rolesList;

	@Transient
	private Integer assignedUsersCountToReportingManager;

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
	private Long userId;

	@Transient
	private String isMailSent;

	@Transient
	private String vendorOrgRelationId;

	@Transient
	private String designationOtherType;

	@Transient
	private String vendorSpocId;

	@Transient
	private String department;
	
	@Transient
	private String reportingManagerName; 
	
	@Transient
	private String employeMentType; 

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

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
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

}