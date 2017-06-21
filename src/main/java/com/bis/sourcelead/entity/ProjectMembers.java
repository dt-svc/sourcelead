package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PROJECT_MEMBERS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "projectMembers")
public class ProjectMembers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projects;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employees employee;

	/*
	 * @Column(name = "TIMESHEET_FREQUENCY") private String timesheetFrequency;
	 */

	@Column(name = "PROJECT_ASSIGNED_DATE")
	private Date projectAssignedDate;

	@Column(name = "PROJECT_END_DATE")
	private Date projectEndDate;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "CURRENT_BILL_RATE")
	private Float currentBillRate;

	@Column(name = "RATE_TYPE")
	private String rateType;

	@Column(name = "STATUS")
	private String status;

	@Enumerated(EnumType.STRING)
	@Column(name = "IS_MANUALLY_TERMINATED")
	private Primary isManuallyTerminated;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
	private String empName;
	@Transient
	private String empEmail;
	@Transient
	private String startDate;
	@Transient
	private String endDate;
	@Transient
	private String approvedHrs;
	@Transient
	private String rejectedHrs;
	@Transient
	private String openHrs;
	@Transient
	private String draftHrs;
	@Transient
	private String submittedHrs;
	@Transient
	private String projectAssignedDateStr;
	@Transient
	private String projectEndDateStr;
	@Transient
	private String projectName;
	@Transient
	private String userId;
	@Transient
	@Enumerated(EnumType.STRING)
	private Status userState;
    
    @Transient
    private String projectStatus;
    
    @Column(name = "TERMINATE_REASON")
    private String terminateReason;
    
    @Transient
    private String totalHours;
    
    @Transient
    private String candidateName;
    
    @Transient
    private Long candidateId;
    
    @Transient
    private String userRoleType;
    
    @Transient
    private Long submissionApproverHierarchyId;
    
    
    @Transient
    private Long submissionInvoiceId;
	
    //For Expenses 
    
    @Transient
    private Float janExpenseTotal;
    
    @Transient
    private Float janExpenseApprovedTotal;
    
    @Transient
    private Float janExpenseUnApprovedTotal;
    
    
    @Transient
    private Float febExpenseTotal;
    
    @Transient
    private Float febExpenseApprovedTotal;
    
    @Transient
    private Float febExpenseUnApprovedTotal;
    
    @Transient
    private Float marExpenseTotal;
    
    @Transient
    private Float marExpenseApprovedTotal;
    
    @Transient
    private Float marExpenseUnApprovedTotal;
    
    
    @Transient
    private Float aprExpenseTotal;
    
    @Transient
    private Float aprExpenseApprovedTotal;
    
    @Transient
    private Float aprExpenseUnApprovedTotal;
    
    @Transient
    private Float mayExpenseTotal;
    
    @Transient
    private Float mayExpenseApprovedTotal;
    
    @Transient
    private Float mayExpenseUnApprovedTotal;
    
    @Transient
    private Float junExpenseTotal;
    
    @Transient
    private Float junExpenseApprovedTotal;
    
    @Transient
    private Float junExpenseUnApprovedTotal;
    
    @Transient
    private Float julExpenseTotal;
    
    @Transient
    private Float julExpenseApprovedTotal;
    
    @Transient
    private Float julExpenseUnApprovedTotal;
    
    
    @Transient
    private Float augExpenseTotal;
    
    @Transient
    private Float augExpenseApprovedTotal;
    
    @Transient
    private Float augExpenseUnApprovedTotal;
    
    @Transient
    private Float sepExpenseTotal;
    
    @Transient
    private Float sepExpenseApprovedTotal;
    
    @Transient
    private Float sepExpenseUnApprovedTotal;
    
    @Transient
    private Float octExpenseTotal;
    
    @Transient
    private Float octExpenseApprovedTotal;
    
    @Transient
    private Float octExpenseUnApprovedTotal;
    
    @Transient
    private Float novExpenseTotal;
    
    @Transient
    private Float novExpenseApprovedTotal;
    
    @Transient
    private Float novExpenseUnApprovedTotal;
    
    @Transient
    private Float decExpenseTotal;
    
    @Transient
    private Float decExpenseApprovedTotal;
    
    @Transient
    private Float decExpenseUnApprovedTotal;
    
    
    @Transient
    private Float Q1ExpenseTotal;
    
    @Transient
    private Float Q1ExpenseApprovedTotal;
    
    @Transient
    private Float Q1ExpenseUnApprovedTotal;
    
    @Transient
    private Float Q2ExpenseTotal;
    
    @Transient
    private Float Q2ExpenseApprovedTotal;
    
    @Transient
    private Float Q2ExpenseUnApprovedTotal;
    
    @Transient
    private Float Q3ExpenseTotal;
    
    @Transient
    private Float Q3ExpenseApprovedTotal;
    
    @Transient
    private Float Q3ExpenseUnApprovedTotal;
    
    @Transient
    private Float Q4ExpenseTotal;
    
    @Transient
    private Float Q4ExpenseApprovedTotal;
    
    @Transient
    private Float Q4ExpenseUnApprovedTotal;
    
    //For Expenses 
    @Transient
    public Long getSubmissionInvoiceId() {

	@Transient
	private String projectStatus;

	@Column(name = "TERMINATE_REASON")
	private String terminateReason;

	@Transient
	private String totalHours;

	@Transient
	private String candidateName;

	@Transient
	private Long candidateId;

	@Transient
	private String userRoleType;

	@Transient
	private Long submissionApproverHierarchyId;

	@Transient
	private Long submissionInvoiceId;

	public Long getSubmissionInvoiceId() {
		return submissionInvoiceId;
	}

	public void setSubmissionInvoiceId(Long submissionInvoiceId) {
		this.submissionInvoiceId = submissionInvoiceId;
	}

	@Transient
	private List<Contracts> contractsList;

	@JsonIgnore
	@OneToMany(mappedBy = "projectMemberId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	@Fetch(FetchMode.SELECT)
	private Set<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>(0);

	@Transient
	private List<ProjectMembers> projectMembersList;

	public Date getProjectAssignedDate() {
		return projectAssignedDate;
	}

	public void setProjectAssignedDate(Date projectAssignedDate) {
		this.projectAssignedDate = projectAssignedDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public Status getUserState() {
		return userState;
	}

	public void setUserState(Status userState) {
		this.userState = userState;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public String getSubmittedHrs() {
		return submittedHrs;
	}

	public void setSubmittedHrs(String submittedHrs) {
		this.submittedHrs = submittedHrs;
	}

	public String getDraftHrs() {
		return draftHrs;
	}

	public void setDraftHrs(String draftHrs) {
		this.draftHrs = draftHrs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@JsonIgnore
	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	/*
	 * public String getTimesheetFrequency() { return timesheetFrequency; }
	 * 
	 * public void setTimesheetFrequency(String timesheetFrequency) {
	 * this.timesheetFrequency = timesheetFrequency; }
	 */

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Float getCurrentBillRate() {
		return currentBillRate;
	}

	public void setCurrentBillRate(Float currentBillRate) {
		this.currentBillRate = currentBillRate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
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

	public String getApprovedHrs() {
		return approvedHrs;
	}

	public void setApprovedHrs(String approvedHrs) {
		this.approvedHrs = approvedHrs;
	}

	public String getRejectedHrs() {
		return rejectedHrs;
	}

	public void setRejectedHrs(String rejectedHrs) {
		this.rejectedHrs = rejectedHrs;
	}

	public String getOpenHrs() {
		return openHrs;
	}

	public void setOpenHrs(String openHrs) {
		this.openHrs = openHrs;
	}

	public String getProjectAssignedDateStr() {
		return projectAssignedDateStr;
	}

	public void setProjectAssignedDateStr(String projectAssignedDateStr) {
		this.projectAssignedDateStr = projectAssignedDateStr;
	}

	public String getProjectEndDateStr() {
		return projectEndDateStr;
	}

	public void setProjectEndDateStr(String projectEndDateStr) {
		this.projectEndDateStr = projectEndDateStr;
	}

	public Set<ProjectMembersHistory> getMemberHistoryList() {
		return memberHistoryList;
	}

	public void setMemberHistoryList(Set<ProjectMembersHistory> memberHistoryList) {
		this.memberHistoryList = memberHistoryList;
	}

	public Primary getIsManuallyTerminated() {
		return isManuallyTerminated;
	}

	public void setIsManuallyTerminated(Primary isManuallyTerminated) {
		this.isManuallyTerminated = isManuallyTerminated;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public List<ProjectMembers> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembers> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}

	public Long getSubmissionApproverHierarchyId() {
		return submissionApproverHierarchyId;
	}

	public void setSubmissionApproverHierarchyId(Long submissionApproverHierarchyId) {
		this.submissionApproverHierarchyId = submissionApproverHierarchyId;
	}

	public List<Contracts> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<Contracts> contractsList) {
		this.contractsList = contractsList;
	}

	public Float getJanExpenseTotal() {
		return janExpenseTotal;
	}

	public void setJanExpenseTotal(Float janExpenseTotal) {
		this.janExpenseTotal = janExpenseTotal;
	}

	public Float getJanExpenseApprovedTotal() {
		return janExpenseApprovedTotal;
	}

	public void setJanExpenseApprovedTotal(Float janExpenseApprovedTotal) {
		this.janExpenseApprovedTotal = janExpenseApprovedTotal;
	}

	public Float getJanExpenseUnApprovedTotal() {
		return janExpenseUnApprovedTotal;
	}

	public void setJanExpenseUnApprovedTotal(Float janExpenseUnApprovedTotal) {
		this.janExpenseUnApprovedTotal = janExpenseUnApprovedTotal;
	}

	public Float getFebExpenseTotal() {
		return febExpenseTotal;
	}

	public void setFebExpenseTotal(Float febExpenseTotal) {
		this.febExpenseTotal = febExpenseTotal;
	}

	public Float getFebExpenseApprovedTotal() {
		return febExpenseApprovedTotal;
	}

	public void setFebExpenseApprovedTotal(Float febExpenseApprovedTotal) {
		this.febExpenseApprovedTotal = febExpenseApprovedTotal;
	}

	public Float getFebExpenseUnApprovedTotal() {
		return febExpenseUnApprovedTotal;
	}

	public void setFebExpenseUnApprovedTotal(Float febExpenseUnApprovedTotal) {
		this.febExpenseUnApprovedTotal = febExpenseUnApprovedTotal;
	}

	public Float getMarExpenseTotal() {
		return marExpenseTotal;
	}

	public void setMarExpenseTotal(Float marExpenseTotal) {
		this.marExpenseTotal = marExpenseTotal;
	}

	public Float getMarExpenseApprovedTotal() {
		return marExpenseApprovedTotal;
	}

	public void setMarExpenseApprovedTotal(Float marExpenseApprovedTotal) {
		this.marExpenseApprovedTotal = marExpenseApprovedTotal;
	}

	public Float getMarExpenseUnApprovedTotal() {
		return marExpenseUnApprovedTotal;
	}

	public void setMarExpenseUnApprovedTotal(Float marExpenseUnApprovedTotal) {
		this.marExpenseUnApprovedTotal = marExpenseUnApprovedTotal;
	}

	public Float getAprExpenseTotal() {
		return aprExpenseTotal;
	}

	public void setAprExpenseTotal(Float aprExpenseTotal) {
		this.aprExpenseTotal = aprExpenseTotal;
	}

	public Float getAprExpenseApprovedTotal() {
		return aprExpenseApprovedTotal;
	}

	public void setAprExpenseApprovedTotal(Float aprExpenseApprovedTotal) {
		this.aprExpenseApprovedTotal = aprExpenseApprovedTotal;
	}

	public Float getAprExpenseUnApprovedTotal() {
		return aprExpenseUnApprovedTotal;
	}

	public void setAprExpenseUnApprovedTotal(Float aprExpenseUnApprovedTotal) {
		this.aprExpenseUnApprovedTotal = aprExpenseUnApprovedTotal;
	}

	public Float getMayExpenseTotal() {
		return mayExpenseTotal;
	}

	public void setMayExpenseTotal(Float mayExpenseTotal) {
		this.mayExpenseTotal = mayExpenseTotal;
	}

	public Float getMayExpenseApprovedTotal() {
		return mayExpenseApprovedTotal;
	}

	public void setMayExpenseApprovedTotal(Float mayExpenseApprovedTotal) {
		this.mayExpenseApprovedTotal = mayExpenseApprovedTotal;
	}

	public Float getMayExpenseUnApprovedTotal() {
		return mayExpenseUnApprovedTotal;
	}

	public void setMayExpenseUnApprovedTotal(Float mayExpenseUnApprovedTotal) {
		this.mayExpenseUnApprovedTotal = mayExpenseUnApprovedTotal;
	}

	public Float getJunExpenseTotal() {
		return junExpenseTotal;
	}

	public void setJunExpenseTotal(Float junExpenseTotal) {
		this.junExpenseTotal = junExpenseTotal;
	}

	public Float getJunExpenseApprovedTotal() {
		return junExpenseApprovedTotal;
	}

	public void setJunExpenseApprovedTotal(Float junExpenseApprovedTotal) {
		this.junExpenseApprovedTotal = junExpenseApprovedTotal;
	}

	public Float getJunExpenseUnApprovedTotal() {
		return junExpenseUnApprovedTotal;
	}

	public void setJunExpenseUnApprovedTotal(Float junExpenseUnApprovedTotal) {
		this.junExpenseUnApprovedTotal = junExpenseUnApprovedTotal;
	}

	public Float getJulExpenseTotal() {
		return julExpenseTotal;
	}

	public void setJulExpenseTotal(Float julExpenseTotal) {
		this.julExpenseTotal = julExpenseTotal;
	}

	public Float getJulExpenseApprovedTotal() {
		return julExpenseApprovedTotal;
	}

	public void setJulExpenseApprovedTotal(Float julExpenseApprovedTotal) {
		this.julExpenseApprovedTotal = julExpenseApprovedTotal;
	}

	public Float getJulExpenseUnApprovedTotal() {
		return julExpenseUnApprovedTotal;
	}

	public void setJulExpenseUnApprovedTotal(Float julExpenseUnApprovedTotal) {
		this.julExpenseUnApprovedTotal = julExpenseUnApprovedTotal;
	}

	public Float getAugExpenseTotal() {
		return augExpenseTotal;
	}

	public void setAugExpenseTotal(Float augExpenseTotal) {
		this.augExpenseTotal = augExpenseTotal;
	}

	public Float getAugExpenseApprovedTotal() {
		return augExpenseApprovedTotal;
	}

	public void setAugExpenseApprovedTotal(Float augExpenseApprovedTotal) {
		this.augExpenseApprovedTotal = augExpenseApprovedTotal;
	}

	public Float getAugExpenseUnApprovedTotal() {
		return augExpenseUnApprovedTotal;
	}

	public void setAugExpenseUnApprovedTotal(Float augExpenseUnApprovedTotal) {
		this.augExpenseUnApprovedTotal = augExpenseUnApprovedTotal;
	}

	public Float getSepExpenseTotal() {
		return sepExpenseTotal;
	}

	public void setSepExpenseTotal(Float sepExpenseTotal) {
		this.sepExpenseTotal = sepExpenseTotal;
	}

	public Float getSepExpenseApprovedTotal() {
		return sepExpenseApprovedTotal;
	}

	public void setSepExpenseApprovedTotal(Float sepExpenseApprovedTotal) {
		this.sepExpenseApprovedTotal = sepExpenseApprovedTotal;
	}

	public Float getSepExpenseUnApprovedTotal() {
		return sepExpenseUnApprovedTotal;
	}

	public void setSepExpenseUnApprovedTotal(Float sepExpenseUnApprovedTotal) {
		this.sepExpenseUnApprovedTotal = sepExpenseUnApprovedTotal;
	}

	public Float getOctExpenseTotal() {
		return octExpenseTotal;
	}

	public void setOctExpenseTotal(Float octExpenseTotal) {
		this.octExpenseTotal = octExpenseTotal;
	}

	public Float getOctExpenseApprovedTotal() {
		return octExpenseApprovedTotal;
	}

	public void setOctExpenseApprovedTotal(Float octExpenseApprovedTotal) {
		this.octExpenseApprovedTotal = octExpenseApprovedTotal;
	}

	public Float getOctExpenseUnApprovedTotal() {
		return octExpenseUnApprovedTotal;
	}

	public void setOctExpenseUnApprovedTotal(Float octExpenseUnApprovedTotal) {
		this.octExpenseUnApprovedTotal = octExpenseUnApprovedTotal;
	}

	public Float getNovExpenseTotal() {
		return novExpenseTotal;
	}

	public void setNovExpenseTotal(Float novExpenseTotal) {
		this.novExpenseTotal = novExpenseTotal;
	}

	public Float getNovExpenseApprovedTotal() {
		return novExpenseApprovedTotal;
	}

	public void setNovExpenseApprovedTotal(Float novExpenseApprovedTotal) {
		this.novExpenseApprovedTotal = novExpenseApprovedTotal;
	}

	public Float getNovExpenseUnApprovedTotal() {
		return novExpenseUnApprovedTotal;
	}

	public void setNovExpenseUnApprovedTotal(Float novExpenseUnApprovedTotal) {
		this.novExpenseUnApprovedTotal = novExpenseUnApprovedTotal;
	}

	public Float getDecExpenseTotal() {
		return decExpenseTotal;
	}

	public void setDecExpenseTotal(Float decExpenseTotal) {
		this.decExpenseTotal = decExpenseTotal;
	}

	public Float getDecExpenseApprovedTotal() {
		return decExpenseApprovedTotal;
	}

	public void setDecExpenseApprovedTotal(Float decExpenseApprovedTotal) {
		this.decExpenseApprovedTotal = decExpenseApprovedTotal;
	}

	public Float getDecExpenseUnApprovedTotal() {
		return decExpenseUnApprovedTotal;
	}

	public void setDecExpenseUnApprovedTotal(Float decExpenseUnApprovedTotal) {
		this.decExpenseUnApprovedTotal = decExpenseUnApprovedTotal;
	}

	public Float getQ1ExpenseTotal() {
		return Q1ExpenseTotal;
	}

	public void setQ1ExpenseTotal(Float q1ExpenseTotal) {
		Q1ExpenseTotal = q1ExpenseTotal;
	}

	public Float getQ1ExpenseApprovedTotal() {
		return Q1ExpenseApprovedTotal;
	}

	public void setQ1ExpenseApprovedTotal(Float q1ExpenseApprovedTotal) {
		Q1ExpenseApprovedTotal = q1ExpenseApprovedTotal;
	}

	public Float getQ1ExpenseUnApprovedTotal() {
		return Q1ExpenseUnApprovedTotal;
	}

	public void setQ1ExpenseUnApprovedTotal(Float q1ExpenseUnApprovedTotal) {
		Q1ExpenseUnApprovedTotal = q1ExpenseUnApprovedTotal;
	}

	public Float getQ2ExpenseTotal() {
		return Q2ExpenseTotal;
	}

	public void setQ2ExpenseTotal(Float q2ExpenseTotal) {
		Q2ExpenseTotal = q2ExpenseTotal;
	}

	public Float getQ2ExpenseApprovedTotal() {
		return Q2ExpenseApprovedTotal;
	}

	public void setQ2ExpenseApprovedTotal(Float q2ExpenseApprovedTotal) {
		Q2ExpenseApprovedTotal = q2ExpenseApprovedTotal;
	}

	public Float getQ2ExpenseUnApprovedTotal() {
		return Q2ExpenseUnApprovedTotal;
	}

	public void setQ2ExpenseUnApprovedTotal(Float q2ExpenseUnApprovedTotal) {
		Q2ExpenseUnApprovedTotal = q2ExpenseUnApprovedTotal;
	}

	public Float getQ3ExpenseTotal() {
		return Q3ExpenseTotal;
	}

	public void setQ3ExpenseTotal(Float q3ExpenseTotal) {
		Q3ExpenseTotal = q3ExpenseTotal;
	}

	public Float getQ3ExpenseApprovedTotal() {
		return Q3ExpenseApprovedTotal;
	}

	public void setQ3ExpenseApprovedTotal(Float q3ExpenseApprovedTotal) {
		Q3ExpenseApprovedTotal = q3ExpenseApprovedTotal;
	}

	public Float getQ3ExpenseUnApprovedTotal() {
		return Q3ExpenseUnApprovedTotal;
	}

	public void setQ3ExpenseUnApprovedTotal(Float q3ExpenseUnApprovedTotal) {
		Q3ExpenseUnApprovedTotal = q3ExpenseUnApprovedTotal;
	}

	public Float getQ4ExpenseTotal() {
		return Q4ExpenseTotal;
	}

	public void setQ4ExpenseTotal(Float q4ExpenseTotal) {
		Q4ExpenseTotal = q4ExpenseTotal;
	}

	public Float getQ4ExpenseApprovedTotal() {
		return Q4ExpenseApprovedTotal;
	}

	public void setQ4ExpenseApprovedTotal(Float q4ExpenseApprovedTotal) {
		Q4ExpenseApprovedTotal = q4ExpenseApprovedTotal;
	}

	public Float getQ4ExpenseUnApprovedTotal() {
		return Q4ExpenseUnApprovedTotal;
	}

	public void setQ4ExpenseUnApprovedTotal(Float q4ExpenseUnApprovedTotal) {
		Q4ExpenseUnApprovedTotal = q4ExpenseUnApprovedTotal;
	}

	
}
