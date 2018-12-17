package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class ShiftDTO {
	
	private long id;
	private String userId;
	private String orgLocationId;
	
	@Required(desc = "Shift Name")
	private String shiftName;
	
	@Required(desc = "Time In")
	private String timeIn;
	
	@Required(desc = "Time Out")
	private String timeOut;
	
	private String shiftDescription;
	private String shiftId;
	private String status;
	private String updatedBy;
	private String shiftUUID;
	
	private String totalHours;
	private String minimumHours; 
	private String mondayInTime;
	private String mondayOutTime;
	private String tuesdayInTime;
	private String tuesdayOutTime;
	private String wednesdayInTime;
	private String wednesdayOutTime;
	private String thursdayInTime;
	private String thursdayOutTime;
	private String fridayInTime;
	private String fridayOutTime;
	private String saturdayInTime;
	private String saturdayOutTime;
	private String sundayInTime;
	private String sundayOutTime;
	private String defaultStatus;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
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
	public String getShiftId()
	{
		return shiftId;
	}
	public void setShiftId(String shiftId)
	{
		this.shiftId = shiftId;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
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
	public String getMinimumHours() {
		return minimumHours;
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
	public void setMinimumHours(String minimumHours) {
		this.minimumHours = minimumHours;
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
