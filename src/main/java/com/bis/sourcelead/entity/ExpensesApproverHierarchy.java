package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* This class is used to keep track of the approver hierarchy for Expenses.
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version    1.0        Pradeep Reddy     New_File       New_File
* 
* @since   2017-06-02
*/

/**
 * @author BIS101
 *
 */
@Entity
@Table(name = "EXPENSES_APPROVER_HIERARCHY")
public class ExpensesApproverHierarchy implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;

@NotFound(action=NotFoundAction.IGNORE)
@ManyToOne
@Fetch(FetchMode.SELECT)
@JoinColumn(name="HIERARCHY_ID", nullable=false )        
private ExpenseProjectApproverHierarchy hierarchyId;

@NotFound(action=NotFoundAction.IGNORE)
@ManyToOne
@Fetch(FetchMode.SELECT)
@JoinColumn(name="EXPENSESID", nullable=false )        
private Expenses expenseId;

@Column(name = "EXPENSE_STATUS")
@Enumerated(EnumType.STRING)
private Submission_Status expenseStatus;

@Column(name = "EXPENSE_APPROVAL_STATUS")
@Enumerated(EnumType.STRING)
private Submission_ApprovalStatus expenseApprovalStatus;


@Column(name = "REJECTED_REASON", columnDefinition = "TEXT default NULL")
private String rejectedReason;

@Column(name = "ACTIONPERFORMEDBY", columnDefinition = "TEXT default NULL")
private String actionPerformedBy;

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

@Column(name = "ACTION_BY_ORGLOC_ID")
private Long actionbyorglocid;

@NotFound(action=NotFoundAction.IGNORE)
@ManyToOne
@Fetch(FetchMode.SELECT)
@JoinColumn(name = "REOPENED_USER_ID", nullable = false)
private User reopenedUser;

@Column(name = "REOPEN_REASON")
@Type(type="text")
private String reopenReason;

@Column(name = "REOPEN_REJECTED_REASON")
@Type(type="text")
private String reopenRejectedReason;

@Transient
@JsonProperty
private long expensesUserid;

@Transient
@JsonProperty
private String expenseStartDateString;

@Transient
@JsonProperty
private String expenseEndDateString;

@Transient
@JsonProperty
private String expenseEmpUserFirstNameLastName;

@Transient
@JsonProperty
private String expenseEmpUserEmail;

@Transient
@JsonProperty
private String expensesEmployeeUserId;

@Transient
@JsonProperty
private String expenseEmpUserType;

@Transient
@JsonProperty
private String expensesProjectName; 

@Transient
@JsonProperty
private String userTypeVendorName;

@Transient
@JsonProperty
private String expensesApproverHierarchyPurpose; 

@Transient
@JsonProperty
private String expenseLocationCode;

@Transient
@JsonProperty
private String userFileUrl; 

@Transient
private String empType; 

public Long getId()
{
	return id;
}

public void setId(Long id)
{
	this.id = id;
}

public ExpenseProjectApproverHierarchy getHierarchyId()
{
	return hierarchyId;
}

public void setHierarchyId(ExpenseProjectApproverHierarchy hierarchyId)
{
	this.hierarchyId = hierarchyId;
}

public Expenses getExpenseId()
{
	return expenseId;
}

public void setExpenseId(Expenses expenseId)
{
	this.expenseId = expenseId;
}
public Submission_Status getExpenseStatus() {
	return expenseStatus;
}

public void setExpenseStatus(Submission_Status expenseStatus) {
	this.expenseStatus = expenseStatus;
}

public Submission_ApprovalStatus getExpenseApprovalStatus() {
	return expenseApprovalStatus;
}

public void setExpenseApprovalStatus(Submission_ApprovalStatus expenseApprovalStatus) {
	this.expenseApprovalStatus = expenseApprovalStatus;
}

public String getRejectedReason()
{
	return rejectedReason;
}

public void setRejectedReason(String rejectedReason)
{
	this.rejectedReason = rejectedReason;
}

public String getStatus()
{
	return status;
}

public void setStatus(String status)
{
	this.status = status;
}

public Date getCreatedDate()
{
	return createdDate;
}

public void setCreatedDate(Date createdDate)
{
	this.createdDate = createdDate;
}

public String getCreatedBy()
{
	return createdBy;
}

public void setCreatedBy(String createdBy)
{
	this.createdBy = createdBy;
}

public Date getUpdatedDate()
{
	return updatedDate;
}

public void setUpdatedDate(Date updatedDate)
{
	this.updatedDate = updatedDate;
}

public String getUpdatedBy()
{
	return updatedBy;
}

public void setUpdatedBy(String updatedBy)
{
	this.updatedBy = updatedBy;
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

public String getExpensesEmployeeUserId() {
	return expensesEmployeeUserId;
}

public void setExpensesEmployeeUserId(String expensesEmployeeUserId) {
	this.expensesEmployeeUserId = expensesEmployeeUserId;
}

public String getExpenseEmpUserType() {
	return expenseEmpUserType;
}

public void setExpenseEmpUserType(String expenseEmpUserType) {
	this.expenseEmpUserType = expenseEmpUserType;
}

public String getExpensesProjectName() {
	return expensesProjectName;
}

public void setExpensesProjectName(String expensesProjectName) {
	this.expensesProjectName = expensesProjectName;
}

public String getUserTypeVendorName() {
	return userTypeVendorName;
}

public void setUserTypeVendorName(String userTypeVendorName) {
	this.userTypeVendorName = userTypeVendorName;
}


public String getActionPerformedBy() {
	return actionPerformedBy;
}

public void setActionPerformedBy(String actionPerformedBy) {
	this.actionPerformedBy = actionPerformedBy;
}

public User getReopenedUser() {
	return reopenedUser;
}

public void setReopenedUser(User reopenedUser) {
	this.reopenedUser = reopenedUser;
}

public String getReopenReason() {
	return reopenReason;
}

public void setReopenReason(String reopenReason) {
	this.reopenReason = reopenReason;
}

public String getReopenRejectedReason() {
	return reopenRejectedReason;
}

public void setReopenRejectedReason(String reopenRejectedReason) {
	this.reopenRejectedReason = reopenRejectedReason;
}


public long getExpensesUserid() {
	return expensesUserid;
}

public void setExpensesUserid(long expensesUserid) {
	this.expensesUserid = expensesUserid;
}

public String getExpensesApproverHierarchyPurpose() {
	return expensesApproverHierarchyPurpose;
}

public void setExpensesApproverHierarchyPurpose(String expensesApproverHierarchyPurpose) {
	this.expensesApproverHierarchyPurpose = expensesApproverHierarchyPurpose;
}

public String getUserFileUrl() {
	return userFileUrl;
}

public void setUserFileUrl(String userFileUrl) {
	this.userFileUrl = userFileUrl;
}

public String getExpenseLocationCode() {
	return expenseLocationCode;
}

public void setExpenseLocationCode(String expenseLocationCode) {
	this.expenseLocationCode = expenseLocationCode;
}

public Long getActionbyorglocid() {
	return actionbyorglocid;
}

public void setActionbyorglocid(Long actionbyorglocid) {
	this.actionbyorglocid = actionbyorglocid;
}

public String getEmpType() {
	return empType;
}

public void setEmpType(String empType) {
	this.empType = empType;
}


}	