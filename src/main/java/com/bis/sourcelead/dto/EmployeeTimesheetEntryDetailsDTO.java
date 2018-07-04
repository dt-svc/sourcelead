package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeTimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private LinkedHashSet<String> invoiceFrequencyList;
	
	private Map<String,String> weekFrequencyList;
	
	private Long masterCandidateId;
	
	private String currentTimeFrequency;
	
	private String currentTimeSubFrequency;
	
	private Map<String,String> timeSubFrequencies;
	
	private List<ProjectMembersDTO> projectMembersList;
	
	private List<ProjectMembersDTO> submittedProjectMembersList;
	
	private List<ProjectMembersDTO> allProjectMembersList;
	
	private List<ProjectMembersDTO> attProjectMembersList;
	
	private List<EmployeeTimesheetSubmissionDTO> submissionList;
	
	private EmployeeTimesheetSubmissionDTO timesheetSubmission;
	
	private List<EmployeeTimesheetFileInfoDTO> timsheetAttachments;
	
	private List<Long> oldSubmittedProjectMembersList;
	
	private Map<Long,EmployeeTimeSheetSubmissionStatusDTO> statusMap;
	
	private Map<String,List<EmployeeLeaveDTO>> empLeaveMap;
	
    private boolean monEditable;
	
	private boolean tueEditable;
	
	private boolean wedEditable;
	
	private boolean thuEditable;
	
	private boolean friEditable;
	
	private boolean satEditable;
	
	private boolean sunEditable;
	
	private boolean timesheetStatus;
	
	private String timesheetFor;
	
	private String invoiceFrequency;
	
	private List<WeekDaysDTO> weekDays;
	
	private String locDateFomat;
	
	private Integer maxRowCount;
	
	private boolean submitButtonVisible;
	
	private boolean saveButtonVisible;
	
	private boolean leaveOnFullWeek;

	public LinkedHashSet<String> getInvoiceFrequencyList() {
		return invoiceFrequencyList;
	}

	public void setInvoiceFrequencyList(LinkedHashSet<String> invoiceFrequencyList) {
		this.invoiceFrequencyList = invoiceFrequencyList;
	}

	public Map<String, String> getWeekFrequencyList() {
		return weekFrequencyList;
	}

	public void setWeekFrequencyList(Map<String, String> weekFrequencyList) {
		this.weekFrequencyList = weekFrequencyList;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public String getCurrentTimeFrequency() {
		return currentTimeFrequency;
	}

	public void setCurrentTimeFrequency(String currentTimeFrequency) {
		this.currentTimeFrequency = currentTimeFrequency;
	}

	public String getCurrentTimeSubFrequency() {
		return currentTimeSubFrequency;
	}

	public void setCurrentTimeSubFrequency(String currentTimeSubFrequency) {
		this.currentTimeSubFrequency = currentTimeSubFrequency;
	}

	public Map<String, String> getTimeSubFrequencies() {
		return timeSubFrequencies;
	}

	public void setTimeSubFrequencies(Map<String, String> timeSubFrequencies) {
		this.timeSubFrequencies = timeSubFrequencies;
	}

	public String getTimesheetFor() {
		return timesheetFor;
	}

	public void setTimesheetFor(String timesheetFor) {
		this.timesheetFor = timesheetFor;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public List<WeekDaysDTO> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<WeekDaysDTO> weekDays) {
		this.weekDays = weekDays;
	}

	public String getLocDateFomat() {
		return locDateFomat;
	}

	public void setLocDateFomat(String locDateFomat) {
		this.locDateFomat = locDateFomat;
	}

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public List<EmployeeTimesheetSubmissionDTO> getSubmissionList() {
		return submissionList;
	}

	public void setSubmissionList(List<EmployeeTimesheetSubmissionDTO> submissionList) {
		this.submissionList = submissionList;
	}

	public EmployeeTimesheetSubmissionDTO getTimesheetSubmission() {
		return timesheetSubmission;
	}

	public void setTimesheetSubmission(EmployeeTimesheetSubmissionDTO timesheetSubmission) {
		this.timesheetSubmission = timesheetSubmission;
	}

	public Integer getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(Integer maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	

	public Map<Long, EmployeeTimeSheetSubmissionStatusDTO> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Long, EmployeeTimeSheetSubmissionStatusDTO> statusMap) {
		this.statusMap = statusMap;
	}

	public boolean isMonEditable() {
		return monEditable;
	}

	public void setMonEditable(boolean monEditable) {
		this.monEditable = monEditable;
	}

	public boolean isTueEditable() {
		return tueEditable;
	}

	public void setTueEditable(boolean tueEditable) {
		this.tueEditable = tueEditable;
	}

	public boolean isWedEditable() {
		return wedEditable;
	}

	public void setWedEditable(boolean wedEditable) {
		this.wedEditable = wedEditable;
	}

	public boolean isThuEditable() {
		return thuEditable;
	}

	public void setThuEditable(boolean thuEditable) {
		this.thuEditable = thuEditable;
	}

	public boolean isFriEditable() {
		return friEditable;
	}

	public void setFriEditable(boolean friEditable) {
		this.friEditable = friEditable;
	}

	public boolean isSatEditable() {
		return satEditable;
	}

	public void setSatEditable(boolean satEditable) {
		this.satEditable = satEditable;
	}

	public boolean isSunEditable() {
		return sunEditable;
	}

	public void setSunEditable(boolean sunEditable) {
		this.sunEditable = sunEditable;
	}

	public boolean isTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(boolean timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public boolean isSubmitButtonVisible() {
		return submitButtonVisible;
	}

	public void setSubmitButtonVisible(boolean submitButtonVisible) {
		this.submitButtonVisible = submitButtonVisible;
	}

	public List<ProjectMembersDTO> getSubmittedProjectMembersList() {
		return submittedProjectMembersList;
	}

	public void setSubmittedProjectMembersList(List<ProjectMembersDTO> submittedProjectMembersList) {
		this.submittedProjectMembersList = submittedProjectMembersList;
	}

	public List<EmployeeTimesheetFileInfoDTO> getTimsheetAttachments() {
		return timsheetAttachments;
	}

	public void setTimsheetAttachments(List<EmployeeTimesheetFileInfoDTO> timsheetAttachments) {
		this.timsheetAttachments = timsheetAttachments;
	}

	public List<ProjectMembersDTO> getAllProjectMembersList() {
		return allProjectMembersList;
	}

	public void setAllProjectMembersList(List<ProjectMembersDTO> allProjectMembersList) {
		this.allProjectMembersList = allProjectMembersList;
	}

	public List<ProjectMembersDTO> getAttProjectMembersList() {
		return attProjectMembersList;
	}

	public void setAttProjectMembersList(List<ProjectMembersDTO> attProjectMembersList) {
		this.attProjectMembersList = attProjectMembersList;
	}

	public boolean isSaveButtonVisible() {
		return saveButtonVisible;
	}

	public void setSaveButtonVisible(boolean saveButtonVisible) {
		this.saveButtonVisible = saveButtonVisible;
	}

	public List<Long> getOldSubmittedProjectMembersList() {
		return oldSubmittedProjectMembersList;
	}

	public void setOldSubmittedProjectMembersList(List<Long> oldSubmittedProjectMembersList) {
		this.oldSubmittedProjectMembersList = oldSubmittedProjectMembersList;
	}

	public Map<String, List<EmployeeLeaveDTO>> getEmpLeaveMap() {
		return empLeaveMap;
	}

	public void setEmpLeaveMap(Map<String, List<EmployeeLeaveDTO>> empLeaveMap) {
		this.empLeaveMap = empLeaveMap;
	}

	public boolean isLeaveOnFullWeek() {
		return leaveOnFullWeek;
	}

	public void setLeaveOnFullWeek(boolean leaveOnFullWeek) {
		this.leaveOnFullWeek = leaveOnFullWeek;
	}

	
     
}
