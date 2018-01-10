package com.bis.sourcelead.dto;

import java.util.List;

public class DataMigrationDTO extends CommonDTO {

	private String staticPath;
	
	private String uploadType;
	
	private String headerPosition;
	
	private String timesheetMode;

	private List<FileInfo> fileInfo;

	private List<FileInfo> uploadedFileInfo;

	private String assignedTo;
	
	private String exportFilePath;

	private String jobTitle;

	private String jobType;

	private String jobId;

	private String positions;

	private String education;

	private String minExp;

	private String maxExp;

	private String industryName;

	private String rate;

	private String maxRate;

	private String travelAllowance;

	private String closedDate;

	private String location;

	private String country;

	private String state;

	private String city;

	private String zipCode;

	private String skills;

	private String visa;

	private String description;

	private String noticePeriod;

	private String coolingOff;

	private String selectClient;

	private String selectContact;

	private String clientRate;
	
	private String clientSubmissionForm;
	
	private String approver;
	
	private String parallelApprover;

	private String manager;

	private String priority;
	
	private String requirementSubmissionStatus;
	
	private String experienceLevel;
	
	private String applicationFrom;

	/*Timesheets*/
	
	private String employeeId;
	private String employeeName;
	private String employeeEmail;
	private String project;
	private String clientName;
	private String actualStartDate;
	private String actualEndDate;
	private String submissionStatus;
	private String week1;
	private String week2;
	private String week3;
	private String week4;
	private String week5;
	private String totalHours;
	private String billableHours;
	private String nonBillableHours;
	private String weeekComments;
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}
	public String getWeek1() {
		return week1;
	}

	public void setWeek1(String week1) {
		this.week1 = week1;
	}

	public String getWeek2() {
		return week2;
	}

	public void setWeek2(String week2) {
		this.week2 = week2;
	}

	public String getWeek3() {
		return week3;
	}

	public void setWeek3(String week3) {
		this.week3 = week3;
	}

	public String getWeek4() {
		return week4;
	}

	public void setWeek4(String week4) {
		this.week4 = week4;
	}

	public String getWeek5() {
		return week5;
	}

	public void setWeek5(String week5) {
		this.week5 = week5;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}

	public String getNonBillableHours() {
		return nonBillableHours;
	}

	public void setNonBillableHours(String nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}

	public String getWeeekComments() {
		return weeekComments;
	}

	public void setWeeekComments(String weeekComments) {
		this.weeekComments = weeekComments;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/*Timesheets*/
	
	
	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getHeaderPosition()
	{
		return headerPosition;
	}

	public void setHeaderPosition(String headerPosition)
	{
		this.headerPosition = headerPosition;
	}

	public String getTimesheetMode()
	{
		return timesheetMode;
	}

	public void setTimesheetMode(String timesheetMode)
	{
		this.timesheetMode = timesheetMode;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getAssignedTo()
	{
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo)
	{
		this.assignedTo = assignedTo;
	}

	public String getExportFilePath() {
		return exportFilePath;
	}

	public void setExportFilePath(String exportFilePath) {
		this.exportFilePath = exportFilePath;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMinExp() {
		return minExp;
	}

	public void setMinExp(String minExp) {
		this.minExp = minExp;
	}

	public String getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(String maxExp) {
		this.maxExp = maxExp;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getTravelAllowance() {
		return travelAllowance;
	}

	public void setTravelAllowance(String travelAllowance) {
		this.travelAllowance = travelAllowance;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getVisa() {
		return visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getCoolingOff() {
		return coolingOff;
	}

	public void setCoolingOff(String coolingOff) {
		this.coolingOff = coolingOff;
	}

	public String getSelectClient() {
		return selectClient;
	}

	public void setSelectClient(String selectClient) {
		this.selectClient = selectClient;
	}

	public String getSelectContact() {
		return selectContact;
	}

	public void setSelectContact(String selectContact) {
		this.selectContact = selectContact;
	}

	public String getClientRate() {
		return clientRate;
	}

	public void setClientRate(String clientRate) {
		this.clientRate = clientRate;
	}

	public String getClientSubmissionForm()
	{
		return clientSubmissionForm;
	}

	public void setClientSubmissionForm(String clientSubmissionForm)
	{
		this.clientSubmissionForm = clientSubmissionForm;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getParallelApprover()
	{
		return parallelApprover;
	}

	public void setParallelApprover(String parallelApprover)
	{
		this.parallelApprover = parallelApprover;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRequirementSubmissionStatus()
	{
		return requirementSubmissionStatus;
	}

	public void setRequirementSubmissionStatus(String requirementSubmissionStatus)
	{
		this.requirementSubmissionStatus = requirementSubmissionStatus;
	}

	public String getExperienceLevel()
	{
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel)
	{
		this.experienceLevel = experienceLevel;
	}

	public String getApplicationFrom()
	{
		return applicationFrom;
	}

	public void setApplicationFrom(String applicationFrom)
	{
		this.applicationFrom = applicationFrom;
	}

	
}
