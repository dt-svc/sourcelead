package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.Tasks;

public class DashboardDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long interviewCount;
	private Long taskCount;
	
	
	
	
	public Long getInterviewCount()
	{
		return interviewCount;
	}
	public void setInterviewCount(Long interviewCount)
	{
		this.interviewCount = interviewCount;
	}
	public Long getTaskCount()
	{
		return taskCount;
	}
	public void setTaskCount(Long taskCount)
	{
		this.taskCount = taskCount;
	}
}
