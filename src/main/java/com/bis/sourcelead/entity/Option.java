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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LEAVE_OPTION")
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	private Department departmentId;

	
	@Column(name = "CALENDAR_MONTH")
	private String calendarMonth;
	
	
	@Column(name = "WEEKENDS")
	private String weekends;

	@Column(name = "HALFDAY_REQUEST")
	private String halfdayRequest;

	@Column(name = "LEAVE_TRANSFER")
	private String leaveTransfer;
	

	@Column(name = "WORKING_HOURS")
	private String workingHours;
	
	@Column(name = "DELETE_REASON",columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
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
	
	@Column(name = "OPTION_UUID")
	private String optionUUID;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "leavesOptionId")
	private Set<LeaveRole> leaverolesSet;
	

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

	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	public String getWeekends() {
		return weekends;
	}

	public void setWeekends(String weekends) {
		this.weekends = weekends;
	}

	public String getHalfdayRequest() {
		return halfdayRequest;
	}

	public void setHalfdayRequest(String halfdayRequest) {
		this.halfdayRequest = halfdayRequest;
	}

	public String getLeaveTransfer() {
		return leaveTransfer;
	}

	public void setLeaveTransfer(String leaveTransfer) {
		this.leaveTransfer = leaveTransfer;
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

	public Set<LeaveRole> getLeaverolesSet() {
		return leaverolesSet;
	}

	public void setLeaverolesSet(Set<LeaveRole> leaverolesSet) {
		this.leaverolesSet = leaverolesSet;
	}

	public String getCalendarMonth() {
		return calendarMonth;
	}

	public void setCalendarMonth(String calendarMonth) {
		this.calendarMonth = calendarMonth;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getOptionUUID()
	{
		return optionUUID;
	}

	public void setOptionUUID(String optionUUID)
	{
		this.optionUUID = optionUUID;
	}
	
}
