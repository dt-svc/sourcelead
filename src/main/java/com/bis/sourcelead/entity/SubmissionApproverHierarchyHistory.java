package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Approval_Mechanism_Type;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;

public class SubmissionApproverHierarchyHistory {

	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "HIERARCHY_ID", nullable = false)
	private ProjectApproverHierarchy hierarchyId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "SUBMISSION_ID", nullable = false)
	private TimesheetSubmissions submissionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBMISSION_APPROVER_HIERARCHY_ID", nullable = false)
	private SubmissionApproverHierarchy submissionApproverHierarchyId;
	
	
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status submissionStatus;
	
	@Column(name = "SUBMISSION_APPROVALSTATUS")
	@Enumerated(EnumType.STRING)
	private Submission_ApprovalStatus submissionApprovalStatus;
	
	 
	@Column(name = "INVOICE_STATUS")
    private String invoiceStatus;
	
	@Column(name = "BILLING_STATUS")
    private String billingStatus;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "INVOICE_ID", nullable = false)
	private Invoice invoiceId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "BILL_ID", nullable = false)
	private Invoice billId;
	
	@Column(name = "FREQUENCY_START_DATE")
    private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE")
    private Date frequencyEndDate;
	
	@Column(name = "MECHANISM_TYPE")
	@Enumerated(EnumType.STRING)
	private Approval_Mechanism_Type mechanismType;
	
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
	
	@Column(name = "ACTIONPERFORMEDBY")
	@Type(type="text")
	private String actionPerformedBy;
	
	@Column(name = "ACTION_BY_ORGLOC_ID")
	private Long actionPerformedByOrgLocationId;
	
	@Column(name = "STATUS")
	private String status;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "REOPENED_USER_ID", nullable = false)
	private User reopenedUser;
	
	@Column(name = "REOPEN_REASON")
	@Type(type="text")
	private String reopenReason;
	
	@Column(name = "REOPEN_REJECTED_REASON")
	@Type(type="text")
	private String reopenRejectedReason;
	
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

	public ProjectApproverHierarchy getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(ProjectApproverHierarchy hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public TimesheetSubmissions getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(TimesheetSubmissions submissionId) {
		this.submissionId = submissionId;
	}

	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyId() {
		return submissionApproverHierarchyId;
	}

	public void setSubmissionApproverHierarchyId(SubmissionApproverHierarchy submissionApproverHierarchyId) {
		this.submissionApproverHierarchyId = submissionApproverHierarchyId;
	}

	public Submission_Status getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(Submission_Status submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public Submission_ApprovalStatus getSubmissionApprovalStatus() {
		return submissionApprovalStatus;
	}

	public void setSubmissionApprovalStatus(Submission_ApprovalStatus submissionApprovalStatus) {
		this.submissionApprovalStatus = submissionApprovalStatus;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Invoice getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Invoice invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Invoice getBillId() {
		return billId;
	}

	public void setBillId(Invoice billId) {
		this.billId = billId;
	}

	public Date getFrequencyStartDate() {
		return frequencyStartDate;
	}

	public void setFrequencyStartDate(Date frequencyStartDate) {
		this.frequencyStartDate = frequencyStartDate;
	}

	public Date getFrequencyEndDate() {
		return frequencyEndDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate) {
		this.frequencyEndDate = frequencyEndDate;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getActionPerformedBy() {
		return actionPerformedBy;
	}

	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}

	public Long getActionPerformedByOrgLocationId() {
		return actionPerformedByOrgLocationId;
	}

	public void setActionPerformedByOrgLocationId(Long actionPerformedByOrgLocationId) {
		this.actionPerformedByOrgLocationId = actionPerformedByOrgLocationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Approval_Mechanism_Type getMechanismType() {
		return mechanismType;
	}

	public void setMechanismType(Approval_Mechanism_Type mechanismType) {
		this.mechanismType = mechanismType;
	}
	

	
}
