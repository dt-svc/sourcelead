package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author BIS_Prasad
 *
 */
@Entity
@Table(name = "EXPENSES_HISTORY")
public class ExpensesHistory implements Serializable {

private static final long serialVersionUID = 1L;


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="EXPENSE_ID", nullable=false )        
private Expenses expenseId;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="LOCATION_ID", nullable=false )        
private OrgLocations orgLocationId;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "PROJECT_MEMBERS_ID", nullable=false)
private ProjectMembers projectMembers;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "EXPENSE_ENTRY_ID", nullable=false)
private ExpensesEntries expensesEntries;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "EXPENSE_CATEGORY_ID", nullable=false)
private ExpensesCategory expensesCategory;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "EXPENSE_APPROVER_HIERARCHY_ID", nullable=false)
private ExpensesApproverHierarchy expensesApproverHierarchy;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "UPDATED_BY_USER", nullable=false)
private User user;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "EXPENSE_ENTRIES_DOCUMENTS_ID", nullable=false)
private ExpensesEntryDocuments expensesEntryDocuments;

@Column(name = "CLIENT_NAME")
private String clientName;

@Column(name = "START_DATE")
private Date startDate;

@Column(name = "END_DATE")
private Date endDate;

@Column(name = "EXPENSE_STATUS_OLD")
private String expenseStatusId;

@Column(name = "EXPENSE_STATUS_NEW")
private String expensStatusNew;

@Column(name = "ENTRY_DATE")
private Date entryDate;

@Column(name = "ENTRY_PAYMENT_MODE")
private String entryPaymentMode;

@Column(name = "ENTRY_RECEIPT_ISSUED_BY")
private String entryReceiptIssuedBy;

@Column(name = "ENTRY_CURRENCY_TYPE")
private String entryCurrencyType;

@Column(name = "ENTRY_AMOUNT")
private float entryAmount;

@Column(name = "ENTRY_DESCRIPTION" , columnDefinition = "TEXT default NULL")
private String entryDescription;

@Column(name = "ENTRY_STATUS")
private String entryStatus;


@Column(name = "EXPENSE_APPROVAL_STATUS_OLD")
private String expenseApprovalStatusOld;

@Column(name = "EXPENSE_APPROVAL_STATUS_NEW")
private String expenseApprovalStatusNew;

@Column(name = "PURPOSE" , columnDefinition = "TEXT default NULL")
private String purpose;

@Column(name = "EXPENSE_APPROVAL_STATUS_REASON" , columnDefinition = "TEXT default NULL")
private String expenseApprovalStatusReason;

@Column(name = "UPDATED_DATE")
private Date updatedDate;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Expenses getExpenseId() {
	return expenseId;
}

public void setExpenseId(Expenses expenseId) {
	this.expenseId = expenseId;
}

public OrgLocations getOrgLocationId() {
	return orgLocationId;
}

public void setOrgLocationId(OrgLocations orgLocationId) {
	this.orgLocationId = orgLocationId;
}

public ProjectMembers getProjectMembers() {
	return projectMembers;
}

public void setProjectMembers(ProjectMembers projectMembers) {
	this.projectMembers = projectMembers;
}

public ExpensesEntries getExpensesEntries() {
	return expensesEntries;
}

public void setExpensesEntries(ExpensesEntries expensesEntries) {
	this.expensesEntries = expensesEntries;
}

public ExpensesCategory getExpensesCategory() {
	return expensesCategory;
}

public void setExpensesCategory(ExpensesCategory expensesCategory) {
	this.expensesCategory = expensesCategory;
}

public ExpensesApproverHierarchy getExpensesApproverHierarchy() {
	return expensesApproverHierarchy;
}

public void setExpensesApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) {
	this.expensesApproverHierarchy = expensesApproverHierarchy;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public ExpensesEntryDocuments getExpensesEntryDocuments() {
	return expensesEntryDocuments;
}

public void setExpensesEntryDocuments(ExpensesEntryDocuments expensesEntryDocuments) {
	this.expensesEntryDocuments = expensesEntryDocuments;
}

public String getClientName() {
	return clientName;
}

public void setClientName(String clientName) {
	this.clientName = clientName;
}

public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getEndDate() {
	return endDate;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}

public String getExpenseStatusId() {
	return expenseStatusId;
}

public void setExpenseStatusId(String expenseStatusId) {
	this.expenseStatusId = expenseStatusId;
}

public String getExpensStatusNew() {
	return expensStatusNew;
}

public void setExpensStatusNew(String expensStatusNew) {
	this.expensStatusNew = expensStatusNew;
}

public Date getEntryDate() {
	return entryDate;
}

public void setEntryDate(Date entryDate) {
	this.entryDate = entryDate;
}

public String getEntryPaymentMode() {
	return entryPaymentMode;
}

public void setEntryPaymentMode(String entryPaymentMode) {
	this.entryPaymentMode = entryPaymentMode;
}

public String getEntryReceiptIssuedBy() {
	return entryReceiptIssuedBy;
}

public void setEntryReceiptIssuedBy(String entryReceiptIssuedBy) {
	this.entryReceiptIssuedBy = entryReceiptIssuedBy;
}

public String getEntryCurrencyType() {
	return entryCurrencyType;
}

public void setEntryCurrencyType(String entryCurrencyType) {
	this.entryCurrencyType = entryCurrencyType;
}

public float getEntryAmount() {
	return entryAmount;
}

public void setEntryAmount(float entryAmount) {
	this.entryAmount = entryAmount;
}

public String getEntryDescription() {
	return entryDescription;
}

public void setEntryDescription(String entryDescription) {
	this.entryDescription = entryDescription;
}

public String getEntryStatus() {
	return entryStatus;
}

public void setEntryStatus(String entryStatus) {
	this.entryStatus = entryStatus;
}

public String getExpenseApprovalStatusOld() {
	return expenseApprovalStatusOld;
}

public void setExpenseApprovalStatusOld(String expenseApprovalStatusOld) {
	this.expenseApprovalStatusOld = expenseApprovalStatusOld;
}

public String getExpenseApprovalStatusNew() {
	return expenseApprovalStatusNew;
}

public void setExpenseApprovalStatusNew(String expenseApprovalStatusNew) {
	this.expenseApprovalStatusNew = expenseApprovalStatusNew;
}

public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}

public String getExpenseApprovalStatusReason() {
	return expenseApprovalStatusReason;
}

public void setExpenseApprovalStatusReason(String expenseApprovalStatusReason) {
	this.expenseApprovalStatusReason = expenseApprovalStatusReason;
}

public Date getUpdatedDate() {
	return updatedDate;
}

public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}


}	