package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty
	private String empName;
	
	@Transient
	private String empEmail;
	@Transient
	private String startDate;
	@Transient
	private String endDate;
	
	@JsonProperty
	@Transient
	private String approvedHrs;
	
	@JsonProperty
	@Transient
	private String rejectedHrs;
	@Transient
	private String openHrs;
	
	@JsonProperty
	@Transient
	private String draftHrs;
	
	@JsonProperty
	@Transient
	private String submittedHrs;
	@Transient
	@JsonProperty
	private String projectAssignedDateStr;
	@Transient
	@JsonProperty
	private String projectEndDateStr;
	
	@Transient
	@JsonProperty
	private String projectName;
	
	@JsonProperty
	@Transient
	private String memberEnddate;
	
	@Transient
	@JsonProperty
	private String userId;
	@Transient
	@Enumerated(EnumType.STRING)
	private Status userState;
    
	@Transient
	private String employeeIdForExpenses;
	
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
    private Float claimedExpenseTotal;
    
    @Transient
    private Float claimedExpenseApprovedTotal;
    
    @Transient
    private Float claimedExpenseUnApprovedTotal;
    
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
    
    @Transient
    private Float expenseTotal;
    
    @Transient
    private Float expenseApprovedTotal;
    
    @Transient
    private Float expenseUnApprovedTotal;
    
    @Transient
	private String employementType;
	
	@Transient
	private String userName;
	
	@Transient
	private String userEmail;
	
	
	@Transient
	private String employmentTypeVendors;
    //For Expenses 

	@JsonProperty
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

	@Transient
	private LinkedHashSet<String> monthsStringFormat;
	
	@Transient
	private String clientNameExpenses;
	
	@JsonProperty
	@Transient
	private Float userExpensesClaimedAmount;
	
	@JsonProperty
	@Transient
	private String projectUUID;
	
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
		if(janExpenseTotal == null){
			janExpenseTotal = 0f;
		}
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
		if(febExpenseTotal == null){
			febExpenseTotal = 0f;
		}
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
		if(marExpenseTotal == null){
			marExpenseTotal = 0f;
		}
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
		if(aprExpenseTotal == null){
			aprExpenseTotal = 0f;
		}
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
		if(mayExpenseTotal == null){
			mayExpenseTotal = 0f;
		}
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
		if(junExpenseTotal == null){
			junExpenseTotal = 0f;
		}
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
		if(julExpenseTotal == null){
			julExpenseTotal = 0f;
		}
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
		if(augExpenseTotal == null){
			augExpenseTotal = 0f;
		}
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
		if(sepExpenseTotal == null){
			sepExpenseTotal = 0f;
		}
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
		if(octExpenseTotal == null){
			octExpenseTotal = 0f;
		}
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
		if(novExpenseTotal == null){
			novExpenseTotal = 0f;
		}
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
		if(decExpenseTotal == null){
			decExpenseTotal = 0f;
		}
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

	public LinkedHashSet<String> getMonthsStringFormat() {
		return monthsStringFormat;
	}

	public void setMonthsStringFormat(LinkedHashSet<String> monthsStringFormat) {
		this.monthsStringFormat = monthsStringFormat;
	}

	public void setQ4ExpenseUnApprovedTotal(Float q4ExpenseUnApprovedTotal) {
		Q4ExpenseUnApprovedTotal = q4ExpenseUnApprovedTotal;
	}

	public String getEmployementType() {
		return employementType;
	}

	public void setEmployementType(String employementType) {
		this.employementType = employementType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Float getExpenseTotal() {
		return expenseTotal;
	}

	public void setExpenseTotal(Float expenseTotal) {
		this.expenseTotal = expenseTotal;
	}

	public Float getExpenseApprovedTotal() {
		return expenseApprovedTotal;
	}

	public void setExpenseApprovedTotal(Float expenseApprovedTotal) {
		this.expenseApprovedTotal = expenseApprovedTotal;
	}

	public Float getExpenseUnApprovedTotal() {
		return expenseUnApprovedTotal;
	}

	public void setExpenseUnApprovedTotal(Float expenseUnApprovedTotal) {
		this.expenseUnApprovedTotal = expenseUnApprovedTotal;
	}

	public String getEmployeeIdForExpenses() {
		return employeeIdForExpenses;
	}

	public void setEmployeeIdForExpenses(String employeeIdForExpenses) {
		this.employeeIdForExpenses = employeeIdForExpenses;
	}

	public Float getClaimedExpenseTotal() {
		return claimedExpenseTotal;
	}

	public void setClaimedExpenseTotal(Float claimedExpenseTotal) {
		this.claimedExpenseTotal = claimedExpenseTotal;
	}

	public Float getClaimedExpenseApprovedTotal() {
		return claimedExpenseApprovedTotal;
	}

	public void setClaimedExpenseApprovedTotal(Float claimedExpenseApprovedTotal) {
		this.claimedExpenseApprovedTotal = claimedExpenseApprovedTotal;
	}

	public Float getClaimedExpenseUnApprovedTotal() {
		return claimedExpenseUnApprovedTotal;
	}

	public void setClaimedExpenseUnApprovedTotal(Float claimedExpenseUnApprovedTotal) {
		this.claimedExpenseUnApprovedTotal = claimedExpenseUnApprovedTotal;
	}

	public String getClientNameExpenses() {
		return clientNameExpenses;
	}

	public void setClientNameExpenses(String clientNameExpenses) {
		this.clientNameExpenses = clientNameExpenses;
	}

	public String getEmploymentTypeVendors() {
		return employmentTypeVendors;
	}

	public void setEmploymentTypeVendors(String employmentTypeVendors) {
		this.employmentTypeVendors = employmentTypeVendors;
	}

	public Float getUserExpensesClaimedAmount()
	{
		return userExpensesClaimedAmount;
	}

	public void setUserExpensesClaimedAmount(Float userExpensesClaimedAmount)
	{
		this.userExpensesClaimedAmount = userExpensesClaimedAmount;
	}

	public String getProjectUUID()
	{
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID)
	{
		this.projectUUID = projectUUID;
	}

	public String getMemberEnddate()
	{
		return memberEnddate;
	}

	public void setMemberEnddate(String memberEnddate)
	{
		this.memberEnddate = memberEnddate;
	}
	
}
