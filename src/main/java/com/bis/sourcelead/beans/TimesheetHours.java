package com.bis.sourcelead.beans;

public class TimesheetHours {
	private String taskName;
	private String monDayHours;
	private String tueDayHours;
	private String wedDayHours;
	private String thuDayHours;
	private String friDayHours;
	private String satDayHours;
	private String sunDayHours;
	private String status;
	private String weekTotal;
	private String sdate;
	private String edate;
	private long weekTotalHours;

	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getMonDayHours() {
		return monDayHours;
	}

	public void setMonDayHours(String monDayHours) {
		this.monDayHours = monDayHours;
	}

	public String getTueDayHours() {
		return tueDayHours;
	}

	public void setTueDayHours(String tueDayHours) {
		this.tueDayHours = tueDayHours;
	}

	public String getWedDayHours() {
		return wedDayHours;
	}

	public void setWedDayHours(String wedDayHours) {
		this.wedDayHours = wedDayHours;
	}

	public String getThuDayHours() {
		return thuDayHours;
	}

	public void setThuDayHours(String thuDayHours) {
		this.thuDayHours = thuDayHours;
	}

	public String getFriDayHours() {
		return friDayHours;
	}

	public void setFriDayHours(String friDayHours) {
		this.friDayHours = friDayHours;
	}

	public String getSatDayHours() {
		return satDayHours;
	}

	public void setSatDayHours(String satDayHours) {
		this.satDayHours = satDayHours;
	}

	public String getSunDayHours() {
		return sunDayHours;
	}

	public void setSunDayHours(String sunDayHours) {
		this.sunDayHours = sunDayHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeekTotal() {
		return weekTotal;
	}

	public void setWeekTotal(String weekTotal) {
		this.weekTotal = weekTotal;
	}

	public String getSdate()
	{
		return sdate;
	}

	public void setSdate(String sdate)
	{
		this.sdate = sdate;
	}

	public String getEdate()
	{
		return edate;
	}

	public void setEdate(String edate)
	{
		this.edate = edate;
	}

	public long getWeekTotalHours() {
		return weekTotalHours;
	}

	public void setWeekTotalHours(long weekTotalHours) {
		this.weekTotalHours = weekTotalHours;
	}

}
