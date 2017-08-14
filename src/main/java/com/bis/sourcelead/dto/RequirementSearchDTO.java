package com.bis.sourcelead.dto;

import java.io.Serializable;

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
	

	
}
