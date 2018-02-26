package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.Tasks;

public class EventsDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String eventType;
	private String dateRange;
	private String startDate;
	private String endDate;
	private List<Tasks> tasksList;
	private List<RequirementInterviewHistory> interviewerList;
	
	
	public String getEventType()
	{
		return eventType;
	}
	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}
	public String getDateRange()
	{
		return dateRange;
	}
	public void setDateRange(String dateRange)
	{
		this.dateRange = dateRange;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public List<Tasks> getTasksList()
	{
		return tasksList;
	}
	public void setTasksList(List<Tasks> tasksList)
	{
		this.tasksList = tasksList;
	}
	public List<RequirementInterviewHistory> getInterviewerList()
	{
		return interviewerList;
	}
	public void setInterviewerList(List<RequirementInterviewHistory> interviewerList)
	{
		this.interviewerList = interviewerList;
	}
}
