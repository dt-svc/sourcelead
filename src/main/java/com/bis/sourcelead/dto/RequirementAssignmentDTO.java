package com.bis.sourcelead.dto;

public class RequirementAssignmentDTO extends CommonDTO {


	private String jobTitle;

	private String jobType;

	private String jobId;
	
	private String rate;
	
	private String clientName;
	
	private String country;

	private String state;

	private String city;
	
	private String postedDateStr;

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public String getJobType()
	{
		return jobType;
	}

	public void setJobType(String jobType)
	{
		this.jobType = jobType;
	}

	public String getJobId()
	{
		return jobId;
	}

	public void setJobId(String jobId)
	{
		this.jobId = jobId;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPostedDateStr()
	{
		return postedDateStr;
	}

	public void setPostedDateStr(String postedDateStr)
	{
		this.postedDateStr = postedDateStr;
	}


	
	
}
