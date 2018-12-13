package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.annotations.Required;
 
public class ProjectsDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String projectSequeneNumber;
	
	private String expenseId;
	
	@Required(desc="startDate")
	private String startDate;
	
	private String endDate;
	
	@Required(desc="status")
	private String status;
	
	@Required(desc="projectApprovalTypeValue")
	private String projectApprovalTypeValue;
	
	@Required(desc="expenseApprovalTypeValue")
	private String expenseApprovalTypeValue;
	
	private String projectApprovalType;
	
	private String expenseApprovalType;
	
	private String timeSheetFrequency;
	
	private String isAllowFreelanceDevelopers;
	
	private String isAllowRemoteWork;
	
	private String isAllowOverTime;
	
	private String flatPercentageId;
	
	@Required(desc="projectName")
	private String projectName;
	
	@Required(desc="level1Manager")
	private String level1Manager;
	
	private String level2Manager;
	
	@Required(desc="invoiceFrequency")
	private String invoiceFrequency;
	
	private String isTimesheetAttachmentRequired;
	
	private String isAllowOffshoreResource;
	
	private String isTimeEntryAfterDeadline;
	
	private String isAllowBidding;
	
	private String projectDescription;
	
	private String description;
	
	private String projectId;
	
	private Integer estimatedHours;
	
	private Integer estimatedBudget;
	
	private String flatPercentage;
	
	private String staticPath;
	
    private List<Map<String, String>> fileInfo;

	private List<FileInfo> uploadedFileInfo;
	
	private String projectManagers;
	
	private String projectDate;
	
	private String client;
	
	private String projectStatus;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String savedSharedClientOrgLocId;
	
	 private Long sharedClientOrgLocationId;
	 
	 private String isProjectShared;
	
	private String estimatedBudgetCurrency;
	
	private String budgetApproverUser;
	
	private String notifyBudgetAt;
	
	private String budgetStatus;
	
	private String projectShortCode;
	
	private String projectStatusId;
	
	private String endDateStr1;
	
	List<ExpensesCategoryListDTO> expenseCategoryList;
	
	List<ProjectMembersDTO> projectMembersList;
	
	List<ClientOrgRelationsDTO> clientsList;
	
	private String reStartDate;
	
	private String reEndDate;
	
	private String projectMode;
	
	private String projectUUID;
	
	private String projectClientUUID;
	
	private String employeeStartDateStr;
	
	private String employeeEndDateStr;
	
	private String managerLevel1;
	
	private String managerLevel2;
	
	private String projectbudgetCurrency;
	
	private double projectbudgetAmount;
	
	private String createdDateStr;
	
	private String updatedDateStr;
	
	private String createdByName;
	
	private int manager1;
	
	private int manager2;
	
	private String managerLevel1UserType;
	
	private Collection<UserDTO> managersList;
	
	private Collection<UserDTO> ExpenseApproversList;
	
	private Collection<UserDTO> managersListwithoutManager1;
	
	private String[] projectManagerName;
	
	private String projectMembersCount;
	
	private String allowFreeLance;
	
	private String allowRemote;
	
	private String allowOvertime;
	
	private String allowTimeEntryAfterDeadLine;
	
	private String allowBidding;
	
	private String allowOffShore;
	
	private String overtimeRateType;
	
	private int orgLocationId;
	
	private String clientlocationId;
	
	private String manager1Name;
	
	private String manager2Name;
	
	private String flatPercent; 
	
	private String orgRelationUUID;
	
	private String orgRelationId;
	
	private Map<Long,String> projectCustomFieldlabelNameAndValue;
	
	private List<CustomFormFieldDTO> customFormData;
	
	private String projectApprover;
	
	private String projectType;
	
	private String exportType;
	
	private String values;
	
	private String clients;
	
	private String isGlobalAccess;
	
	private long orgLocationsId;
	
	private Long orgId;
	
	private String dateFormat;
	
	private String isReportingManagerAsApprover;
	
	private String isTicketNumberRequired;
	
	private String isTaskCommentsRequired;
	
	private String sharedClientOrgLocId;
	
	private Long sharedProjectCreatedOrgLocId;
	
	private BigInteger projId;
	
	private String level1ExpenseApprover;
	
	private String level2ExpenseApprover;
	
    private String isProjectForTimeSheets;
	
	private String isProjectForExpenses;
	
	//@Required(desc="projectForValue")
	private String projectForValue;
	
	
	private int expenseApprover1;
	
	private int expenseApprover2;
	
	private String expenseApprover1Name;
	
	private String expenseApprover2Name;
	
	private String expenseApproverLevel1UserType;
	
	private String expenseApproverLevel1;
	
	private String expenseApproverLevel2;
	  
	private Collection<UserDTO> expensesApproversListwithoutApprover1;
	
	 private String tags;
	
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getProjectSequeneNumber() {
		return projectSequeneNumber;
	}

	public void setProjectSequeneNumber(String projectSequeneNumber) {
		this.projectSequeneNumber = projectSequeneNumber;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeSheetFrequency() {
		return timeSheetFrequency;
	}

	public void setTimeSheetFrequency(String timeSheetFrequency) {
		this.timeSheetFrequency = timeSheetFrequency;
	}

	public String getIsAllowFreelanceDevelopers() {
		return isAllowFreelanceDevelopers;
	}

	public void setIsAllowFreelanceDevelopers(String isAllowFreelanceDevelopers) {
		this.isAllowFreelanceDevelopers = isAllowFreelanceDevelopers;
	}

	public String getIsAllowRemoteWork() {
		return isAllowRemoteWork;
	}

	public void setIsAllowRemoteWork(String isAllowRemoteWork) {
		this.isAllowRemoteWork = isAllowRemoteWork;
	}

	public String getIsAllowOverTime() {
		return isAllowOverTime;
	}

	public void setIsAllowOverTime(String isAllowOverTime) {
		this.isAllowOverTime = isAllowOverTime;
	}

	public String getFlatPercentageId() {
		return flatPercentageId;
	}

	public void setFlatPercentageId(String flatPercentageId) {
		this.flatPercentageId = flatPercentageId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLevel1Manager() {
		return level1Manager;
	}

	public void setLevel1Manager(String level1Manager) {
		this.level1Manager = level1Manager;
	}

	public String getLevel2Manager() {
		return level2Manager;
	}

	public void setLevel2Manager(String level2Manager) {
		this.level2Manager = level2Manager;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getIsTimesheetAttachmentRequired() {
		return isTimesheetAttachmentRequired;
	}

	public void setIsTimesheetAttachmentRequired(String isTimesheetAttachmentRequired) {
		this.isTimesheetAttachmentRequired = isTimesheetAttachmentRequired;
	}

	public String getIsAllowOffshoreResource() {
		return isAllowOffshoreResource;
	}

	public void setIsAllowOffshoreResource(String isAllowOffshoreResource) {
		this.isAllowOffshoreResource = isAllowOffshoreResource;
	}

	public String getIsTimeEntryAfterDeadline() {
		return isTimeEntryAfterDeadline;
	}

	public void setIsTimeEntryAfterDeadline(String isTimeEntryAfterDeadline) {
		this.isTimeEntryAfterDeadline = isTimeEntryAfterDeadline;
	}

	public String getIsAllowBidding() {
		return isAllowBidding;
	}

	public void setIsAllowBidding(String isAllowBidding) {
		this.isAllowBidding = isAllowBidding;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(String projectManagers) {
		this.projectManagers = projectManagers;
	}

	public String getProjectDate() {
		return projectDate;
	}

	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
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

	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}

	public String getBudgetApproverUser() {
		return budgetApproverUser;
	}

	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}

	public void setBudgetApproverUser(String budgetApproverUser) {
		this.budgetApproverUser = budgetApproverUser;
	}

	public String getNotifyBudgetAt() {
		return notifyBudgetAt;
	}

	public void setNotifyBudgetAt(String notifyBudgetAt) {
		this.notifyBudgetAt = notifyBudgetAt;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public String getProjectShortCode() {
		return projectShortCode;
	}

	public void setProjectShortCode(String projectShortCode) {
		this.projectShortCode = projectShortCode;
	}

	public List<ExpensesCategoryListDTO> getExpenseCategoryList() {
		return expenseCategoryList;
	}

	public void setExpenseCategoryList(List<ExpensesCategoryListDTO> expenseCategoryList) {
		this.expenseCategoryList = expenseCategoryList;
	}

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectStatusId() {
		return projectStatusId;
	}

	public void setProjectStatusId(String projectStatusId) {
		this.projectStatusId = projectStatusId;
	}

	public String getEndDateStr1() {
		return endDateStr1;
	}

	public void setEndDateStr1(String endDateStr1) {
		this.endDateStr1 = endDateStr1;
	}

	public String getReStartDate() {
		return reStartDate;
	}

	public void setReStartDate(String reStartDate) {
		this.reStartDate = reStartDate;
	}

	public String getReEndDate() {
		return reEndDate;
	}

	public void setReEndDate(String reEndDate) {
		this.reEndDate = reEndDate;
	}

	public String getProjectMode() {
		return projectMode;
	}

	public void setProjectMode(String projectMode) {
		this.projectMode = projectMode;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public String getEmployeeStartDateStr() {
		return employeeStartDateStr;
	}

	public void setEmployeeStartDateStr(String employeeStartDateStr) {
		this.employeeStartDateStr = employeeStartDateStr;
	}

	public String getEmployeeEndDateStr() {
		return employeeEndDateStr;
	}

	public void setEmployeeEndDateStr(String employeeEndDateStr) {
		this.employeeEndDateStr = employeeEndDateStr;
	}

	public String getManagerLevel1() {
		return managerLevel1;
	}

	public void setManagerLevel1(String managerLevel1) {
		this.managerLevel1 = managerLevel1;
	}

	public String getManagerLevel2() {
		return managerLevel2;
	}

	public void setManagerLevel2(String managerLevel2) {
		this.managerLevel2 = managerLevel2;
	}

	public String getProjectbudgetCurrency() {
		return projectbudgetCurrency;
	}

	public void setProjectbudgetCurrency(String projectbudgetCurrency) {
		this.projectbudgetCurrency = projectbudgetCurrency;
	}

	 

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getUpdatedDateStr() {
		return updatedDateStr;
	}

	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	

	public String getManagerLevel1UserType() {
		return managerLevel1UserType;
	}

	public void setManagerLevel1UserType(String managerLevel1UserType) {
		this.managerLevel1UserType = managerLevel1UserType;
	}

	public Collection<UserDTO> getManagersList() {
		return managersList;
	}

	public void setManagersList(Collection<UserDTO> managersList) {
		this.managersList = managersList;
	}

	public Collection<UserDTO> getManagersListwithoutManager1() {
		return managersListwithoutManager1;
	}

	public void setManagersListwithoutManager1(Collection<UserDTO> managersListwithoutManager1) {
		this.managersListwithoutManager1 = managersListwithoutManager1;
	}

	public String[] getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String[] projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String getProjectMembersCount() {
		return projectMembersCount;
	}

	public void setProjectMembersCount(String projectMembersCount) {
		this.projectMembersCount = projectMembersCount;
	}

	public String getAllowFreeLance() {
		return allowFreeLance;
	}

	public void setAllowFreeLance(String allowFreeLance) {
		this.allowFreeLance = allowFreeLance;
	}

	public String getAllowRemote() {
		return allowRemote;
	}

	public void setAllowRemote(String allowRemote) {
		this.allowRemote = allowRemote;
	}

	public String getAllowOvertime() {
		return allowOvertime;
	}

	public void setAllowOvertime(String allowOvertime) {
		this.allowOvertime = allowOvertime;
	}

	public String getAllowTimeEntryAfterDeadLine() {
		return allowTimeEntryAfterDeadLine;
	}

	public void setAllowTimeEntryAfterDeadLine(String allowTimeEntryAfterDeadLine) {
		this.allowTimeEntryAfterDeadLine = allowTimeEntryAfterDeadLine;
	}

	public String getAllowBidding() {
		return allowBidding;
	}

	public void setAllowBidding(String allowBidding) {
		this.allowBidding = allowBidding;
	}

	public String getAllowOffShore() {
		return allowOffShore;
	}

	public void setAllowOffShore(String allowOffShore) {
		this.allowOffShore = allowOffShore;
	}

	public String getOvertimeRateType() {
		return overtimeRateType;
	}

	public void setOvertimeRateType(String overtimeRateType) {
		this.overtimeRateType = overtimeRateType;
	}

	public String getManager1Name() {
		return manager1Name;
	}

	public void setManager1Name(String manager1Name) {
		this.manager1Name = manager1Name;
	}

	public String getManager2Name() {
		return manager2Name;
	}

	public void setManager2Name(String manager2Name) {
		this.manager2Name = manager2Name;
	}

	public String getFlatPercent() {
		return flatPercent;
	}

	public void setFlatPercent(String flatPercent) {
		this.flatPercent = flatPercent;
	}

	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public Map<Long, String> getProjectCustomFieldlabelNameAndValue() {
		return projectCustomFieldlabelNameAndValue;
	}

	public void setProjectCustomFieldlabelNameAndValue(Map<Long, String> projectCustomFieldlabelNameAndValue) {
		this.projectCustomFieldlabelNameAndValue = projectCustomFieldlabelNameAndValue;
	}

	public List<CustomFormFieldDTO> getCustomFormData() {
		return customFormData;
	}

	public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
		this.customFormData = customFormData;
	}

	public List<ClientOrgRelationsDTO> getClientsList() {
		return clientsList;
	}

	public void setClientsList(List<ClientOrgRelationsDTO> clientsList) {
		this.clientsList = clientsList;
	}

	public String getClientlocationId() {
		return clientlocationId;
	}

	public void setClientlocationId(String clientlocationId) {
		this.clientlocationId = clientlocationId;
	}


	public String getProjectApprover()
	{
		return projectApprover;
	}

	public void setProjectApprover(String projectApprover)
	{
		this.projectApprover = projectApprover;
	}

	
	public double getProjectbudgetAmount() {
		return projectbudgetAmount;
	}
 

	public void setProjectbudgetAmount(double projectbudgetAmount) {
		this.projectbudgetAmount = projectbudgetAmount;
	}

	public int getManager1() {
		return manager1;
	}

	public int getManager2() {
		return manager2;
	}

	public int getOrgLocationId() {
		return orgLocationId;
	}

	public void setManager1(int manager1) {
		this.manager1 = manager1;
	}

	public void setManager2(int manager2) {
		this.manager2 = manager2;
	}

	public void setOrgLocationId(int orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Integer estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Integer getEstimatedBudget() {
		return estimatedBudget;
	}

	public void setEstimatedBudget(Integer estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public String getFlatPercentage() {
		return flatPercentage;
	}

	public void setFlatPercentage(String flatPercentage) {
		this.flatPercentage = flatPercentage;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getExportType() {
		return exportType;
	}

	public String getValues() {
		return values;
	}

	public String getClients() {
		return clients;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public long getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(long orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}

	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getIsReportingManagerAsApprover() {
		return isReportingManagerAsApprover;
	}

	public void setIsReportingManagerAsApprover(String isReportingManagerAsApprover) {
		this.isReportingManagerAsApprover = isReportingManagerAsApprover;
	}

	public String getIsTicketNumberRequired() {
		return isTicketNumberRequired;
	}

	public void setIsTicketNumberRequired(String isTicketNumberRequired) {
		this.isTicketNumberRequired = isTicketNumberRequired;
	}

	public String getIsTaskCommentsRequired() {
		return isTaskCommentsRequired;
	}

	public void setIsTaskCommentsRequired(String isTaskCommentsRequired) {
		this.isTaskCommentsRequired = isTaskCommentsRequired;
	}

	public String getSharedClientOrgLocId() {
		return sharedClientOrgLocId;
	}

	public void setSharedClientOrgLocId(String sharedClientOrgLocId) {
		this.sharedClientOrgLocId = sharedClientOrgLocId;
	}

	public String getSavedSharedClientOrgLocId() {
		return savedSharedClientOrgLocId;
	}

	public void setSavedSharedClientOrgLocId(String savedSharedClientOrgLocId) {
		this.savedSharedClientOrgLocId = savedSharedClientOrgLocId; 
	}

	public Long getSharedClientOrgLocationId() {
		return sharedClientOrgLocationId;
	}

	public void setSharedClientOrgLocationId(Long sharedClientOrgLocationId) {
		this.sharedClientOrgLocationId = sharedClientOrgLocationId;
	}

	public String getIsProjectShared() {
		return isProjectShared;
	}

	public void setIsProjectShared(String isProjectShared) {
		this.isProjectShared = isProjectShared;
	}

	public Long getSharedProjectCreatedOrgLocId() {
		return sharedProjectCreatedOrgLocId;
	}

	public void setSharedProjectCreatedOrgLocId(Long sharedProjectCreatedOrgLocId) {
		this.sharedProjectCreatedOrgLocId = sharedProjectCreatedOrgLocId;
	}

	public BigInteger getProjId() {
		return projId;
	}

	public void setProjId(BigInteger projId) {
		this.projId = projId;
	}


	public String getLevel1ExpenseApprover() {
		return level1ExpenseApprover;
	}

	public void setLevel1ExpenseApprover(String level1ExpenseApprover) {
		this.level1ExpenseApprover = level1ExpenseApprover;
	}

	public String getLevel2ExpenseApprover() {
		return level2ExpenseApprover;
	}

	public void setLevel2ExpenseApprover(String level2ExpenseApprover) {
		this.level2ExpenseApprover = level2ExpenseApprover;
	}

	public String getIsProjectForTimeSheets() {
		return isProjectForTimeSheets;
	}

	public void setIsProjectForTimeSheets(String isProjectForTimeSheets) {
		this.isProjectForTimeSheets = isProjectForTimeSheets;
	}

	public String getIsProjectForExpenses() {
		return isProjectForExpenses;
	}

	public void setIsProjectForExpenses(String isProjectForExpenses) {
		this.isProjectForExpenses = isProjectForExpenses;
	}

	public String getProjectForValue() {
		return projectForValue;
	}

	public void setProjectForValue(String projectForValue) {
		this.projectForValue = projectForValue;
	}

	public int getExpenseApprover1() {
		return expenseApprover1;
	}

	public void setExpenseApprover1(int expenseApprover1) {
		this.expenseApprover1 = expenseApprover1;
	}

	public int getExpenseApprover2() {
		return expenseApprover2;
	}

	public void setExpenseApprover2(int expenseApprover2) {
		this.expenseApprover2 = expenseApprover2;
	}

	public String getExpenseApprover1Name() {
		return expenseApprover1Name;
	}

	public void setExpenseApprover1Name(String expenseApprover1Name) {
		this.expenseApprover1Name = expenseApprover1Name;
	}

	public String getExpenseApprover2Name() {
		return expenseApprover2Name;
	}

	public void setExpenseApprover2Name(String expenseApprover2Name) {
		this.expenseApprover2Name = expenseApprover2Name;
	}

	public String getExpenseApproverLevel1UserType() {
		return expenseApproverLevel1UserType;
	}

	public void setExpenseApproverLevel1UserType(String expenseApproverLevel1UserType) {
		this.expenseApproverLevel1UserType = expenseApproverLevel1UserType;
	}

	public String getExpenseApproverLevel1() {
		return expenseApproverLevel1;
	}

	public void setExpenseApproverLevel1(String expenseApproverLevel1) {
		this.expenseApproverLevel1 = expenseApproverLevel1;
	}

	public String getExpenseApproverLevel2() {
		return expenseApproverLevel2;
	}

	public void setExpenseApproverLevel2(String expenseApproverLevel2) {
		this.expenseApproverLevel2 = expenseApproverLevel2;
	}

	public Collection<UserDTO> getExpensesApproversListwithoutApprover1() {
		return expensesApproversListwithoutApprover1;
	}

	public void setExpensesApproversListwithoutApprover1(Collection<UserDTO> expensesApproversListwithoutApprover1) {
		this.expensesApproversListwithoutApprover1 = expensesApproversListwithoutApprover1;
	}

	public Collection<UserDTO> getExpenseApproversList() {
		return ExpenseApproversList;
	}

	public void setExpenseApproversList(Collection<UserDTO> expenseApproversList) {
		ExpenseApproversList = expenseApproversList;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getProjectApprovalTypeValue() {
		return projectApprovalTypeValue;
	}

	public void setProjectApprovalTypeValue(String projectApprovalTypeValue) {
		this.projectApprovalTypeValue = projectApprovalTypeValue;
	}

	public String getExpenseApprovalTypeValue() {
		return expenseApprovalTypeValue;
	}

	public void setExpenseApprovalTypeValue(String expenseApprovalTypeValue) {
		this.expenseApprovalTypeValue = expenseApprovalTypeValue;
	}

	public String getProjectApprovalType() {
		return projectApprovalType;
	}

	public void setProjectApprovalType(String projectApprovalType) {
		this.projectApprovalType = projectApprovalType;
	}

	public String getExpenseApprovalType() {
		return expenseApprovalType;
	}

	public void setExpenseApprovalType(String expenseApprovalType) {
		this.expenseApprovalType = expenseApprovalType;
	}
	

}
