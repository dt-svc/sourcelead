package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
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
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.bis.sourcelead.enums.Status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PROJECT_MEMBERS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projectMembers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projects;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID", nullable=false )	
	private Employees employee;
	
	@Column(name = "TIMESHEET_FREQUENCY")
	private String timesheetFrequency;
	
	/*@Column(name = "PROJECT_ASSIGNED_DATE")
    private Date projectAssignedDate;
	
	@Column(name = "PROJECT_END_DATE")
    private Date projectEndDate;*/
	
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
    
    
    @Column(name = "TERMINATE_REASON")
    private String terminateReason;
    
    @JsonIgnore
    @OneToMany(mappedBy = "projectMemberId",cascade = CascadeType.ALL)
    @JsonBackReference
	@Fetch(FetchMode.SELECT)
	private Set<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>(0);
    
    
    
    
    
	

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

	public String getTimesheetFrequency() {
		return timesheetFrequency;
	}

	public void setTimesheetFrequency(String timesheetFrequency) {
		this.timesheetFrequency = timesheetFrequency;
	}


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

}
