package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECTS")
public class Projects implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@Column(name = "PROJECT_SEQUENCE_NUMBER")
	private String projectSequeneNumber;
	
    @Column(name = "IS_CLIENT_PROJECT")
    private String isClientProject;
	
    @Column(name = "PROJECT_NAME")
    private String projectName;
 
    @Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
    private String description;
 
    @Column(name = "WEEK_START_DATE")
    private Date weekStartDate;
    
    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name = "END_DATE")
    private Date endDate;
    
    @Column(name="INVOICE_FREQUENCY")
	private String invoiceFrequency;
    
    @Column(name = "TIMESHEET_FREQUENCY")
    private String timesheetFrequency;
    
    @Column(name="ESTIMATED_HOURS")
    private Double estimatedHours;
    
    @Column(name="ESTIMATED_BUDGET")
    private Double estimatedBudget;
    
    @Column(name="ALLOW_FL")
    private Boolean allowFreeLance;
    
    @Column(name="ALLOW_REMOTE")
    private Boolean allowRemote;
    
    @Column(name="AllOW_OT")
    private Boolean allowOvertime;
    
    @Column(name="ALLOW_TIME_DEADLINE")
    private Boolean allowTimeEntryAfterDeadLine;
    
    @Column(name="ALLOW_BID")
    private Boolean allowBidding;
    
    @Column(name="ALLOW_OFFSHORE")
    private Boolean allowOffShore;
    
    @Column(name="FLAT_PCT")
    private Double flatPercent;
    
    @Column(name="OVERTIME_RATE_TYPE")
    private String overtimeRateType;

    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERIOR_USER_ID")
   	private User superiorUser;
    
	@Column(name = "PROJECT_STATUS")
	@Enumerated(EnumType.STRING)
	private Project_Status projectStatus;
	
	@Column(name = "IS_MAIL_TO_TEAM_MEMBER")
	@Enumerated(EnumType.STRING)
	private Status isMailToTeamMembers;
	
	@Column(name = "IS_MAIL_TO_PROJECT_MANAGER")
	@Enumerated(EnumType.STRING)
	private Status isMailToProjectManager;
	
	@Column(name = "IS_PRIMARY")
    private Integer isPrimary;
	
	@Column(name = "IS_TIMESHEET_ATTACHMENT_REQUIRED")
	private Integer isTimesheetAttachmentRequired;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_RELATION_ID")
    private OrgRelations orgRelationId;
   
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_LOCATIONS_ID")
    private OrgLocations orgLocationsId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHARED_LOCATION_ID")
    private OrgLocations sharedLocationId;
    
    @Column(name = "IS_PROJECT_SHARED")
    private String isProjectShared;
	
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "IS_DEFAULT_PROJECT")
    private String isDefaultProject;
    
    @Column(name = "PROJECT_UUID")
	private String projectUUID;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Column(name = "PROJECT_SHORT_CODE")
    private String projectShortCode;
    
    @Column(name = "PROJECT_MODE")
    private String projectMode;
    
    @Column(name = "IS_PROJECT_DETAILS_FILLED")
    private String isProjectDetailsFilled;
    
    @Column(name = "IS_PROJECT_MEMBERS_FILLED")
    private String isProjectMembersFilled;
    
    @Column(name = "IS_PROJECT_ADDITIONAL_DETAILS_FILLED")
    private String isProjectAdditionalDetailsFilled;
    
    @Column(name = "NOTIFY_BUDGET_AT")
   	private String notifyBudgetAt;
    
    @Column(name = "IS_TICKET_NUMBER_REQUIRED")
   	private String isTicketNumberRequired;
    
    @Column(name = "IS_TASK_COMMENTS_REQUIRED")
   	private String isTaskCommentsRequired;
    
    @Column(name="IS_PROJECT_FOR_TIMESHEETS")
    private String isProjectForTimeSheets;
    
    @Column(name="IS_PROJECT_FOR_EXPENSES")
    private String isProjectForExpenses;
    
    @Column(name="PROJECT_APPROVAL_TYPE")
	private String projectApprovalType;
    
    @Column(name="EXPENSE_APPROVAL_TYPE")
	private String expenseApprovalType;
	
     
    
	@OneToMany(mappedBy = "projectId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<ProjectBudget> projectBudget = new HashSet<ProjectBudget>(0);
    
    @Transient
    @JsonProperty
    Set<ProjectBudget>  projectBudgetList;
    
    
    @OneToMany(mappedBy="projects",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectMembers> projectMembers;
    
    
    @OneToMany(mappedBy="projectId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectApproverHierarchy> projectApprovers;
    
    
    @OneToMany(mappedBy="projectId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectTasks> projectTasks;
    

    @OneToMany(mappedBy="projectId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectClients> projectClients;
    
    @OneToMany(mappedBy="projectId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectClients> sharedClientProjectClients;

    
    @OneToMany(mappedBy="projects")
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectDocuments> projectDocuments;
    
    
    @OneToMany(mappedBy="projectId")
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectDocumentFolder> ProjectDocumentFolder;
    
    
    @Column(name="CURRENCY")
    private String currency;
    
    @Column(name="IS_REPORTING_MANAGER_AS_APPROVER")
    private String isReportingManagerAsApprover;
    
    public Set<ProjectDocuments> getProjectDocuments() {
		return projectDocuments;
	}

	public void setProjectDocuments(Set<ProjectDocuments> projectDocuments) {
		this.projectDocuments = projectDocuments;
	}

	@Transient
	@JsonProperty
    private String managerLevel1;
    
    @Transient
    @JsonProperty
    private String managerLevel2;
    
    @Transient
    private String managerLevel1UserType;
    
    @Transient
    private String managerLevel2UserType;
    
    @Transient
    private String createdDateStr;
    
    @Transient
    private String updatedDateStr;
    
    @Transient
    @JsonProperty
    private String startDateStr;
    
    @Transient
    @JsonProperty
    private String endDateStr;
    
    @Transient
    @JsonProperty
    private String projStatus;
    
    
    @Transient
    private String[] projectManagerName;
    
    @Transient
    private String[] clientName;
    
    @Transient
    @JsonProperty
    private String client;
    
    @Transient
    private String projectMembersCount;
    
    @Transient
    private Long relationId;
    
    
    @Transient
    private Long manager1;
    
    @Transient
    private Long manager2;
    
    @Transient
    Collection<ProjectApproverHierarchy>  projectApproverHierarchiesList;
    
    @Transient
    Collection<OrgRelations> clientList;
    
    @Transient
    @JsonProperty
    Collection<User> managersList;
    
    
    @Transient
    Collection<User> managersListwithoutManager1;
    
    @Transient
    @JsonProperty
    Set<ProjectApproverHierarchy>  approverHierarchyList;
    
    @Transient
    @JsonProperty
    Set<ExpenseProjectApproverHierarchy>  expenseApproverHierarchyList;
    
    @Transient
    private String employeeStartDateStr;
    
    @Transient
    private String employeeEndDateStr;
    
    @Transient
	private Integer assignedEmpCount;
    
    @Transient
    private String createdByName;

    @Transient
    private String expensesClientName;
    
    @Transient
	@JsonProperty
    private String projectClientUUID;
    
    @Transient
   	@JsonProperty
    private Double projectbudgetAmount;
    
    @Transient
   	@JsonProperty
    private String projectbudgetCurrency;
    
    @Transient
   	@JsonProperty
    private Long sharedClientOrgLocationId;
    
    @Transient
    @JsonProperty
    Set<ProjectDocuments>  projectDocumentsList;
    
    @Transient
    @JsonProperty
    private String projectsTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;
	
    
    @Column(name = "GENERATED_ID")
	private String generatedId;
    
	public Long getId() {
		return id;
	}
	
	public String getProjectSequeneNumber() {
		return projectSequeneNumber;
	}

	public void setProjectSequeneNumber(String projectSequeneNumber) {
		this.projectSequeneNumber = projectSequeneNumber;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getTimesheetFrequency() {
		return timesheetFrequency;
	}

	public void setTimesheetFrequency(String timesheetFrequency) {
		this.timesheetFrequency = timesheetFrequency;
	}
	


	@JsonIgnore
	public User getSuperiorUser() {
		return superiorUser;
	}

	public void setSuperiorUser(User superiorUser) {
		this.superiorUser = superiorUser;
	}

	

	public Project_Status getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Project_Status projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Status getIsMailToTeamMembers() {
		return isMailToTeamMembers;
	}

	public void setIsMailToTeamMembers(Status isMailToTeamMembers) {
		this.isMailToTeamMembers = isMailToTeamMembers;
	}

	public Status getIsMailToProjectManager() {
		return isMailToProjectManager;
	}

	public void setIsMailToProjectManager(Status isMailToProjectManager) {
		this.isMailToProjectManager = isMailToProjectManager;
	}

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}

	@JsonIgnore
	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getProjectShortCode() {
		return projectShortCode;
	}

	public void setProjectShortCode(String projectShortCode) {
		this.projectShortCode = projectShortCode;
	}

	@JsonIgnore
	public Set<ProjectMembers> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Set<ProjectMembers> projectMembers) {
		this.projectMembers = projectMembers;
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
	@JsonIgnore
	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}
	@JsonIgnore
	public Set<ProjectApproverHierarchy> getProjectApprovers() {
		return projectApprovers;
	}

	public void setProjectApprovers(Set<ProjectApproverHierarchy> projectApprovers) {
		this.projectApprovers = projectApprovers;
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

	public String getStartDateStr() {
		if(startDate != null){
			SimpleDateFormat dateFormat = null;
			if(StringUtils.isNotBlank(isProjectShared) && isProjectShared.equalsIgnoreCase(Constants.YES)) {
				if(sharedLocationId != null && sharedLocationId.getDateFormat() != null && StringUtils.isNotEmpty(sharedLocationId.getDateFormat())){
					dateFormat = new SimpleDateFormat(sharedLocationId.getDateFormat());
				}
				else{
					dateFormat = (SimpleDateFormat) Constants.DF_YMD;
				}
			}else {
				if(orgLocationsId != null && orgLocationsId.getDateFormat() != null && StringUtils.isNotEmpty(orgLocationsId.getDateFormat())){
					dateFormat = new SimpleDateFormat(orgLocationsId.getDateFormat());
				}
				else{
					dateFormat = (SimpleDateFormat) Constants.DF_YMD;
				}
			}
			
			startDateStr = dateFormat.format(startDate);	
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) { 
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null){
			SimpleDateFormat dateFormat = null;
			if(StringUtils.isNotBlank(isProjectShared) && isProjectShared.equalsIgnoreCase(Constants.YES)) {
				if(sharedLocationId != null && sharedLocationId.getDateFormat() != null && StringUtils.isNotEmpty(sharedLocationId.getDateFormat())){
					dateFormat = new SimpleDateFormat(sharedLocationId.getDateFormat());
				}
				else{
					dateFormat = (SimpleDateFormat) Constants.DF_YMD;
				}
			}else {
				if(orgLocationsId != null && orgLocationsId.getDateFormat() != null && StringUtils.isNotEmpty(orgLocationsId.getDateFormat())){
					dateFormat = new SimpleDateFormat(orgLocationsId.getDateFormat());
				}
				else{
					dateFormat = (SimpleDateFormat) Constants.DF_YMD;
				}
			}
			
			endDateStr = dateFormat.format(endDate);	
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String[] getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String[] projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String[] getClientName() {
		return clientName;
	}

	public void setClientName(String[] clientName) {
		this.clientName = clientName;
	}

	public Collection<ProjectApproverHierarchy> getProjectApproverHierarchiesList() {
		return projectApproverHierarchiesList;
	}

	public void setProjectApproverHierarchiesList(Collection<ProjectApproverHierarchy> projectApproverHierarchiesList) {
		this.projectApproverHierarchiesList = projectApproverHierarchiesList;
	}
	@JsonIgnore
	public Collection<OrgRelations> getClientList() {
		return clientList;
	}

	public void setClientList(Collection<OrgRelations> clientList) {
		this.clientList = clientList;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProjectMembersCount() {
		return projectMembersCount;
	}

	public void setProjectMembersCount(String projectMembersCount) {
		this.projectMembersCount = projectMembersCount;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Integer getIsTimesheetAttachmentRequired() {
		return isTimesheetAttachmentRequired;
	}

	public void setIsTimesheetAttachmentRequired(Integer isTimesheetAttachmentRequired) {
		this.isTimesheetAttachmentRequired = isTimesheetAttachmentRequired;
	}

	public Integer getAssignedEmpCount() {
		return assignedEmpCount;
	}

	public void setAssignedEmpCount(Integer assignedEmpCount) {
		this.assignedEmpCount = assignedEmpCount;
	}

	public Collection<User> getManagersList() {
		return managersList;
	}

	public void setManagersList(Collection<User> managersList) {
		this.managersList = managersList;
	}

	public Long getManager1() {
		return manager1;
	}

	public void setManager1(Long manager1) {
		this.manager1 = manager1;
	}

	public Long getManager2() {
		return manager2;
	}

	public void setManager2(Long manager2) {
		this.manager2 = manager2;
	}

	public Collection<User> getManagersListwithoutManager1() {
		return managersListwithoutManager1;
	}

	public void setManagersListwithoutManager1(Collection<User> managersListwithoutManager1) {
		this.managersListwithoutManager1 = managersListwithoutManager1;
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

	public Set<ProjectTasks> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(Set<ProjectTasks> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public String getManagerLevel1UserType() {
		return managerLevel1UserType;
	}

	public void setManagerLevel1UserType(String managerLevel1UserType) {
		this.managerLevel1UserType = managerLevel1UserType;
	}

	public String getManagerLevel2UserType() {
		return managerLevel2UserType;
	}

	public void setManagerLevel2UserType(String managerLevel2UserType) {
		this.managerLevel2UserType = managerLevel2UserType;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getExpensesClientName() {
		return expensesClientName;
	}

	public void setExpensesClientName(String expensesClientName) {
		this.expensesClientName = expensesClientName;
	}
	
	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public void setEstimatedBudget(Double estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public Double getEstimatedHours() {
		return estimatedHours;
	}

	public Double getEstimatedBudget() {
		return estimatedBudget;
	}

	public Boolean getAllowFreeLance() {
		return allowFreeLance;
	}

	public void setAllowFreeLance(Boolean allowFreeLance) {
		this.allowFreeLance = allowFreeLance;
	}

	public Boolean getAllowRemote() {
		return allowRemote;
	}

	public void setAllowRemote(Boolean allowRemote) {
		this.allowRemote = allowRemote;
	}

	public Boolean getAllowOvertime() {
		return allowOvertime;
	}

	public void setAllowOvertime(Boolean allowOvertime) {
		this.allowOvertime = allowOvertime;
	}

	public Boolean getAllowTimeEntryAfterDeadLine() {
		return allowTimeEntryAfterDeadLine;
	}

	public void setAllowTimeEntryAfterDeadLine(Boolean allowTimeEntryAfterDeadLine) {
		this.allowTimeEntryAfterDeadLine = allowTimeEntryAfterDeadLine;
	}

	public Boolean getAllowBidding() {
		return allowBidding;
	}

	public void setAllowBidding(Boolean allowBidding) {
		this.allowBidding = allowBidding;
	}

	public Boolean getAllowOffShore() {
		return allowOffShore;
	}

	public void setAllowOffShore(Boolean allowOffShore) {
		this.allowOffShore = allowOffShore;
	}

	public Double getFlatPercent() {
		return flatPercent;
	}

	public void setFlatPercent(Double flatPercent) {
		this.flatPercent = flatPercent;
	}

	public String getOvertimeRateType() {
		return overtimeRateType;
	}

	public void setOvertimeRateType(String overtimeRateType) {
		this.overtimeRateType = overtimeRateType;
	}

	public String getGeneratedId()
	{
		return generatedId;
	}

	public void setGeneratedId(String generatedId)
	{
		this.generatedId = generatedId;
	}

	public String getIsClientProject() {
		return isClientProject;
	}

	public void setIsClientProject(String isClientProject) {
		this.isClientProject = isClientProject;
	}

	public String getProjectMode() {
		return projectMode;
	}

	public void setProjectMode(String projectMode) {
		this.projectMode = projectMode;
	}

	public String getIsProjectDetailsFilled() {
		return isProjectDetailsFilled;
	}

	public void setIsProjectDetailsFilled(String isProjectDetailsFilled) {
		this.isProjectDetailsFilled = isProjectDetailsFilled;
	}

	public String getIsProjectMembersFilled() {
		return isProjectMembersFilled;
	}

	public void setIsProjectMembersFilled(String isProjectMembersFilled) {
		this.isProjectMembersFilled = isProjectMembersFilled;
	}

	public String getIsProjectAdditionalDetailsFilled() {
		return isProjectAdditionalDetailsFilled;
	}

	public void setIsProjectAdditionalDetailsFilled(String isProjectAdditionalDetailsFilled) {
		this.isProjectAdditionalDetailsFilled = isProjectAdditionalDetailsFilled;
	}

	public Set<ProjectClients> getProjectClients() {
		return projectClients;
	}

	public void setProjectClients(Set<ProjectClients> projectClients) {
		this.projectClients = projectClients;
	}

	public Set<ProjectApproverHierarchy> getApproverHierarchyList() {
	   /*  if(projectClients != null){
	    	 for(ProjectClients projectClients : projectClients){
	    		 if(projectClients.getProjectApproverHierarchyList() != null && 
	    				 !projectClients.getProjectApproverHierarchyList().isEmpty()){
	    			 Set<ProjectApproverHierarchy> hirearchySet = new LinkedHashSet<ProjectApproverHierarchy>();
	    			 for(ProjectApproverHierarchy hierarcy : projectClients.getProjectApproverHierarchyList()){
	    				 hirearchySet.add(hierarcy);
		    		 }
	    			 if(hirearchySet != null && !hirearchySet.isEmpty()){
	    				 approverHierarchyList = hirearchySet;
	    			 }
	    		 }
	    	 }
	     }*/
		return approverHierarchyList;
	}

	public void setApproverHierarchyList(Set<ProjectApproverHierarchy> approverHierarchyList) {
		this.approverHierarchyList = approverHierarchyList;
	}

	public Set<ProjectDocuments> getProjectDocumentsList() {
		return projectDocumentsList;
	}

	public void setProjectDocumentsList(Set<ProjectDocuments> projectDocumentsList) {
		this.projectDocumentsList = projectDocumentsList;
	}

	public String getProjStatus() {
		return projStatus;
	}

	public void setProjStatus(String projStatus) {
		this.projStatus = projStatus;
	}

	public String getIsDefaultProject() {
		return isDefaultProject;
	}

	public void setIsDefaultProject(String isDefaultProject) {
		this.isDefaultProject = isDefaultProject;
	}


	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}


	public String getNotifyBudgetAt() {
		return notifyBudgetAt;
	}

	public void setNotifyBudgetAt(String notifyBudgetAt) {
		this.notifyBudgetAt = notifyBudgetAt;
	}

	public Set<ProjectBudget> getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(Set<ProjectBudget> projectBudget) {
		this.projectBudget = projectBudget;
	}

	public Set<ProjectBudget> getProjectBudgetList() {
		return projectBudgetList;
	}

	public void setProjectBudgetList(Set<ProjectBudget> projectBudgetList) {
		this.projectBudgetList = projectBudgetList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
 
	public Double getProjectbudgetAmount() {
		return projectbudgetAmount;
	}

	public void setProjectbudgetAmount(Double projectbudgetAmount) {
		this.projectbudgetAmount = projectbudgetAmount;
	}

	public String getProjectbudgetCurrency() {
		return projectbudgetCurrency;
	}

	public void setProjectbudgetCurrency(String projectbudgetCurrency) {
		this.projectbudgetCurrency = projectbudgetCurrency;
	}

	public Set<ProjectDocumentFolder> getProjectDocumentFolder() {
		return ProjectDocumentFolder;
	}

	public void setProjectDocumentFolder(Set<ProjectDocumentFolder> projectDocumentFolder) {
		ProjectDocumentFolder = projectDocumentFolder;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
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

	public OrgLocations getSharedLocationId() {
		return sharedLocationId;
	}

	public void setSharedLocationId(OrgLocations sharedLocationId) {
		this.sharedLocationId = sharedLocationId;
	}

	public String getIsProjectShared() {
		return isProjectShared;
	}

	public void setIsProjectShared(String isProjectShared) {
		this.isProjectShared = isProjectShared;
	}

	public Set<ProjectClients> getSharedClientProjectClients() {
		return sharedClientProjectClients;
	}

	public void setSharedClientProjectClients(Set<ProjectClients> sharedClientProjectClients) {
		this.sharedClientProjectClients = sharedClientProjectClients;
	}

	public Long getSharedClientOrgLocationId() {
		return sharedClientOrgLocationId;
	}

	public void setSharedClientOrgLocationId(Long sharedClientOrgLocationId) {
		this.sharedClientOrgLocationId = sharedClientOrgLocationId;
	}


	public Set<ExpenseProjectApproverHierarchy> getExpenseApproverHierarchyList() {
		return expenseApproverHierarchyList;
	}

	public void setExpenseApproverHierarchyList(Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList) {
		this.expenseApproverHierarchyList = expenseApproverHierarchyList;
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
	
	public String getProjectsTags() {
		return projectsTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setProjectsTags(String projectsTags) {
		this.projectsTags = projectsTags;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
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
