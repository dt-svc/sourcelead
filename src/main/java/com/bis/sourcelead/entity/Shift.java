package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "SHIFT")
public class Shift {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID")        
    private OrgLocations orgLocations;
	
	@Column(name = "SHIFT_NAME")
	private String shiftName;
	
	@Column(name = "TIME_IN")
	private String timeIn;
	
	@Column(name = "TIME_OUT")
	private String timeOut;
	
	@Column(name = "SHIFT_DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String shiftDescription;
	
	@Column(name = "APPLICABLE_TO_USERS")
	private String applicableToUsers;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;

	@Column(name = "MINMUM_HOURS")
	private String minmumHours;
	
	@Column(name = "MONDAY_IN_TIME")
	private String mondayInTime;
	
	@Column(name = "MONDAY_OUT_TIME")
	private String mondayOutTime;
	
	@Column(name = "TUESDAY_IN_TIME")
	private String tuesdayInTime;
	
	@Column(name = "TUESDAY_OUT_TIME")
	private String tuesdayOutTime;
	
	@Column(name = "WEDNESDAY_IN_TIME")
	private String wednesdayInTime;
	
	@Column(name = "WEDNESDAY_OUT_TIME")
	private String wednesdayOutTime;
	
	@Column(name = "THURSDAY_IN_TIME")
	private String thursdayInTime;
	
	@Column(name = "THURSDAY_OUT_TIME")
	private String thursdayOutTime;
	
	@Column(name = "FRIDAY_IN_TIME")
	private String fridayInTime;
	
	@Column(name = "FRIDAY_OUT_TIME")
	private String fridayOutTime;
	
	@Column(name = "SATURDAY_IN_TIME")
	private String saturdayInTime;
	
	@Column(name = "SATURDAY_OUT_TIME")
	private String saturdayOutTime;
	
	@Column(name = "SUNDAY_IN_TIME")
	private String sundayInTime;
	
	@Column(name = "SUNDAY_OUT_TIME")
	private String sundayOutTime;
	
	@Column(name = "DEFAULT_STATUS")
	private String defaultStatus;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DELETE_REASON",columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "SHIFT_UUID")
	private String shiftUUID;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OrgLocations getOrgLocations()
	{
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations)
	{
		this.orgLocations = orgLocations;
	}

	public String getShiftName()
	{
		return shiftName;
	}

	public void setShiftName(String shiftName)
	{
		this.shiftName = shiftName;
	}

	public String getTimeIn()
	{
		return timeIn;
	}

	public void setTimeIn(String timeIn)
	{
		this.timeIn = timeIn;
	}

	public String getTimeOut()
	{
		return timeOut;
	}

	public void setTimeOut(String timeOut)
	{
		this.timeOut = timeOut;
	}

	public String getShiftDescription()
	{
		return shiftDescription;
	}

	public void setShiftDescription(String shiftDescription)
	{
		this.shiftDescription = shiftDescription;
	}

	public String getApplicableToUsers()
	{
		return applicableToUsers;
	}

	public void setApplicableToUsers(String applicableToUsers)
	{
		this.applicableToUsers = applicableToUsers;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDeleteReason()
	{
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason)
	{
		this.deleteReason = deleteReason;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getShiftUUID()
	{
		return shiftUUID;
	}

	public void setShiftUUID(String shiftUUID)
	{
		this.shiftUUID = shiftUUID;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public String getMinmumHours() {
		return minmumHours;
	}

	public String getMondayInTime() {
		return mondayInTime;
	}

	public String getMondayOutTime() {
		return mondayOutTime;
	}

	public String getTuesdayInTime() {
		return tuesdayInTime;
	}

	public String getTuesdayOutTime() {
		return tuesdayOutTime;
	}

	public String getWednesdayInTime() {
		return wednesdayInTime;
	}

	public String getWednesdayOutTime() {
		return wednesdayOutTime;
	}

	public String getThursdayInTime() {
		return thursdayInTime;
	}

	public String getThursdayOutTime() {
		return thursdayOutTime;
	}

	public String getFridayInTime() {
		return fridayInTime;
	}

	public String getFridayOutTime() {
		return fridayOutTime;
	}

	public String getSaturdayInTime() {
		return saturdayInTime;
	}

	public String getSaturdayOutTime() {
		return saturdayOutTime;
	}

	public String getSundayInTime() {
		return sundayInTime;
	}

	public String getSundayOutTime() {
		return sundayOutTime;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public void setMinmumHours(String minmumHours) {
		this.minmumHours = minmumHours;
	}

	public void setMondayInTime(String mondayInTime) {
		this.mondayInTime = mondayInTime;
	}

	public void setMondayOutTime(String mondayOutTime) {
		this.mondayOutTime = mondayOutTime;
	}

	public void setTuesdayInTime(String tuesdayInTime) {
		this.tuesdayInTime = tuesdayInTime;
	}

	public void setTuesdayOutTime(String tuesdayOutTime) {
		this.tuesdayOutTime = tuesdayOutTime;
	}

	public void setWednesdayInTime(String wednesdayInTime) {
		this.wednesdayInTime = wednesdayInTime;
	}

	public void setWednesdayOutTime(String wednesdayOutTime) {
		this.wednesdayOutTime = wednesdayOutTime;
	}

	public void setThursdayInTime(String thursdayInTime) {
		this.thursdayInTime = thursdayInTime;
	}

	public void setThursdayOutTime(String thursdayOutTime) {
		this.thursdayOutTime = thursdayOutTime;
	}

	public void setFridayInTime(String fridayInTime) {
		this.fridayInTime = fridayInTime;
	}

	public void setFridayOutTime(String fridayOutTime) {
		this.fridayOutTime = fridayOutTime;
	}

	public void setSaturdayInTime(String saturdayInTime) {
		this.saturdayInTime = saturdayInTime;
	}

	public void setSaturdayOutTime(String saturdayOutTime) {
		this.saturdayOutTime = saturdayOutTime;
	}

	public void setSundayInTime(String sundayInTime) {
		this.sundayInTime = sundayInTime;
	}

	public void setSundayOutTime(String sundayOutTime) {
		this.sundayOutTime = sundayOutTime;
	}

	public String getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(String defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
}
