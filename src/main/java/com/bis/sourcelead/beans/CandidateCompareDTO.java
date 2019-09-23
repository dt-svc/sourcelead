package com.bis.sourcelead.beans;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.elk.index.CandidateIndex;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * to get candidate details to compare
 * @since 14/07/2017
 * @version 1.0
 */
public class CandidateCompareDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String jobId;
	@JsonProperty
	private String jobTitile;
	@JsonProperty
	private String jobRate;
	@JsonProperty
	private String jobRatePer;
	@JsonProperty
	private String jobSkills;
	@JsonProperty
	private String jobLocation;
	@JsonProperty
	private List<String> jobLocationList;
	@JsonProperty
	private String jobNoticePeriod;
	@JsonProperty
	private String jobEducation;
	@JsonProperty
	private String jobMinExp;
	@JsonProperty
	private String jobMaxExp;
	@JsonProperty
	private String jobVisa;
	@JsonProperty
	private List<CandidateIndex> candidateList;
	
	public String getJobId()
	{
		return jobId;
	}
	public void setJobId(String jobId)
	{
		this.jobId = jobId;
	}
	public String getJobTitile()
	{
		return jobTitile;
	}
	public void setJobTitile(String jobTitile)
	{
		this.jobTitile = jobTitile;
	}
	public String getJobRate()
	{
		return jobRate;
	}
	public void setJobRate(String jobRate)
	{
		this.jobRate = jobRate;
	}
	public String getJobRatePer()
	{
		return jobRatePer;
	}
	public void setJobRatePer(String jobRatePer)
	{
		this.jobRatePer = jobRatePer;
	}
	public String getJobSkills()
	{
		return jobSkills;
	}
	public void setJobSkills(String jobSkills)
	{
		this.jobSkills = jobSkills;
	}
	public String getJobLocation()
	{
		return jobLocation;
	}
	public void setJobLocation(String jobLocation)
	{
		this.jobLocation = jobLocation;
	}
	public List<String> getJobLocationList()
	{
		return jobLocationList;
	}
	public void setJobLocationList(List<String> jobLocationList)
	{
		this.jobLocationList = jobLocationList;
	}
	public String getJobNoticePeriod()
	{
		return jobNoticePeriod;
	}
	public void setJobNoticePeriod(String jobNoticePeriod)
	{
		this.jobNoticePeriod = jobNoticePeriod;
	}
	public String getJobEducation()
	{
		return jobEducation;
	}
	public void setJobEducation(String jobEducation)
	{
		this.jobEducation = jobEducation;
	}
	public String getJobMinExp()
	{
		return jobMinExp;
	}
	public void setJobMinExp(String jobMinExp)
	{
		this.jobMinExp = jobMinExp;
	}
	public String getJobMaxExp()
	{
		return jobMaxExp;
	}
	public void setJobMaxExp(String jobMaxExp)
	{
		this.jobMaxExp = jobMaxExp;
	}
	public String getJobVisa()
	{
		return jobVisa;
	}
	public void setJobVisa(String jobVisa)
	{
		this.jobVisa = jobVisa;
	}
	public List<CandidateIndex> getCandidateList()
	{
		return candidateList;
	}
	public void setCandidateList(List<CandidateIndex> candidateList)
	{
		this.candidateList = candidateList;
	}
}
