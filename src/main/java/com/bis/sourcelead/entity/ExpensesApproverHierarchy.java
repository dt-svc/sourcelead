package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
* This class is used to keep track of the approver hierarchy for Expenses.
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modifier|   |Comments|
*          -----------   ----------   ---------- 
* @version    1.0       Pradeep Reddy  
* 
* @since   2017-06-02
*/

@Entity
@Table(name = "EXPENSES_ENTRIES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ExpensesEntries")
public class ExpensesApproverHierarchy implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;

@ManyToOne
@JoinColumn(name="PROJECT_APPROVER_HIERARCHY", nullable=false )        
private Expenses hierarchyId;

@ManyToOne
@JoinColumn(name="EXPENSES", nullable=false )        
private Expenses expenseId;

@Column(name = "EXPENSE_STATUS")
private int expenseStatus;

@Column(name = "EXPENSE_APPROVALSTATUS")
private int expenseApprovalStatus;

@Column(name = "REJECTED_REASON", columnDefinition = "TEXT default NULL")
private String rejectedReason;

@Column(name = "ACTIONPERFORMEDBY", columnDefinition = "TEXT default NULL")
private String actionPerformed;

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

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Expenses getHierarchyId() {
	return hierarchyId;
}

public void setHierarchyId(Expenses hierarchyId) {
	this.hierarchyId = hierarchyId;
}

public Expenses getExpenseId() {
	return expenseId;
}

public void setExpenseId(Expenses expenseId) {
	this.expenseId = expenseId;
}

public int getExpenseStatus() {
	return expenseStatus;
}

public void setExpenseStatus(int expenseStatus) {
	this.expenseStatus = expenseStatus;
}

public int getExpenseApprovalStatus() {
	return expenseApprovalStatus;
}

public void setExpenseApprovalStatus(int expenseApprovalStatus) {
	this.expenseApprovalStatus = expenseApprovalStatus;
}

public String getRejectedReason() {
	return rejectedReason;
}

public void setRejectedReason(String rejectedReason) {
	this.rejectedReason = rejectedReason;
}

public String getActionPerformed() {
	return actionPerformed;
}

public void setActionPerformed(String actionPerformed) {
	this.actionPerformed = actionPerformed;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
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

}
	