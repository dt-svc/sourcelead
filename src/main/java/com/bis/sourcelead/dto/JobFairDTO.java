package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.annotations.Type;

public class JobFairDTO extends SearchDTO implements Serializable {

	/**
	 *
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String uuid;
	private String generatedId;
	@Type(desc = "name", maxLength = 100)
	private String name;
	private String budget;
	private String description;
	private String startDate;
	private String endDate;
	private String startDateStr;
	private String endDateStr;
	private String postedFrom;
	private String selectedClient;
	private long requirementId;
	// private List<String> requirementsList;
	@Type(desc = "venue", maxLength = 200)
	private String venue;
	@Type(desc = "notifyTo", maxLength = 500)
	private String notifyTo;
	private long userId;
	private long orgLocationId;
	// private List<RequirementDTO> requirementDTOList;
	private int status;
	private String requirementJSONData;
	private List<JobFairRequirementsDTO> jobFairRequirementList;
	private String dateFormat;
	private List<String> requirementFairDates;
	private long jobFairRequirementId;
	private long applicantCount;
	private long offeredCount;

	private long createdByUserId;
	private Date createdDate;
	private long updatedByUserId;
	private Date updatedDate;
	private boolean adminUser;
	private boolean toExport;

	public boolean isToExport() {
		return toExport;
	}

	public void setToExport(boolean toExport) {
		this.toExport = toExport;
	}

	public boolean isAdminUser() {
		return adminUser;
	}

	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getUpdatedByUserId() {
		return updatedByUserId;
	}

	public void setUpdatedByUserId(long updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getApplicantCount() {
		return applicantCount;
	}

	public void setApplicantCount(long applicantCount) {
		this.applicantCount = applicantCount;
	}

	public long getOfferedCount() {
		return offeredCount;
	}

	public void setOfferedCount(long offeredCount) {
		this.offeredCount = offeredCount;
	}

	public long getJobFairRequirementId() {
		return jobFairRequirementId;
	}

	public void setJobFairRequirementId(long jobFairRequirementId) {
		this.jobFairRequirementId = jobFairRequirementId;
	}

	public List<String> getRequirementFairDates() {
		return requirementFairDates;
	}

	public void setRequirementFairDates(List<String> requirementFairDates) {
		this.requirementFairDates = requirementFairDates;
	}

	public String getPostedFrom() {
		return postedFrom;
	}

	public void setPostedFrom(String postedFrom) {
		this.postedFrom = postedFrom;
	}

	public List<JobFairRequirementsDTO> getJobFairRequirementList() {
		return jobFairRequirementList;
	}

	public void setJobFairRequirementList(List<JobFairRequirementsDTO> jobFairRequirementList) {
		this.jobFairRequirementList = jobFairRequirementList;
	}

	public String getRequirementJSONData() {
		return requirementJSONData;
	}

	public void setRequirementJSONData(String requirementJSONData) {
		this.requirementJSONData = requirementJSONData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String generatedId) {
		this.description = generatedId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(long requirementId) {
		this.requirementId = requirementId;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(String selectedClient) {
		this.selectedClient = selectedClient;
	}

}
