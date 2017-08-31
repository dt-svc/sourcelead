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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.enums.Approval_Mechanism_Type;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;

@Entity
@Table(name = "SUBMISSION_APPROVER_HIERARCHY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "submissionApproverHeirarchy")
public class SubmissionApproverHierarchy implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "HIERARCHY_ID", nullable = false)
	private ProjectApproverHierarchy hierarchyId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
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
	
	@Column(name = "STATUS")
	private String status;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
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
	private Float previousMonthBillableHours;
    
    @Transient
	private Float previousMonthNonBillableHours;
    
    @Transient
	private Float previousMonthTotalHours;
    
    @Transient
	private Float projectToDateBillableHours;
    
    @Transient
	private Float projectToDateNonBillableHours;
    
    @Transient
	private Float projectToDateTotalHours;
    
	

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

	public Float getPreviousMonthBillableHours() {
		return previousMonthBillableHours;
	}

	public void setPreviousMonthBillableHours(Float previousMonthBillableHours) {
		this.previousMonthBillableHours = previousMonthBillableHours;
	}

	public Float getPreviousMonthNonBillableHours() {
		return previousMonthNonBillableHours;
	}

	public void setPreviousMonthNonBillableHours(Float previousMonthNonBillableHours) {
		this.previousMonthNonBillableHours = previousMonthNonBillableHours;
	}

	public Float getPreviousMonthTotalHours() {
		return previousMonthTotalHours;
	}

	public void setPreviousMonthTotalHours(Float previousMonthTotalHours) {
		this.previousMonthTotalHours = previousMonthTotalHours;
	}

	public Float getProjectToDateBillableHours() {
		return projectToDateBillableHours;
	}

	public void setProjectToDateBillableHours(Float projectToDateBillableHours) {
		this.projectToDateBillableHours = projectToDateBillableHours;
	}

	public Float getProjectToDateNonBillableHours() {
		return projectToDateNonBillableHours;
	}

	public void setProjectToDateNonBillableHours(Float projectToDateNonBillableHours) {
		this.projectToDateNonBillableHours = projectToDateNonBillableHours;
	}

	public Float getProjectToDateTotalHours() {
		return projectToDateTotalHours;
	}

	public void setProjectToDateTotalHours(Float projectToDateTotalHours) {
		this.projectToDateTotalHours = projectToDateTotalHours;
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


	
	
	
}
