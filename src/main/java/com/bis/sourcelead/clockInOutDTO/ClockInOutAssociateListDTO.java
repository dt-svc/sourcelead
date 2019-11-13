package com.bis.sourcelead.clockInOutDTO;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockInOutAssociateListDTO {
	
	private Map<Object, HashMap<Object,List<ClockInOutAssociateReportSummaryDTO>>> dailyList;
	private Map<Object, HashMap<Object,List<ClockInOutAssociateReportSummaryDTO>>> weeklyList;
	private Map<Object, HashMap<Object,List<ClockInOutAssociateReportSummaryDTO>>> monthlyList;
	private Map<Object, HashMap<Object,List<ClockInOutAssociateReportSummaryDTO>>> yearlyList;
	
	public Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> getDailyList() {
		return dailyList;
	}
	public void setDailyList(Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> dailyList) {
		this.dailyList = dailyList;
	}
	public Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> getWeeklyList() {
		return weeklyList;
	}
	public void setWeeklyList(Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> weeklyList) {
		this.weeklyList = weeklyList;
	}
	public Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> getMonthlyList() {
		return monthlyList;
	}
	public void setMonthlyList(Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> monthlyList) {
		this.monthlyList = monthlyList;
	}
	public Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> getYearlyList() {
		return yearlyList;
	}
	public void setYearlyList(Map<Object, HashMap<Object, List<ClockInOutAssociateReportSummaryDTO>>> yearlyList) {
		this.yearlyList = yearlyList;
	}
	
}
