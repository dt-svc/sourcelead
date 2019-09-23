package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectMembersDTO> projectMembersList;
	
	@Required(desc="projectMemberId")
	private Long projectMemberId;
	
	@Required(desc="projectId")
	private Long projectId;
	
	private Long masterCandidateId;
	
    private Map<String, List<WeekDaysDTO>> weekDaysMap;
    
    private Map<String,String> timeFrequencies;
	
	private String currentTimeFrequency;
	
	private Map<String,String> timeSubFrequencies;
	
	private LinkedList<String> filledTimeSubFrequencies;
	
	private String currentTimeSubFrequency;
	
	@Required(desc = "timesheetSubmission")
	private TimeSheetSubmissionDTO timesheetSubmission;
	
	private String timesheetFor;
	
	private String profileImageFileUrl;
	
	private String employeeName;
	
	private String employeeEmail;
	
	private String employeePhoneNo;
	
	private String locDateFomat;
	
	private String projectNameForSubmissionPageDisplay;
	
	private String employmentTypeForSubmissionPageDisplay;
	
	private String clientNameForDisplay;
	
	@JsonIgnore
	private Map<String,String>  timesheetFrequencyMap;
	

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public Map<String, String> getTimeFrequencies() {
		return timeFrequencies;
	}

	public void setTimeFrequencies(Map<String, String> timeFrequencies) {
		this.timeFrequencies = timeFrequencies;
	}
	public String getCurrentTimeFrequency() {
		return currentTimeFrequency;
	}

	public void setCurrentTimeFrequency(String currentTimeFrequency) {
		this.currentTimeFrequency = currentTimeFrequency;
	}
	public Map<String, String> getTimeSubFrequencies() {
		return timeSubFrequencies;
	}

	public void setTimeSubFrequencies(Map<String, String> timeSubFrequencies) {
		this.timeSubFrequencies = timeSubFrequencies;
	}

	public String getCurrentTimeSubFrequency() {
		return currentTimeSubFrequency;
	}

	public void setCurrentTimeSubFrequency(String currentTimeSubFrequency) {
		this.currentTimeSubFrequency = currentTimeSubFrequency;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public TimeSheetSubmissionDTO getTimesheetSubmission() {
		return timesheetSubmission;
	}

	public void setTimesheetSubmission(TimeSheetSubmissionDTO timesheetSubmission) {
		this.timesheetSubmission = timesheetSubmission;
	}

	public String getTimesheetFor() {
		return timesheetFor;
	}

	public void setTimesheetFor(String timesheetFor) {
		this.timesheetFor = timesheetFor;
	}

	public Map<String, String> getTimesheetFrequencyMap() {
		return timesheetFrequencyMap;
	}

	public void setTimesheetFrequencyMap(Map<String, String> timesheetFrequencyMap) {
		this.timesheetFrequencyMap = timesheetFrequencyMap;
	}

	public LinkedList<String> getFilledTimeSubFrequencies() {
		return filledTimeSubFrequencies;
	}

	public void setFilledTimeSubFrequencies(LinkedList<String> filledTimeSubFrequencies) {
		this.filledTimeSubFrequencies = filledTimeSubFrequencies;
	}

	public String getProfileImageFileUrl() {
		return profileImageFileUrl;
	}

	public void setProfileImageFileUrl(String profileImageFileUrl) {
		this.profileImageFileUrl = profileImageFileUrl;
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

	public String getEmployeePhoneNo() {
		return employeePhoneNo;
	}

	public void setEmployeePhoneNo(String employeePhoneNo) {
		this.employeePhoneNo = employeePhoneNo;
	}

	public String getLocDateFomat() {
		return locDateFomat;
	}

	public void setLocDateFomat(String locDateFomat) {
		this.locDateFomat = locDateFomat;
	}

	public Map<String, List<WeekDaysDTO>> getWeekDaysMap() {
		return weekDaysMap;
	}

	public void setWeekDaysMap(Map<String, List<WeekDaysDTO>> weekDaysMap) {
		this.weekDaysMap = weekDaysMap;
	}

	public String getProjectNameForSubmissionPageDisplay() {
		return projectNameForSubmissionPageDisplay;
	}

	public void setProjectNameForSubmissionPageDisplay(String projectNameForSubmissionPageDisplay) {
		this.projectNameForSubmissionPageDisplay = projectNameForSubmissionPageDisplay;
	}

	public String getEmploymentTypeForSubmissionPageDisplay() {
		return employmentTypeForSubmissionPageDisplay;
	}

	public void setEmploymentTypeForSubmissionPageDisplay(String employmentTypeForSubmissionPageDisplay) {
		this.employmentTypeForSubmissionPageDisplay = employmentTypeForSubmissionPageDisplay;
	}

	public String getClientNameForDisplay() {
		return clientNameForDisplay;
	}

	public void setClientNameForDisplay(String clientNameForDisplay) {
		this.clientNameForDisplay = clientNameForDisplay;
	}
     
}
