package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

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

import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Approval_Mechanism_Type;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SUBMISSION_APPROVER_HIERARCHY")
public class SubmissionApproverHierarchy implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
   /*###################################### Fields Added For De-Normalization ###################################*/
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projectId;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_MEMBER_ID", nullable = false)
	private ProjectMembers projectMemberId;
	
	@Column(name = "MASTER_CANDIDATE_ID", nullable=false)
	private Long masterCandidateId;
	
	@Column(name = "CANDIDATE_ID", nullable=false)
	private Long candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CONSULTANT_USER_ID")
	private User consultantUser;

	@Column(name = "ORG_LOCATION_ID", nullable=false)
	private Long orgLocationId;

	@Column(name = "ORG_ID", nullable=false)
	private Long orgId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PROJECT_CLIENT_ID", nullable=false)
    private ProjectClients projectClientId;
	
	/* ######################################***********************************#####################################*/
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "HIERARCHY_ID", nullable = false)
	private ProjectApproverHierarchy hierarchyId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "SUBMISSION_ID", nullable = false)
	private TimesheetSubmissions submissionId;
	
	
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
	
	@Transient
	private String managerLevel1;
	    
	@Transient
	private String managerLevel2;
	
	@Transient
	private String projectName;
	
	@Transient
	private String employeeName;
	
	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	
	@Transient
	private Float totalHours;
	
	@Transient
	private Float billableHours;
	
	@Transient
	private Float nonBillableHours;
	
	@Transient
	private LinkedHashMap<Date,Float> weekTotals;
	
    @Transient
	private LinkedHashMap<Date,Submission_ApprovalStatus> weekApprovalStatusMap;
    
    @Transient
	private LinkedHashMap<Date,Float> weekBillableHoursMap;
    
    @Transient
	private LinkedHashMap<Date,Float> weekNonBillableHoursMap;
    
    @Transient
	private LinkedHashMap<Date,Float> weekTotalHoursMap;
    
    
    @Transient
	private float previousMonthBillableHours;
    
    @Transient
	private float previousMonthNonBillableHours;
    
    @Transient
	private float previousMonthTotalHours;
    
    @Transient
	private float projectToDateBillableHours;
    
    @Transient
	private float projectToDateNonBillableHours;
    
    @Transient
	private float projectToDateTotalHours;
    
	@Transient
	private float currentMonthBillableHours;

	@Transient
	private float currentMonthNonBillableHours;

	@Transient
	private float currentMonthTotalHours;
	
	@Transient
	@JsonProperty
	@JsonBackReference
	private Long timesheetActivityLoginUser;

	
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

	public String getManagerLevel1() {
		return managerLevel1;
	}

	public void setManagerLevel1(String managerLevel1) {
		this.managerLevel1 = managerLevel1;
	}

	public String getManagerLevel2() {
		return managerLevel2;
	}

	public void setManagerLevel2(String managerLevel2) {
		this.managerLevel2 = managerLevel2;
	}

	public ProjectApproverHierarchy getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(ProjectApproverHierarchy hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Float getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Float totalHours) {
		this.totalHours = totalHours;
	}

	public LinkedHashMap<Date, Float> getWeekTotals() {
		return weekTotals;
	}

	public void setWeekTotals(LinkedHashMap<Date, Float> weekTotals) {
		this.weekTotals = weekTotals;
	}

	public LinkedHashMap<Date, Submission_ApprovalStatus> getWeekApprovalStatusMap() {
		return weekApprovalStatusMap;
	}

	public void setWeekApprovalStatusMap(LinkedHashMap<Date, Submission_ApprovalStatus> weekApprovalStatusMap) {
		this.weekApprovalStatusMap = weekApprovalStatusMap;
	}

	public LinkedHashMap<Date, Float> getWeekBillableHoursMap() {
		return weekBillableHoursMap;
	}

	public void setWeekBillableHoursMap(LinkedHashMap<Date, Float> weekBillableHoursMap) {
		this.weekBillableHoursMap = weekBillableHoursMap;
	}

	public LinkedHashMap<Date, Float> getWeekNonBillableHoursMap() {
		return weekNonBillableHoursMap;
	}

	public void setWeekNonBillableHoursMap(LinkedHashMap<Date, Float> weekNonBillableHoursMap) {
		this.weekNonBillableHoursMap = weekNonBillableHoursMap;
	}

	public LinkedHashMap<Date, Float> getWeekTotalHoursMap() {
		return weekTotalHoursMap;
	}

	public void setWeekTotalHoursMap(LinkedHashMap<Date, Float> weekTotalHoursMap) {
		this.weekTotalHoursMap = weekTotalHoursMap;
	}

	public Float getBillableHours()
	{
		return billableHours;
	}

	public void setBillableHours(Float billableHours)
	{
		this.billableHours = billableHours;
	}

	public Float getNonBillableHours()
	{
		return nonBillableHours;
	}

	public void setNonBillableHours(Float nonBillableHours)
	{
		this.nonBillableHours = nonBillableHours;
	}

	public String getInvoiceStatus()
	{
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus)
	{
		this.invoiceStatus = invoiceStatus;
	}

	public String getBillingStatus()
	{
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus)
	{
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getActionPerformedByOrgLocationId() {
		return actionPerformedByOrgLocationId;
	}

	public void setActionPerformedByOrgLocationId(Long actionPerformedByOrgLocationId) {
		this.actionPerformedByOrgLocationId = actionPerformedByOrgLocationId;
	}

	public float getPreviousMonthBillableHours() {
		return previousMonthBillableHours;
	}

	public void setPreviousMonthBillableHours(float previousMonthBillableHours) {
		this.previousMonthBillableHours = previousMonthBillableHours;
	}

	public float getPreviousMonthNonBillableHours() {
		return previousMonthNonBillableHours;
	}

	public void setPreviousMonthNonBillableHours(float previousMonthNonBillableHours) {
		this.previousMonthNonBillableHours = previousMonthNonBillableHours;
	}

	public float getPreviousMonthTotalHours() {
		return previousMonthTotalHours;
	}

	public void setPreviousMonthTotalHours(float previousMonthTotalHours) {
		this.previousMonthTotalHours = previousMonthTotalHours;
	}

	public float getProjectToDateBillableHours() {
		return projectToDateBillableHours;
	}

	public void setProjectToDateBillableHours(float projectToDateBillableHours) {
		this.projectToDateBillableHours = projectToDateBillableHours;
	}

	public float getProjectToDateNonBillableHours() {
		return projectToDateNonBillableHours;
	}

	public void setProjectToDateNonBillableHours(float projectToDateNonBillableHours) {
		this.projectToDateNonBillableHours = projectToDateNonBillableHours;
	}

	public float getProjectToDateTotalHours() {
		return projectToDateTotalHours;
	}

	public void setProjectToDateTotalHours(float projectToDateTotalHours) {
		this.projectToDateTotalHours = projectToDateTotalHours;
	}

	public float getCurrentMonthBillableHours() {
		return currentMonthBillableHours;
	}

	public void setCurrentMonthBillableHours(float currentMonthBillableHours) {
		this.currentMonthBillableHours = currentMonthBillableHours;
	}

	public float getCurrentMonthNonBillableHours() {
		return currentMonthNonBillableHours;
	}

	public void setCurrentMonthNonBillableHours(float currentMonthNonBillableHours) {
		this.currentMonthNonBillableHours = currentMonthNonBillableHours;
	}

	public float getCurrentMonthTotalHours() {
		return currentMonthTotalHours;
	}

	public void setCurrentMonthTotalHours(float currentMonthTotalHours) {
		this.currentMonthTotalHours = currentMonthTotalHours;
	}

	public Long getTimesheetActivityLoginUser() {
		return timesheetActivityLoginUser;
	}

	public void setTimesheetActivityLoginUser(Long timesheetActivityLoginUser) {
		this.timesheetActivityLoginUser = timesheetActivityLoginUser;
	}

	public User getConsultantUser() {
		return consultantUser;
	}

	public void setConsultantUser(User consultantUser) {
		this.consultantUser = consultantUser;
	}

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public ProjectClients getProjectClientId() {
		return projectClientId;
	}

	public void setProjectClientId(ProjectClients projectClientId) {
		this.projectClientId = projectClientId;
	}


}
