package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class UserRoleDTO extends SearchDTO implements Serializable  {
	private Long id;
	private String userName;
	private Long userId;
	private Long employeeid;
	private String userRoleName;
	private String isMailSent;
	private String userEmail;
	private String userCode;
	private String fileUrl;
	private String mobilePhone;
	private String reportingManagerName;
	private String employeMentType;
	private String userState;
	private String orgName;
	private Long mutualCount;
	private Date createdDate;
	private Integer sentCount;
	private Integer receivedCount;
	private Integer connectedCount;
	private String connectionUUID;
	private String userType;
	private String noOfActivites;
	private String orgClients;
	private String orgVendors;
	private String orgManagers;
	private String orgTeams;
	private String orgDepartments;
	private String isGlobalUser;
	private Long orgId;
	private BigInteger orgLocationId;
	private String type;
	private String clients;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public String getIsMailSent() {
		return isMailSent;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public String getReportingManagerName() {
		return reportingManagerName;
	}
	public String getEmployeMentType() {
		return employeMentType;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}
	public void setEmployeMentType(String employeMentType) {
		this.employeMentType = employeMentType;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getMutualCount() {
		return mutualCount;
	}
	public void setMutualCount(Long mutualCount) {
		this.mutualCount = mutualCount;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getSentCount() {
		return sentCount;
	}
	public void setSentCount(Integer sentCount) {
		this.sentCount = sentCount;
	}
	public Integer getReceivedCount() {
		return receivedCount;
	}
	public void setReceivedCount(Integer receivedCount) {
		this.receivedCount = receivedCount;
	}
	public Integer getConnectedCount() {
		return connectedCount;
	}
	public void setConnectedCount(Integer connectedCount) {
		this.connectedCount = connectedCount;
	}
	public String getConnectionUUID() {
		return connectionUUID;
	}
	public void setConnectionUUID(String connectionUUID) {
		this.connectionUUID = connectionUUID;
	}
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public String getNoOfActivites()
	{
		return noOfActivites;
	}
	public void setNoOfActivites(String noOfActivites)
	{
		this.noOfActivites = noOfActivites;
	}
	public String getOrgClients() {
		return orgClients;
	}
	public void setOrgClients(String orgClients) {
		this.orgClients = orgClients;
	}
	public String getOrgManagers() {
		return orgManagers;
	}
	public void setOrgManagers(String orgManagers) {
		this.orgManagers = orgManagers;
	}
	public String getOrgTeams() {
		return orgTeams;
	}
	public void setOrgTeams(String orgTeams) {
		this.orgTeams = orgTeams;
	}
	public String getOrgDepartments() {
		return orgDepartments;
	}
	public void setOrgDepartments(String orgDepartments) {
		this.orgDepartments = orgDepartments;
	}
	public String getIsGlobalUser() {
		return isGlobalUser;
	}
	public void setIsGlobalUser(String isGlobalUser) {
		this.isGlobalUser = isGlobalUser;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;

	}
	public String getClients()
	{
		return clients;
	}
	public void setClients(String clients)
	{
		this.clients = clients;
	}
	public String getOrgVendors() {
		return orgVendors;
	}
	public void setOrgVendors(String orgVendors) {
		this.orgVendors = orgVendors;
	}
	
	
}
