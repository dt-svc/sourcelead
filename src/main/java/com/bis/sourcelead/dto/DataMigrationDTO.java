package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.enums.Status;

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
	
	private String inviteEmail;
	
	private String currencyCode;

	private String rateType;

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
	
	// below lines is commented for to move in next release build.!--//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
	private String workflow; // added on 3-6-19 SRINIVAS SABBU for job import functionality.

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
	private String week6;
	private String sun;
	private String mon;
	private String tue;
	private String wed;
	private String thu;
	private String fri;
	private String sat;
	private List<String> days;
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
	
	private List<AssetAssignDTO> assignAsset;
	
	private String assignAssetStr;
	
	private String issueDate;
	
	private String purchaseDate;
	
	private String depreciationDate;
	
	private String operatingSystem;
	
	private String osKey;
	
	private String antiVirus;
	
	private String cost;
	
	private String depreciationAmount;
	
	private String cpuSerialNumber;
	
	private String vendorDetails;
	
	private String macAddress;
	
	/*Asset Uploads End*/
	
   //	Project Tasks
	
    private String taskName;
	
	private String taskBillableType;

	private String epic;
	
	private String taskstype;
	
	private String userStory;
	
	private String subTask;
	
	private String severity;
	
	private String duDate;
	
	private String estimatedDate;
	
	private String estimatedTime;
	
	private String taskStatus;
	
	private String taskDescription;
	
	private String currencyType;
	
	private String businessValue;
	
	private String taskOwner;
	
	private String assignTaskId;
	
	private String projectId;
	
	private String projectUUID;
	
	private String projectClientUUID;
	
	// immigration import fields starts here date 17 May 2018. Srinivas Sabbu.
	
    private String beneficiaryFirstName;
    
    private String beneficiaryLastName;
    
    private String beneficiaryEmail;
    
    private String beneficiaryPhone;
    
    private String experience;
    
    private String ssn;
    
    private String i94Number;
    
    private String i20Number;
    
    private String eadNumber;
    
    private String dateOfBirth;
    
    private String maritalStatus;
    
    private String petitionType;
    
    private String template;
    
    private String dateOfArrival;
    
    private String openOn;
    
    private String status; // Status field in excel as immigration status.
    
    private String visaNo;
    
    private String visaStartDate;
    
    private String visaEndDate;
    
    private String passportNo;
    
    private String passportStartDate;
    
    private String passportEndDate;
    
    private String visaCountry;
    
    private String passportCountry;
    
    private String company;
    
    private String visaType; // name field in excel
    
    private String companyContactEmail; // AttorneyContactemail field in excel.
    
    private String receiptNo;
    
    private String benficiarylocation; // location field in excel
    
    private String lcaStartDate;
    
    private String lcaEndDate;
    
    private String lcaNumber;
    
    private String lcaRateCurrency;
    
    private String lcaRatePer;
    
    
    private String lcaRate;
    
    private String lcaLocation;
    
    private String lcaTitle;
    
    private String immigrationJobId;
    
    private String prevLcaStartDate;
    
    private String prevLcaEndDate;
    
    private String prevLcaNumber;
    
    private String prevLcaTitle;
    
    private String prevLcaLocation;
	
// immigration fields ends here.	
    
    
    
 // Candidate import fields 
    private String firstName;
    
    private String lastName;
    
    private String middleName;
    
    private String email;
    
    private String mobilePhone;
    
    private String alternateMobilePhone;
    
    private String address;
    
    private String zipcode;
    
    private String designation;
    
    private String designationType;
    
    private String experienceYears;
    
    private String vendors;
    
    private String vendor;
    
    private String vendorcontact;
    
    private String currentRate;
    
    private String expectedRate;
    
    private String candidateEmployers;
    
    private String candidateEducation;
    
    private String candidateSkills;
    
    private String summary;
    
    private String primarySkill;
 // Candidate import fields ends
    
	
    
    //Associate import fields
                                     
    private String emailId;			 
                                     
    private String phoneNumber;	 
                                     
    private String extensionNumber;  
                                     
    private String ratePer;			 

    private String dateofJoin;		 

    private String role;			 

    private String invoiceFrequency; 

    private String dayHours; 

    private String dateString;

    private Status isMailChecked;

    private String reason;

    private String vendorContact;

    private String employmentType;

    private String grade;

    private String genderValue;
    
    private String shift;
    
    private String currentCurrency;
    
    private String shiftId;
    
    private String department;
    
    private String roleType;
    
    private String reportingManager;
    
    private String isMailSent;
    
    private String isGlobalAccess;
    
    //NEW LCA Import Excel Fields
    
    private String newLcaNumber;
    
    private String newLcaTitle;
    
    private String noOfPos;
    
    private String empName;
    
    private String empMail;
    
    private String currClientLocation;
    
    private String curPrimrVenodorLocation;
    
    private String curVenodorLocation;
    
    private String socCode;
    
    private String socTitle;
    
    private String socJobTitle;
    
    private String wageLevel;
    
    private String prevailWageCurr;
    
    private String prevailWageRate;
    
    private String prevailWageRatePer;
    
    private String newLcaStartDate;
    
    private String newLcaEndDate;
    
    private String lcaStatus;
    
    private String newStatus;

    // This for associate bulk import to job
    private String jsonData;
    
    private String con;
    
    private Long requirementId;
    
    private String uploadid;

    private String sessionId;
    
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
	
	public String getWeek6() {
		return week6;
	}

	public void setWeek6(String week6) {
		this.week6 = week6;
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
	

	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) {
		this.days = days;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
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

	
	public List<AssetAssignDTO> getAssignAsset() {
		return assignAsset;
	}

	public void setAssignAsset(List<AssetAssignDTO> assignAsset) {
		this.assignAsset = assignAsset;
	}

	public String getAssignAssetStr() {
		return assignAssetStr;
	}

	public void setAssignAssetStr(String assignAssetStr) {
		this.assignAssetStr = assignAssetStr;
	}
	
	public String getIssueDate() {
		return issueDate;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public String getDepreciationDate() {
		return depreciationDate;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public String getOsKey() {
		return osKey;
	}

	public String getAntiVirus() {
		return antiVirus;
	}

	public String getCost() {
		return cost;
	}

	public String getDepreciationAmount() {
		return depreciationAmount;
	}

	public String getCpuSerialNumber() {
		return cpuSerialNumber;
	}

	public String getVendorDetails() {
		return vendorDetails;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setDepreciationDate(String depreciationDate) {
		this.depreciationDate = depreciationDate;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public void setOsKey(String osKey) {
		this.osKey = osKey;
	}

	public void setAntiVirus(String antiVirus) {
		this.antiVirus = antiVirus;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setDepreciationAmount(String depreciationAmount) {
		this.depreciationAmount = depreciationAmount;
	}

	public void setCpuSerialNumber(String cpuSerialNumber) {
		this.cpuSerialNumber = cpuSerialNumber;
	}

	public void setVendorDetails(String vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskBillableType() {
		return taskBillableType;
	}

	public String getEpic() {
		return epic;
	}

	public String getTaskstype() {
		return taskstype;
	}

	public String getUserStory() {
		return userStory;
	}

	public String getSubTask() {
		return subTask;
	}

	public String getSeverity() {
		return severity;
	}

	public String getDuDate() {
		return duDate;
	}

	public String getEstimatedDate() {
		return estimatedDate;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public String getBusinessValue() {
		return businessValue;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskBillableType(String taskBillableType) {
		this.taskBillableType = taskBillableType;
	}

	public void setEpic(String epic) {
		this.epic = epic;
	}

	public void setTaskstype(String taskstype) {
		this.taskstype = taskstype;
	}

	public void setUserStory(String userStory) {
		this.userStory = userStory;
	}

	public void setSubTask(String subTask) {
		this.subTask = subTask;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public void setDuDate(String duDate) {
		this.duDate = duDate;
	}

	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public void setBusinessValue(String businessValue) {
		this.businessValue = businessValue;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	 
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getAssignTaskId() {
		return assignTaskId;
	}

	public void setAssignTaskId(String assignTaskId) {
		this.assignTaskId = assignTaskId;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}

	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}

	public String getBeneficiaryLastName() {
		return beneficiaryLastName;
	}

	public void setBeneficiaryLastName(String beneficiaryLastName) {
		this.beneficiaryLastName = beneficiaryLastName;
	}

	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}

	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getI94Number() {
		return i94Number;
	}

	public void setI94Number(String i94Number) {
		this.i94Number = i94Number;
	}

	public String getI20Number() {
		return i20Number;
	}

	public void setI20Number(String i20Number) {
		this.i20Number = i20Number;
	}

	public String getEadNumber() {
		return eadNumber;
	}

	public void setEadNumber(String eadNumber) {
		this.eadNumber = eadNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(String dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public String getOpenOn() {
		return openOn;
	}

	public void setOpenOn(String openOn) {
		this.openOn = openOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVisaNo() {
		return visaNo;
	}

	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	public String getVisaStartDate() {
		return visaStartDate;
	}

	public void setVisaStartDate(String visaStartDate) {
		this.visaStartDate = visaStartDate;
	}

	public String getVisaEndDate() {
		return visaEndDate;
	}

	public void setVisaEndDate(String visaEndDate) {
		this.visaEndDate = visaEndDate;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyContactEmail() {
		return companyContactEmail;
	}

	public void setCompanyContactEmail(String companyContactEmail) {
		this.companyContactEmail = companyContactEmail;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getBenficiarylocation() {
		return benficiarylocation;
	}

	public void setBenficiarylocation(String benficiarylocation) {
		this.benficiarylocation = benficiarylocation;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassportStartDate() {
		return passportStartDate;
	}

	public void setPassportStartDate(String passportStartDate) {
		this.passportStartDate = passportStartDate;
	}

	public String getPassportEndDate() {
		return passportEndDate;
	}

	public void setPassportEndDate(String passportEndDate) {
		this.passportEndDate = passportEndDate;
	}

	public String getVisaCountry() {
		return visaCountry;
	}

	public void setVisaCountry(String visaCountry) {
		this.visaCountry = visaCountry;
	}

	public String getPassportCountry() {
		return passportCountry;
	}

	public void setPassportCountry(String passportCountry) {
		this.passportCountry = passportCountry;
	}

	

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getLcaStartDate() {
		return lcaStartDate;
	}

	public void setLcaStartDate(String lcaStartDate) {
		this.lcaStartDate = lcaStartDate;
	}

	public String getLcaEndDate() {
		return lcaEndDate;
	}

	public void setLcaEndDate(String lcaEndDate) {
		this.lcaEndDate = lcaEndDate;
	}

	public String getLcaNumber() {
		return lcaNumber;
	}

	public void setLcaNumber(String lcaNumber) {
		this.lcaNumber = lcaNumber;
	}

	public String getLcaRateCurrency() {
		return lcaRateCurrency;
	}

	public void setLcaRateCurrency(String lcaRateCurrency) {
		this.lcaRateCurrency = lcaRateCurrency;
	}

	public String getLcaRatePer() {
		return lcaRatePer;
	}

	public void setLcaRatePer(String lcaRatePer) {
		this.lcaRatePer = lcaRatePer;
	}

	public String getLcaRate() {
		return lcaRate;
	}

	public void setLcaRate(String lcaRate) {
		this.lcaRate = lcaRate;
	}

	public String getLcaLocation() {
		return lcaLocation;
	}

	public void setLcaLocation(String lcaLocation) {
		this.lcaLocation = lcaLocation;
	}

	public String getLcaTitle() {
		return lcaTitle;
	}

	public void setLcaTitle(String lcaTitle) {
		this.lcaTitle = lcaTitle;
	}

	public String getImmigrationJobId() {
		return immigrationJobId;
	}

	public void setImmigrationJobId(String immigrationJobId) {
		this.immigrationJobId = immigrationJobId;
	}

	public String getPrevLcaStartDate() {
		return prevLcaStartDate;
	}

	public void setPrevLcaStartDate(String prevLcaStartDate) {
		this.prevLcaStartDate = prevLcaStartDate;
	}

	public String getPrevLcaEndDate() {
		return prevLcaEndDate;
	}

	public void setPrevLcaEndDate(String prevLcaEndDate) {
		this.prevLcaEndDate = prevLcaEndDate;
	}

	public String getPrevLcaNumber() {
		return prevLcaNumber;
	}

	public void setPrevLcaNumber(String prevLcaNumber) {
		this.prevLcaNumber = prevLcaNumber;
	}

	public String getPrevLcaTitle() {
		return prevLcaTitle;
	}

	public void setPrevLcaTitle(String prevLcaTitle) {
		this.prevLcaTitle = prevLcaTitle;
	}

	public String getPrevLcaLocation() {
		return prevLcaLocation;
	}

	public void setPrevLcaLocation(String prevLcaLocation) {
		this.prevLcaLocation = prevLcaLocation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAlternateMobilePhone() {
		return alternateMobilePhone;
	}

	public void setAlternateMobilePhone(String alternateMobilePhone) {
		this.alternateMobilePhone = alternateMobilePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDesignationType() {
		return designationType;
	}

	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	public String getVendorcontact() {
		return vendorcontact;
	}

	public void setVendorcontact(String vendorcontact) {
		this.vendorcontact = vendorcontact;
	}

	public String getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}
	public String getCandidateEmployers() {
		return candidateEmployers;
	}

	public void setCandidateEmployers(String candidateEmployers) {
		this.candidateEmployers = candidateEmployers;
	}

	public String getCandidateEducation() {
		return candidateEducation;
	}

	public void setCandidateEducation(String candidateEducation) {
		this.candidateEducation = candidateEducation;
	}

	public String getCandidateSkills() {
		return candidateSkills;
	}

	public void setCandidateSkills(String candidateSkills) {
		this.candidateSkills = candidateSkills;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}


	public String getEmailId() {
		return emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getExtensionNumber() {
		return extensionNumber;
	}

	public String getRatePer() {
		return ratePer;
	}

	public String getRole() {
		return role;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}


	public String getDateString() {
		return dateString;
	}

	public Status getIsMailChecked() {
		return isMailChecked;
	}

	public String getReason() {
		return reason;
	}

	public String getVendorContact() {
		return vendorContact;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getGrade() {
		return grade;
	}

	public String getShift() {
		return shift;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}

	public void setRatePer(String ratePer) {
		this.ratePer = ratePer;
	}

	public String getDateofJoin() {
		return dateofJoin;
	}

	public void setDateofJoin(String dateofJoin) {
		this.dateofJoin = dateofJoin;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void setIsMailChecked(Status isMailChecked) {
		this.isMailChecked = isMailChecked;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setVendorContact(String vendorContact) {
		this.vendorContact = vendorContact;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

	public String getCurrentCurrency() {
		return currentCurrency;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setCurrentCurrency(String currentCurrency) {
		this.currentCurrency = currentCurrency;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getDepartment() {
		return department;
	}

	public String getRoleType() {
		return roleType;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
	}

	public String getDayHours() {
		return dayHours;
	}

	public void setDayHours(String dayHours) {
		this.dayHours = dayHours;
	}

	public String getInviteEmail() {
		return inviteEmail;
	}

	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}


	// LCA FIELDS
	
	public String getNewLcaNumber() {
		return newLcaNumber;
	}

	public void setNewLcaNumber(String newLcaNumber) {
		this.newLcaNumber = newLcaNumber;
	}

	public String getNewLcaTitle() {
		return newLcaTitle;
	}

	public void setNewLcaTitle(String newLcaTitle) {
		this.newLcaTitle = newLcaTitle;
	}

	public String getNoOfPos() {
		return noOfPos;
	}

	public void setNoOfPos(String noOfPos) {
		this.noOfPos = noOfPos;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpMail() {
		return empMail;
	}

	public void setEmpMail(String empMail) {
		this.empMail = empMail;
	}

	public String getCurrClientLocation() {
		return currClientLocation;
	}

	public void setCurrClientLocation(String currClientLocation) {
		this.currClientLocation = currClientLocation;
	}

	public String getCurPrimrVenodorLocation() {
		return curPrimrVenodorLocation;
	}

	public void setCurPrimrVenodorLocation(String curPrimrVenodorLocation) {
		this.curPrimrVenodorLocation = curPrimrVenodorLocation;
	}

	public String getCurVenodorLocation() {
		return curVenodorLocation;
	}

	public void setCurVenodorLocation(String curVenodorLocation) {
		this.curVenodorLocation = curVenodorLocation;
	}

	public String getSocCode() {
		return socCode;
	}

	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}

	

	public String getSocTitle() {
		return socTitle;
	}

	public void setSocTitle(String socTitle) {
		this.socTitle = socTitle;
	}

	public String getWageLevel() {
		return wageLevel;
	}

	public void setWageLevel(String wageLevel) {
		this.wageLevel = wageLevel;
	}

	public String getPrevailWageCurr() {
		return prevailWageCurr;
	}

	public void setPrevailWageCurr(String prevailWageCurr) {
		this.prevailWageCurr = prevailWageCurr;
	}

	public String getPrevailWageRate() {
		return prevailWageRate;
	}

	public void setPrevailWageRate(String prevailWageRate) {
		this.prevailWageRate = prevailWageRate;
	}

	public String getPrevailWageRatePer() {
		return prevailWageRatePer;
	}

	public void setPrevailWageRatePer(String prevailWageRatePer) {
		this.prevailWageRatePer = prevailWageRatePer;
	}

	public String getNewLcaStartDate() {
		return newLcaStartDate;
	}

	public void setNewLcaStartDate(String newLcaStartDate) {
		this.newLcaStartDate = newLcaStartDate;
	}

	public String getNewLcaEndDate() {
		return newLcaEndDate;
	}

	public void setNewLcaEndDate(String newLcaEndDate) {
		this.newLcaEndDate = newLcaEndDate;
	}

	public String getLcaStatus() {
		return lcaStatus;
	}

	public void setLcaStatus(String lcaStatus) {
		this.lcaStatus = lcaStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getSocJobTitle() {
		return socJobTitle;
	}

	public void setSocJobTitle(String socJobTitle) {
		this.socJobTitle = socJobTitle;
	}

	public String getUploadid() {
		return uploadid;
	}

	public void setUploadid(String uploadid) {
		this.uploadid = uploadid;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getBeneficiaryPhone() {
		return beneficiaryPhone;
	}

	public void setBeneficiaryPhone(String beneficiaryPhone) {
		this.beneficiaryPhone = beneficiaryPhone;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
   
}
