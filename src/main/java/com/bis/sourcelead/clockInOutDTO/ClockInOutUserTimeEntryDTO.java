package com.bis.sourcelead.clockInOutDTO;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ClockInOutUserTimeEntryDTO {

	private BigInteger id;
	
	private Time clockInTime;
	
	private String totalTime;
	
	private Time clockOutTime;
	
	private String comments;
	
	private String projectTaskName;
	
	private Date clockInDate;
	
	private Date clockOutDate;
	
	private String clockInDateStr;
	
	private String clockOutDateStr;
	
	private String frequency;
	
	private String graphForamatDate;
	
	private String graphTotalTime;
	
	private String yearMonth;
	
	private String type;
	
	private List<ClockInOutSubDTO> subList;
	
	private String currentTotTime;
	
	private String weekTotTime;
	
	private String monthTotTime;
	
	private Time yearTotTime;
	
	private String totalBreakTime;
	
	private String geoLocationData;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Time getClockInTime() {
		return clockInTime;
	}

	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Time getClockOutTime() {
		return clockOutTime;
	}

	public void setClockOutTime(Time clockOutTime) {
		this.clockOutTime = clockOutTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProjectTaskName() {
		return projectTaskName;
	}

	public void setProjectTaskName(String projectTaskName) {
		this.projectTaskName = projectTaskName;
	}

	public Date getClockInDate() {
		return clockInDate;
	}

	public void setClockInDate(Date clockInDate) {
		this.clockInDate = clockInDate;
	}

	public Date getClockOutDate() {
		return clockOutDate;
	}

	public void setClockOutDate(Date clockOutDate) {
		this.clockOutDate = clockOutDate;
	}

	public String getClockInDateStr() {
		return clockInDateStr;
	}

	public void setClockInDateStr(String clockInDateStr) {
		this.clockInDateStr = clockInDateStr;
	}

	public String getClockOutDateStr() {
		return clockOutDateStr;
	}

	public void setClockOutDateStr(String clockOutDateStr) {
		this.clockOutDateStr = clockOutDateStr;
	}

	public List<ClockInOutSubDTO> getSubList() {
		return subList;
	}

	public void setSubList(List<ClockInOutSubDTO> subList) {
		this.subList = subList;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getGraphForamatDate()
	{
		return graphForamatDate;
	}

	public void setGraphForamatDate(String graphForamatDate)
	{
		this.graphForamatDate = graphForamatDate;
	}

	public String getGraphTotalTime()
	{
		return graphTotalTime;
	}

	public void setGraphTotalTime(String graphTotalTime)
	{
		this.graphTotalTime = graphTotalTime;
	}

	public String getYearMonth()
	{
		return yearMonth;
	}

	public void setYearMonth(String yearMonth)
	{
		this.yearMonth = yearMonth;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCurrentTotTime() {
		return currentTotTime;
	}

	public void setCurrentTotTime(String currentTotTime) {
		this.currentTotTime = currentTotTime;
	}

	public String getWeekTotTime() {
		return weekTotTime;
	}

	public void setWeekTotTime(String weekTotTime) {
		this.weekTotTime = weekTotTime;
	}

	public String getMonthTotTime() {
		return monthTotTime;
	}

	public void setMonthTotTime(String monthTotTime) {
		this.monthTotTime = monthTotTime;
	}

	public Time getYearTotTime() {
		return yearTotTime;
	}

	public void setYearTotTime(Time yearTotTime) {
		this.yearTotTime = yearTotTime;
	}

	public String getTotalBreakTime() {
		return totalBreakTime;
	}

	public void setTotalBreakTime(String totalBreakTime) {
		this.totalBreakTime = totalBreakTime;
	}

	public String getGeoLocationData() {
		return geoLocationData;
	}

	public void setGeoLocationData(String geoLocationData) {
		this.geoLocationData = geoLocationData;
	}
}
