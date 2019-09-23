package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class RequirementSearchDTO extends SearchDTO implements Serializable {
	private String status;
	private String clients;
	
	private String requirementTitle;
	private String jobId;
	private String noticePeriod;
	private String requirementStatus;
	private String updatedOn;
	private String client;
	private String searchType;
	
	private String filterStatus;
	private String filterClients;
	private String generatedId;
	private String filterPriority;
	private String filterJobType;
	private String filterAssignTo;
	private String visaType;
	private String requirementSkills;
	private String filterSpecJobs;
	private String filterProjectId;
    private List<String> jobsTags;
    private String cityName; 
    private String exportType;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClients() {
		return clients;
	}
	public void setClients(String clients) {
		this.clients = clients;
	}
	public String getRequirementTitle() {
		return requirementTitle;
	}
	public String getGeneratedId()
	{
		return generatedId;
	}
	public void setGeneratedId(String generatedId)
	{
		this.generatedId = generatedId;
	}
	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getRequirementStatus() {
		return requirementStatus;
	}
	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getFilterStatus() {
		return filterStatus;
	}
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	public String getFilterClients() {
		return filterClients;
	}
	public void setFilterClients(String filterClients) {
		this.filterClients = filterClients;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getFilterPriority() {
		return filterPriority;
	}
	public void setFilterPriority(String filterPriority) {
		this.filterPriority = filterPriority;
	}
	public String getFilterJobType() {
		return filterJobType;
	}
	public void setFilterJobType(String filterJobType) {
		this.filterJobType = filterJobType;
	}
	public String getFilterAssignTo()
	{
		return filterAssignTo;
	}
	public void setFilterAssignTo(String filterAssignTo)
	{
		this.filterAssignTo = filterAssignTo;
	}
	public String getVisaType() {
		return visaType;
	}
	public String getRequirementSkills() {
		return requirementSkills;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public void setRequirementSkills(String requirementSkills) {
		this.requirementSkills = requirementSkills;
	}
	public String getFilterSpecJobs() {
		return filterSpecJobs;
	}
	public void setFilterSpecJobs(String filterSpecJobs) {
		this.filterSpecJobs = filterSpecJobs;
	}
	public String getFilterProjectId() {
		return filterProjectId;
	}
	public void setFilterProjectId(String filterProjectId) {
		this.filterProjectId = filterProjectId;
	}
	public List<String> getJobsTags() {
		return jobsTags;
	}
	public void setJobsTags(List<String> jobsTags) {
		this.jobsTags = jobsTags;
	}
	public String getCityName()
	{
		return cityName;
	}
	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
}
