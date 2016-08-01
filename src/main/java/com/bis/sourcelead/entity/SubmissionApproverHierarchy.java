package com.bis.sourcelead.entity;

import java.util.Date;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Approval_Mechanism_Type;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;

@Entity
@Table(name = "SUBMISSION_APPROVER_HIERARCHY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "submissionApproverHeirarchy")
public class SubmissionApproverHierarchy {
	  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "HIERARCHY_ID", nullable = false)
	private ProjectApproverHierarchy hierarchyId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "SUBMISSION_ID", nullable = false)
	private TimesheetSubmissions submissionId;
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status submissionStatus;
	
	@Column(name = "SUBMISSION_APPROVALSTATUS")
	@Enumerated(EnumType.STRING)
	private Submission_ApprovalStatus submissionApprovalStatus;
	
	@Column(name = "MECHANISM_TYPE")
	@Enumerated(EnumType.STRING)
	private Approval_Mechanism_Type mechanismType;
	
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
	
	@Column(name = "ACTIONPERFORMEDBY")
	@Type(type="text")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectApproverHierarchy getHeirarchyId() {
		return hierarchyId;
	}

	public void setHeirarchyId(ProjectApproverHierarchy hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public TimesheetSubmissions getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(TimesheetSubmissions submissionId) {
		this.submissionId = submissionId;
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

	public Approval_Mechanism_Type getMechanismType() {
		return mechanismType;
	}

	public void setMechanismType(Approval_Mechanism_Type mechanismType) {
		this.mechanismType = mechanismType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
}
