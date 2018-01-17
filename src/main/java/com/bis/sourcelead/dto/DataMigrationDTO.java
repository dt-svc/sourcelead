package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.entity.Employees;

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
	
	private String fileURL;
	
	private String fileContentType;
	
	private String fileName;
	
	private String projectClientID;
	
	private String docFile;

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
	private String sun;
	private String mon;
	private String tue;
	private String wed;
	private String thu;
	private String fri;
	private String sat;
	private String day1;
	private String day2;
	private String day3;
	private String day4;
	private String day5;
	private String day6;
	private String day7;
	private String day8;
	private String day9;
	private String day10;
	private String day11;
	private String day12;
	private String day13;
	private String day14;
	private String day15;
	private String day16;
	private String day17;
	private String day18;
	private String day19;
	private String day20;
	private String day21;
	private String day22;
	private String day23;
	private String day24;
	private String day25;
	private String day26;
	private String day27;
	private String day28;
	private String day29;
	private String day30;
	private String day31;
	private String totalHours;
	private String billableHours;
	private String nonBillableHours;
	private String weekComments;
	private String monthYear;
	private String timeFrequencyType;
	
	/*Asset Uploads Start*/
	
	private String sno;
	
	private String make;
	
	private String model;
	
	private String name;
	
	private String memory;
	
	private String cpu;
	
	private String harddisk;
	
	private String monitor;
	
	private String category;
	
	private String workingStatus;
	
	private String shiftAEmp;
	
	private String shiftBEmp;
	
	private List<FileInfo> attachmentUpload;
	
	private List<FileInfo> uploadBtndskAUpload;
	
	private List<FileInfo> uploadBtndskBUpload;
	
	private String fileUrl;
	
	private String shiftAFileContentType;
	
	private String shiftAFileUrl;
	
	private String shiftAFileName;
	
	private String shiftBFileContentType;
	
	private String shiftBFileUrl;
	
	private String shiftBFileName;
	
	private List<Employees> employeesList;
	
	private String shiftAEmpName;
	
	private String shiftBEmpName;
	
	private String categoryName;
	
	private String categoryId;
	
	private String shiftAEmployee;
	
	private String shiftBEmployee;
	
	/*Asset Uploads End*/
	
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
	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getFri() {
		return fri;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}
	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	public String getDay4() {
		return day4;
	}

	public void setDay4(String day4) {
		this.day4 = day4;
	}

	public String getDay5() {
		return day5;
	}

	public void setDay5(String day5) {
		this.day5 = day5;
	}

	public String getDay6() {
		return day6;
	}

	public void setDay6(String day6) {
		this.day6 = day6;
	}

	public String getDay7() {
		return day7;
	}

	public void setDay7(String day7) {
		this.day7 = day7;
	}

	public String getDay8() {
		return day8;
	}

	public void setDay8(String day8) {
		this.day8 = day8;
	}

	public String getDay9() {
		return day9;
	}

	public void setDay9(String day9) {
		this.day9 = day9;
	}

	public String getDay10() {
		return day10;
	}

	public void setDay10(String day10) {
		this.day10 = day10;
	}

	public String getDay11() {
		return day11;
	}

	public void setDay11(String day11) {
		this.day11 = day11;
	}

	public String getDay12() {
		return day12;
	}

	public void setDay12(String day12) {
		this.day12 = day12;
	}

	public String getDay13() {
		return day13;
	}

	public void setDay13(String day13) {
		this.day13 = day13;
	}

	public String getDay14() {
		return day14;
	}

	public void setDay14(String day14) {
		this.day14 = day14;
	}

	public String getDay15() {
		return day15;
	}

	public void setDay15(String day15) {
		this.day15 = day15;
	}

	public String getDay16() {
		return day16;
	}

	public void setDay16(String day16) {
		this.day16 = day16;
	}

	public String getDay17() {
		return day17;
	}

	public void setDay17(String day17) {
		this.day17 = day17;
	}

	public String getDay18() {
		return day18;
	}

	public void setDay18(String day18) {
		this.day18 = day18;
	}

	public String getDay19() {
		return day19;
	}

	public void setDay19(String day19) {
		this.day19 = day19;
	}

	public String getDay20() {
		return day20;
	}

	public void setDay20(String day20) {
		this.day20 = day20;
	}

	public String getDay21() {
		return day21;
	}

	public void setDay21(String day21) {
		this.day21 = day21;
	}

	public String getDay22() {
		return day22;
	}

	public void setDay22(String day22) {
		this.day22 = day22;
	}

	public String getDay23() {
		return day23;
	}

	public void setDay23(String day23) {
		this.day23 = day23;
	}

	public String getDay24() {
		return day24;
	}

	public void setDay24(String day24) {
		this.day24 = day24;
	}

	public String getDay25() {
		return day25;
	}

	public void setDay25(String day25) {
		this.day25 = day25;
	}

	public String getDay26() {
		return day26;
	}

	public void setDay26(String day26) {
		this.day26 = day26;
	}

	public String getDay27() {
		return day27;
	}

	public void setDay27(String day27) {
		this.day27 = day27;
	}

	public String getDay28() {
		return day28;
	}

	public void setDay28(String day28) {
		this.day28 = day28;
	}

	public String getDay29() {
		return day29;
	}

	public void setDay29(String day29) {
		this.day29 = day29;
	}

	public String getDay30() {
		return day30;
	}

	public void setDay30(String day30) {
		this.day30 = day30;
	}

	public String getDay31() {
		return day31;
	}

	public void setDay31(String day31) {
		this.day31 = day31;
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
	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getWeekComments() {
		return weekComments;
	}
	
	public String getTimeFrequencyType() {
		return timeFrequencyType;
	}

	public void setTimeFrequencyType(String timeFrequencyType) {
		this.timeFrequencyType = timeFrequencyType;
	}

	public void setWeekComments(String weekComments) {
		this.weekComments = weekComments;
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

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getProjectClientID()
	{
		return projectClientID;
	}

	public void setProjectClientID(String projectClientID)
	{
		this.projectClientID = projectClientID;
	}

	public String getDocFile()
	{
		return docFile;
	}

	public void setDocFile(String docFile)
	{
		this.docFile = docFile;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(String harddisk) {
		this.harddisk = harddisk;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getShiftAEmp() {
		return shiftAEmp;
	}

	public void setShiftAEmp(String shiftAEmp) {
		this.shiftAEmp = shiftAEmp;
	}

	public String getShiftBEmp() {
		return shiftBEmp;
	}

	public void setShiftBEmp(String shiftBEmp) {
		this.shiftBEmp = shiftBEmp;
	}

	public List<FileInfo> getAttachmentUpload() {
		return attachmentUpload;
	}

	public void setAttachmentUpload(List<FileInfo> attachmentUpload) {
		this.attachmentUpload = attachmentUpload;
	}

	public List<FileInfo> getUploadBtndskAUpload() {
		return uploadBtndskAUpload;
	}

	public void setUploadBtndskAUpload(List<FileInfo> uploadBtndskAUpload) {
		this.uploadBtndskAUpload = uploadBtndskAUpload;
	}

	public List<FileInfo> getUploadBtndskBUpload() {
		return uploadBtndskBUpload;
	}

	public void setUploadBtndskBUpload(List<FileInfo> uploadBtndskBUpload) {
		this.uploadBtndskBUpload = uploadBtndskBUpload;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getShiftAFileContentType() {
		return shiftAFileContentType;
	}

	public void setShiftAFileContentType(String shiftAFileContentType) {
		this.shiftAFileContentType = shiftAFileContentType;
	}

	public String getShiftAFileUrl() {
		return shiftAFileUrl;
	}

	public void setShiftAFileUrl(String shiftAFileUrl) {
		this.shiftAFileUrl = shiftAFileUrl;
	}

	public String getShiftAFileName() {
		return shiftAFileName;
	}

	public void setShiftAFileName(String shiftAFileName) {
		this.shiftAFileName = shiftAFileName;
	}

	public String getShiftBFileContentType() {
		return shiftBFileContentType;
	}

	public void setShiftBFileContentType(String shiftBFileContentType) {
		this.shiftBFileContentType = shiftBFileContentType;
	}

	public String getShiftBFileUrl() {
		return shiftBFileUrl;
	}

	public void setShiftBFileUrl(String shiftBFileUrl) {
		this.shiftBFileUrl = shiftBFileUrl;
	}

	public String getShiftBFileName() {
		return shiftBFileName;
	}

	public void setShiftBFileName(String shiftBFileName) {
		this.shiftBFileName = shiftBFileName;
	}

	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	public String getShiftAEmpName() {
		return shiftAEmpName;
	}

	public void setShiftAEmpName(String shiftAEmpName) {
		this.shiftAEmpName = shiftAEmpName;
	}

	public String getShiftBEmpName() {
		return shiftBEmpName;
	}

	public void setShiftBEmpName(String shiftBEmpName) {
		this.shiftBEmpName = shiftBEmpName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getShiftAEmployee() {
		return shiftAEmployee;
	}

	public void setShiftAEmployee(String shiftAEmployee) {
		this.shiftAEmployee = shiftAEmployee;
	}

	public String getShiftBEmployee() {
		return shiftBEmployee;
	}

	public void setShiftBEmployee(String shiftBEmployee) {
		this.shiftBEmployee = shiftBEmployee;
	}

	
}
