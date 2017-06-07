package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;

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
@Table(name = "EXPENSES_APPROVER_HIERARCHY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ExpensesApproverHierarchy")
public class ExpensesApproverHierarchy implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;

@ManyToOne
@JoinColumn(name="HIERARCHY_ID", nullable=false )        
private ProjectApproverHierarchy hierarchyId;

@ManyToOne
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

@Column(name = "ACTION_PERFORMEDBY", columnDefinition = "TEXT default NULL")
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

public Long getId()
{
	return id;
}

public void setId(Long id)
{
	this.id = id;
}

public ProjectApproverHierarchy getHierarchyId()
{
	return hierarchyId;
}

public void setHierarchyId(ProjectApproverHierarchy hierarchyId)
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

public String getActionPerformed()
{
	return actionPerformed;
}

public void setActionPerformed(String actionPerformed)
{
	this.actionPerformed = actionPerformed;
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


}	