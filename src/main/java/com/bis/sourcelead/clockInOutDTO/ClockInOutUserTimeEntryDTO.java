package com.bis.sourcelead.clockInOutDTO;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ClockInOutUserTimeEntryDTO {

	private BigInteger id;
	
	private Time clockInTime;
	
	private Time totalTime;
	
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
	
	private Time currentTotTime;
	
	private Time weekTotTime;
	
	private Time monthTotTime;
	
	private Time yearTotTime;
	

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

	public Time getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Time totalTime) {
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

	public Time getCurrentTotTime() {
		return currentTotTime;
	}

	public void setCurrentTotTime(Time currentTotTime) {
		this.currentTotTime = currentTotTime;
	}

	public Time getWeekTotTime() {
		return weekTotTime;
	}

	public void setWeekTotTime(Time weekTotTime) {
		this.weekTotTime = weekTotTime;
	}

	public Time getMonthTotTime() {
		return monthTotTime;
	}

	public void setMonthTotTime(Time monthTotTime) {
		this.monthTotTime = monthTotTime;
	}

	public Time getYearTotTime() {
		return yearTotTime;
	}

	public void setYearTotTime(Time yearTotTime) {
		this.yearTotTime = yearTotTime;
	}

	
}
