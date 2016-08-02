package com.bis.sourcelead.entity;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TIMESHEET_SUBMISSIONS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="timeSheetSubmissions")
public class TimesheetSubmissions {
	
	 
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_MEMBER_ID", nullable=false)
	private ProjectMembers projectMembers;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "START_DATE")
	private Date startDate;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "ESTIMATED_HOURS")
	private String estimatedHours;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;
	
	/*@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status submissionStatus;
	
	@Column(name = "SUBMISSION_APPROVALSTATUS")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus submissionApprovalStatus;*/
	
	
	@OneToMany(mappedBy = "submissionId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<SubmissionApproverHierarchy> approverHierarchyList = new HashSet<SubmissionApproverHierarchy>(0);
	
  
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
		
	@Column(name = "LEVEL_OF_APPROVER")
	private Integer levelOfApprover;
	
	@ManyToOne
	@JoinColumn(name = "APPROVER_USER_ID", nullable=false)
	private User approverUserId;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "WEEK_COMMENTS")
	@Type(type="text")
	private String weekcomments;
	
	@Column(name = "STATUS")
	private String status;
	 
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = " CREATED_DATE")
	private Date createdDate; 
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	  
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
	private Date updatedDate; 
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
/*	@Transient
	private Collection<ProjectTasks> projectTasksList;*/
	
	@Transient
	private String  endDateStr;
	
	@Transient
	private String  startDateStr;
	
	@Transient
	private String  fullName;
	
	@Transient
	private String combinedTotalHours;
	
	@Transient
	private String combinedStatus;
	
	@Transient
	private String employeeId;
	
	@Transient
	private String userId;
	
	@Transient
	private  HashMap<String,Date> dateList;
	
	@Transient
    private String managerLevel1;
    
    @Transient
    private String managerLevel2;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
	@JsonIgnore
	public ProjectMembers getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(ProjectMembers projectMembers) {
		this.projectMembers = projectMembers;
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

	/*public Submission_Status getSubmissionStatus() {
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
	}*/

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Integer getLevelOfApprover() {
		return levelOfApprover;
	}

	public void setLevelOfApprover(Integer levelOfApprover) {
		this.levelOfApprover = levelOfApprover;
	}

	@JsonIgnore
	public User getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(User approverUserId) {
		this.approverUserId = approverUserId;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWeekcomments() {
		return weekcomments;
	}

	public void setWeekcomments(String weekcomments) {
		this.weekcomments = weekcomments;
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

	/*public Collection<ProjectTasks> getProjectTasksList() {
		return projectTasksList;
	}

	public void setProjectTasksList(Collection<ProjectTasks> projectTasksList) {
		this.projectTasksList = projectTasksList;
	}*/

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCombinedTotalHours() {
		return combinedTotalHours;
	}

	public void setCombinedTotalHours(String combinedTotalHours) {
		this.combinedTotalHours = combinedTotalHours;
	}

	public String getCombinedStatus() {
		return combinedStatus;
	}

	public void setCombinedStatus(String combinedStatus) {
		this.combinedStatus = combinedStatus;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public HashMap<String, Date> getDateList() {
		return dateList;
	}

	public void setDateList(HashMap<String, Date> dateList) {
		this.dateList = dateList;
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

	public Set<SubmissionApproverHierarchy> getApproverHierarchyList() {
		return approverHierarchyList;
	}

	public void setApproverHierarchyList(Set<SubmissionApproverHierarchy> approverHierarchyList) {
		this.approverHierarchyList = approverHierarchyList;
	}
	
	
}
