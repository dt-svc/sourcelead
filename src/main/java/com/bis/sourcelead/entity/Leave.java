package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER_LEAVE")
public class Leave {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "LEAVE_TYPE_ID", nullable = false)
	private LeaveType leaveType;
	
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@Column(name = "FROM_HALF_DAY")
	private String fromHalfDay;
	
	@Column(name = "TO_HALF_DAY")
	private String toHalfDay;
	
	@Column(name = "FROM_DATE_FROM_TIME")
	private String fromDateFromHalfTime;
	
	@Column(name = "FROM_DATE_TO_TIME")
	private String fromDateToHalfTime;
	
	@Column(name = "TO_DATE_FROM_TIME")
	private String toDateFromHalfTime;
	
	@Column(name = "TO_DATE_TO_TIME")
	private String toDateToHalfTime;

//	 
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	@Column(name = "REASON",columnDefinition = "TEXT default NULL")
	private String reason;
	
	@Column(name = "NUMBER_OF_DAYS")
	private int numberOfDays;
	
	@Column(name = "EXTRA_LEAVES_TAKEN")
	private float extraLeavesTaken;
	
	@Column(name = "REJECTED_REASON",columnDefinition = "TEXT default NULL")
	private String rejectedReason;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "APPROVED_BY", nullable = false)
	private User approvedBy;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "REJECTED_BY", nullable = false)
	private User rejectedBy;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CANCELLED_BY", nullable = false)
	private User cancelledBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DELETE_REASON",columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name = "WITHDRAW_REASON",columnDefinition = "TEXT default NULL")
	private String withdrawReason;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "LEAVE_UUID")
	private String leaveUUID;
	
	@Column(name = "NOTIFY_TO_OTHERS")
	private String notifyToOthers;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "leaveid")
	private Set<LeaveApproveHierarchy> approverHierarchyList;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "leave")
	private Set<AppliedLeaveDate> appliedLeaveDateSet;
	
	@Transient
	@JsonProperty
	private String leaveTypeName;
	
	@Transient
	@JsonProperty
	private String fromDateStr;
	
	@Transient
	@JsonProperty
	private String toDateStr;
	
	@Transient
	@JsonProperty
	private String createdDateStr;
	
	@Transient
	@JsonProperty
	private String approverUsers;
	
	@Transient
	@JsonProperty
	private float balanceLeavesCount;
	
	@Transient
	@JsonProperty
	private String approvedByUser;
	
	@Transient
	@JsonProperty
	private int totalLeavesTaken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getFromHalfDay() {
		return fromHalfDay;
	}

	public void setFromHalfDay(String fromHalfDay) {
		this.fromHalfDay = fromHalfDay;
	}

	public String getToHalfDay() {
		return toHalfDay;
	}

	public void setToHalfDay(String toHalfDay) {
		this.toHalfDay = toHalfDay;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public User getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(User rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public User getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(User cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
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

	public Set<LeaveApproveHierarchy> getApproverHierarchyList() {
		return approverHierarchyList;
	}

	public void setApproverHierarchyList(Set<LeaveApproveHierarchy> approverHierarchyList) {
		this.approverHierarchyList = approverHierarchyList;
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

	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public String getApproverUsers()
	{
		return approverUsers;
	}

	public void setApproverUsers(String approverUsers)
	{
		this.approverUsers = approverUsers;
	}

	public String getFromDateStr()
	{
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr()
	{
		return toDateStr;
	}

	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}

	public String getCreatedDateStr()
	{
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr)
	{
		this.createdDateStr = createdDateStr;
	}

	public float getBalanceLeavesCount()
	{
		return balanceLeavesCount;
	}

	public void setBalanceLeavesCount(float balanceLeavesCount)
	{
		this.balanceLeavesCount = balanceLeavesCount;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	
	public float getExtraLeavesTaken()
	{
		return extraLeavesTaken;
	}

	public void setExtraLeavesTaken(float extraLeavesTaken)
	{
		this.extraLeavesTaken = extraLeavesTaken;
	}

	public String getApprovedByUser()
	{
		return approvedByUser;
	}

	public void setApprovedByUser(String approvedByUser)
	{
		this.approvedByUser = approvedByUser;
	}

	public String getWithdrawReason() {
		return withdrawReason;
	}

	public void setWithdrawReason(String withdrawReason) {
		this.withdrawReason = withdrawReason;
	}

	public int getTotalLeavesTaken() {
		return totalLeavesTaken;
	}

	public void setTotalLeavesTaken(int totalLeavesTaken) {
		this.totalLeavesTaken = totalLeavesTaken;
	}

	public String getLeaveUUID()
	{
		return leaveUUID;
	}

	public void setLeaveUUID(String leaveUUID)
	{
		this.leaveUUID = leaveUUID;
	}

	public Set<AppliedLeaveDate> getAppliedLeaveDateSet()
	{
		return appliedLeaveDateSet;
	}

	public void setAppliedLeaveDateSet(Set<AppliedLeaveDate> appliedLeaveDateSet)
	{
		this.appliedLeaveDateSet = appliedLeaveDateSet;
	}

	public String getFromDateFromHalfTime() {
		return fromDateFromHalfTime;
	}

	public void setFromDateFromHalfTime(String fromDateFromHalfTime) {
		this.fromDateFromHalfTime = fromDateFromHalfTime;
	}

	public String getFromDateToHalfTime() {
		return fromDateToHalfTime;
	}

	public void setFromDateToHalfTime(String fromDateToHalfTime) {
		this.fromDateToHalfTime = fromDateToHalfTime;
	}

	public String getToDateFromHalfTime() {
		return toDateFromHalfTime;
	}

	public void setToDateFromHalfTime(String toDateFromHalfTime) {
		this.toDateFromHalfTime = toDateFromHalfTime;
	}

	public String getToDateToHalfTime() {
		return toDateToHalfTime;
	}

	public void setToDateToHalfTime(String toDateToHalfTime) {
		this.toDateToHalfTime = toDateToHalfTime;
	}

	public String getNotifyToOthers() {
		return notifyToOthers;
	}

	public void setNotifyToOthers(String notifyToOthers) {
		this.notifyToOthers = notifyToOthers;
	}
	
}
