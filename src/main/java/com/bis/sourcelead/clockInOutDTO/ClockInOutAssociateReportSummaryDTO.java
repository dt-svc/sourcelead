package com.bis.sourcelead.clockInOutDTO;

import java.math.BigInteger;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

public class ClockInOutAssociateReportSummaryDTO {
	
	private BigInteger id;
	private BigInteger userId;
	private BigInteger roleId;
	private BigInteger depId;
	private Time clockInTime;
	private Time clockOutTime;
	private String totalTime;
	private Date clockInDate;
	private Date clockOutDate;
	private String yearDate;
	private String firstName;
	private String lastName;
	private String roleType;
	//private String projectTaskName;
	private String comments;
	private String today;
	private List<String> currWeek;
	private List<String> currMonth;
	private List<String> currYear;
	
	private List<String> headerDates;
	
	private String clockInDateStr;
	private String clockOutDateStr;
	
	private String isDataAvailable;
	private Map<String, String> mapData;
	
	private String graphTotalTime;
	
	private String graphFormatDate;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getRoleId() {
		return roleId;
	}
	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}
	public BigInteger getDepId() {
		return depId;
	}
	public void setDepId(BigInteger depId) {
		this.depId = depId;
	}
	public Time getClockInTime() {
		return clockInTime;
	}
	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}
	public Time getClockOutTime() {
		return clockOutTime;
	}
	public void setClockOutTime(Time clockOutTime) {
		this.clockOutTime = clockOutTime;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
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
	public String getYearDate() {
		return yearDate;
	}
	public void setYearDate(String yearDate) {
		this.yearDate = yearDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public List<String> getCurrWeek() {
		return currWeek;
	}
	public void setCurrWeek(List<String> currWeek) {
		this.currWeek = currWeek;
	}
	public List<String> getCurrMonth() {
		return currMonth;
	}
	public void setCurrMonth(List<String> currMonth) {
		this.currMonth = currMonth;
	}
	public List<String> getCurrYear() {
		return currYear;
	}
	public void setCurrYear(List<String> currYear) {
		this.currYear = currYear;
	}
	public List<String> getHeaderDates() {
		return headerDates;
	}
	public void setHeaderDates(List<String> headerDates) {
		this.headerDates = headerDates;
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
	public String getIsDataAvailable() {
		return isDataAvailable;
	}
	public void setIsDataAvailable(String isDataAvailable) {
		this.isDataAvailable = isDataAvailable;
	}
	public Map<String, String> getMapData() {
		return mapData;
	}
	public void setMapData(Map<String, String> mapData) {
		this.mapData = mapData;
	}
	public String getGraphTotalTime() {
		return graphTotalTime;
	}
	public void setGraphTotalTime(String graphTotalTime) {
		this.graphTotalTime = graphTotalTime;
	}
	public String getGraphFormatDate() {
		return graphFormatDate;
	}
	public void setGraphFormatDate(String graphFormatDate) {
		this.graphFormatDate = graphFormatDate;
	}
	
}
