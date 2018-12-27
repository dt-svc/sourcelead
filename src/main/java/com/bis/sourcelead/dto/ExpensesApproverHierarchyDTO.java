package com.bis.sourcelead.dto;

import java.math.BigDecimal;

public class ExpensesApproverHierarchyDTO {
	

private static final long serialVersionUID = 1L;
private String expenseId;
private String locationsCode,expenseStartDateString,expenseEndDateString,expensesApproverHierarchyPurpose,expensesProjectName,clientName;
private String userFileUrl,userTypeVendorName,expenseEmpUserType,expenseEmpUserFirstNameLastName,expenseEmpUserEmail;
private String reopenRejectedReason,rejectedReason,expensesStatus,expenseApprovalStatus;
private Long expensesUserid;
private BigDecimal totalAmount;
private String tdSpanLength;
private String actionPerformedBy;
private String noOfExpenses;
private Long expId;
private Long projectMemberId;
private String orgRelationUUID;
private String reopenReason;
private String billingStatus;



public String getBillingStatus() {
	return billingStatus;
}
public void setBillingStatus(String billingStatus) {
	this.billingStatus = billingStatus;
}
public String getReopenReason() {
	return reopenReason;
}
public void setReopenReason(String reopenReason) {
	this.reopenReason = reopenReason;
}
public String getExpenseId() {
	return expenseId;
}
public void setExpenseId(String expenseId) {
	this.expenseId = expenseId;
}
public String getLocationsCode() {
	return locationsCode;
}
public void setLocationsCode(String locationsCode) {
	this.locationsCode = locationsCode;
}
public String getExpenseStartDateString() {
	return expenseStartDateString;
}
public void setExpenseStartDateString(String expenseStartDateString) {
	this.expenseStartDateString = expenseStartDateString;
}
public String getExpenseEndDateString() {
	return expenseEndDateString;
}
public void setExpenseEndDateString(String expenseEndDateString) {
	this.expenseEndDateString = expenseEndDateString;
}
public String getExpensesApproverHierarchyPurpose() {
	return expensesApproverHierarchyPurpose;
}
public void setExpensesApproverHierarchyPurpose(String expensesApproverHierarchyPurpose) {
	this.expensesApproverHierarchyPurpose = expensesApproverHierarchyPurpose;
}
public String getExpensesProjectName() {
	return expensesProjectName;
}
public void setExpensesProjectName(String expensesProjectName) {
	this.expensesProjectName = expensesProjectName;
}
public String getUserFileUrl() {
	return userFileUrl;
}
public void setUserFileUrl(String userFileUrl) {
	this.userFileUrl = userFileUrl;
}
public Long getExpensesUserid() {
	return expensesUserid;
}
public void setExpensesUserid(Long expensesUserid) {
	this.expensesUserid = expensesUserid;
}
public String getUserTypeVendorName() {
	return userTypeVendorName;
}
public void setUserTypeVendorName(String userTypeVendorName) {
	this.userTypeVendorName = userTypeVendorName;
}
public String getExpenseEmpUserType() {
	return expenseEmpUserType;
}
public void setExpenseEmpUserType(String expenseEmpUserType) {
	this.expenseEmpUserType = expenseEmpUserType;
}
public String getExpenseEmpUserFirstNameLastName() {
	return expenseEmpUserFirstNameLastName;
}
public void setExpenseEmpUserFirstNameLastName(String expenseEmpUserFirstNameLastName) {
	this.expenseEmpUserFirstNameLastName = expenseEmpUserFirstNameLastName;
}
public String getExpenseEmpUserEmail() {
	return expenseEmpUserEmail;
}
public void setExpenseEmpUserEmail(String expenseEmpUserEmail) {
	this.expenseEmpUserEmail = expenseEmpUserEmail;
}
public String getReopenRejectedReason() {
	return reopenRejectedReason;
}
public void setReopenRejectedReason(String reopenRejectedReason) {
	this.reopenRejectedReason = reopenRejectedReason;
}
public String getRejectedReason() {
	return rejectedReason;
}
public void setRejectedReason(String rejectedReason) {
	this.rejectedReason = rejectedReason;
}
public String getExpensesStatus() {
	return expensesStatus;
}
public void setExpensesStatus(String expensesStatus) {
	this.expensesStatus = expensesStatus;
}
public String getClientName() {
	return clientName;
}
public void setClientName(String clientName) {
	this.clientName = clientName;
}

public BigDecimal getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}
public String getTdSpanLength() {
	return tdSpanLength;
}
public void setTdSpanLength(String tdSpanLength) {
	this.tdSpanLength = tdSpanLength;
}
public String getActionPerformedBy() {
	return actionPerformedBy;
}
public void setActionPerformedBy(String actionPerformedBy) {
	this.actionPerformedBy = actionPerformedBy;
}
public String getNoOfExpenses() {
	return noOfExpenses;
}
public void setNoOfExpenses(String noOfExpenses) {
	this.noOfExpenses = noOfExpenses;
}
public String getExpenseApprovalStatus() {
	return expenseApprovalStatus;
}
public void setExpenseApprovalStatus(String expenseApprovalStatus) {
	this.expenseApprovalStatus = expenseApprovalStatus;
}
public Long getExpId() {
	return expId;
}
public void setExpId(Long expId) {
	this.expId = expId;
}
public Long getProjectMemberId() {
	return projectMemberId;
}
public void setProjectMemberId(Long projectMemberId) {
	this.projectMemberId = projectMemberId;
}
public String getOrgRelationUUID() {
	return orgRelationUUID;
}
public void setOrgRelationUUID(String orgRelationUUID) {
	this.orgRelationUUID = orgRelationUUID;
}

}
