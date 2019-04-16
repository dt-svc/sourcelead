package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.bis.sourcelead.beans.TasksGraphBean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "PROJECT_TASKS")
public class ProjectTasks implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "TASK_NAME")
	private String taskName;
	
	@Column(name = "PROJECT_TASKS_UUID")
	private String projectTasksUUID;
		 
	@Column(name = "GENERATED_ID")
	private String generatedId;
	  
	@Column(name = "DESCRIPTION" , columnDefinition = "LONGTEXT default NULL")
	@Type(type="text")
	private String description;
	
	@Column(name = "BILLABLE_TYPE")
	private String billableType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TASK_TYPE", nullable=false )	
	private LookUpData taskType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projectId;
	
	@Column(name = "DUE_DATE")
    private Date dueDate; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRIORITY", nullable=false )	
	private LookUpData priority;
	
	@Column(name = "ESTIMATE_DATE")
    private Date estimateDate;
	
	@Column(name = "ESTIMATE_TIME")
    private String estimateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SEVERITY", nullable=false )	
	private LookUpData severity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TASK_STATUS", nullable=false )	
	private LookUpData taskStatus;
	
	@Column(name = "BUSINESS_VALUE_CURRENCY")
	private String businessCurrency;
	
	@Column(name = "BUSINESS_VALUE")
	private String businessValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OWNER_USER_ID", nullable=false )	
	private User ownerUserId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_TASK_ID", nullable=false )	
	private ProjectTasks parentTaskId;
	
	@Column(name = "OWNER_NAME")
	private String ownerName;
	
	@Column(name = "PERCENTAGE")
	private Integer percentage;
	
	@Column(name = "STATUS")
	private String status;
	
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "CREATED_DATE")
    private Date createdDate; 
		
    @Column(name = "CREATED_BY")
	private String createdBy;
		
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
    private Date updatedDate; 
		
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEAVE_TYPE_ID")
	private LeaveType leaveType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOLIDAY_ID")
	private Holiday holiday;
	
    @OneToMany(mappedBy="projectTaskId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<ProjectAssignTask> projectAssignTask;
    
	@OneToMany(mappedBy = "projectTaskId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
    private List<ProjectTaskActivity> projectTaskActivity;

	@OneToMany(mappedBy="projectTaskId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<ProjectTaskDocuments> projectTaskDocuments = new HashSet<>();
	
	@Transient
	@JsonProperty
	List<TasksGraphBean> tasksGraphBean;
	
	@JsonProperty
	@Transient
	private String taskPriotiry;
	
	@JsonProperty
	@Transient
	private String taskSeverity;
	
	@JsonProperty
	@Transient
	private String taskOwnerName;
	
	@JsonProperty
	@Transient
	private String taskStaus;
	
	@JsonProperty
	@Transient
	private String taskTypes;
	
	@JsonProperty
	@Transient
	private List<String> taskAssignedUsers;
	
	
	@JsonProperty
	@Transient
	private List<String> assignedTaskUsers;
	
	
	@JsonProperty
	@Transient
	private String createdDateStr;
	
	@JsonProperty
	@Transient
	private Integer newCount;
	
	
	@JsonProperty
	@Transient
	private Integer inProgressCount;
	
	@JsonProperty
	@Transient
	private Integer onHoldCount;
	
	@JsonProperty
	@Transient
	private Integer doneCount;
	
	@JsonProperty
	@Transient
	private Integer p1PriorityCount;
	
	
	@JsonProperty
	@Transient
	private Integer p2PriorityCount;
	
	
	@JsonProperty
	@Transient
	private Integer p3PriorityCount;
	
	
	@JsonProperty
	@Transient
	private Integer p4PriorityCount;
	
	@JsonProperty
	@Transient
	private String userFileUrl;
	
	@JsonProperty
	@Transient
	private String userName;
	
	@JsonProperty
	@Transient
	private String actionTimeStr;
	
	@Transient
    @JsonProperty
    private String taskTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;
	
	@JsonProperty
	@Transient
	private Long taskStausId;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
	public String getProjectTasksUUID() {
		return projectTasksUUID;
	}

	public void setProjectTasksUUID(String projectTasksUUID) {
		this.projectTasksUUID = projectTasksUUID;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBillableType() {
		return billableType;
	}

	public void setBillableType(String billableType) {
		this.billableType = billableType;
	}


	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}

	public String getEstimateTime() {
		return estimateTime;
	}

	public void setEstimateTime(String estimateTime) {
		this.estimateTime = estimateTime;
	}

	public LookUpData getTaskType() {
		return taskType;
	}

	public void setTaskType(LookUpData taskType) {
		this.taskType = taskType;
	}

	public LookUpData getPriority() {
		return priority;
	}

	public void setPriority(LookUpData priority) {
		this.priority = priority;
	}

	public LookUpData getSeverity() {
		return severity;
	}

	public void setSeverity(LookUpData severity) {
		this.severity = severity;
	}

	public LookUpData getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(LookUpData taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getBusinessCurrency() {
		return businessCurrency;
	}

	public void setBusinessCurrency(String businessCurrency) {
		this.businessCurrency = businessCurrency;
	}

	public String getBusinessValue() {
		return businessValue;
	}

	public void setBusinessValue(String businessValue) {
		this.businessValue = businessValue;
	}

	public User getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(User ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public ProjectTasks getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(ProjectTasks parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Set<ProjectAssignTask> getProjectAssignTask() {
		return projectAssignTask;
	}

	public void setProjectAssignTask(Set<ProjectAssignTask> projectAssignTask) {
		this.projectAssignTask = projectAssignTask;
	}

	public Set<ProjectTaskDocuments> getProjectTaskDocuments() {
		return projectTaskDocuments;
	}

	public void setProjectTaskDocuments(Set<ProjectTaskDocuments> projectTaskDocuments) {
		this.projectTaskDocuments = projectTaskDocuments;
	}
	

	public String getTaskPriotiry() {
		String  taskPriotiry = null;
		if (priority != null) {
			taskPriotiry = priority.getName();
		}
		return taskPriotiry;
	}

	public void setTaskPriotiry(String taskPriotiry) {
		this.taskPriotiry = taskPriotiry;
	}

	public String getTaskStaus() {
		String  taskStaus = null;
		if(taskStatus != null) {
			taskStaus = taskStatus.getName();
		}
		return taskStaus;
	}

	public void setTaskStaus(String taskStaus) {
		this.taskStaus = taskStaus;
	}

	public String getTaskTypes() {
		String taskTypes = null;
		if(taskType != null) {
			taskTypes = taskType.getName();
		}else {
			taskTypes = "--";
		}
		return taskTypes;
	}

	public void setTaskTypes(String taskTypes) {
		this.taskTypes = taskTypes;
	}

	public List<String> getTaskAssignedUsers() {
		List<String> usersList = new ArrayList<>();
		if(projectAssignTask != null && !projectAssignTask.isEmpty()) {
			for(ProjectAssignTask assignedTask: projectAssignTask) {
				if(assignedTask.getProjectMemberId() != null && 
						assignedTask.getProjectMemberId().getProjectMemberCandidteId() != null && 
						assignedTask.getProjectMemberId().getProjectMemberCandidteId() != null) {
					usersList.add(assignedTask.getProjectMemberId().getProjectMemberCandidteId().getFullName());
				}
			}
		}
		if(usersList != null && !usersList.isEmpty()) {
			taskAssignedUsers = usersList;
		}
		return taskAssignedUsers;
	}

	public void setTaskAssignedUsers(List<String> taskAssignedUsers) {
		this.taskAssignedUsers = taskAssignedUsers;
	}

	public String getCreatedDateStr() {
		if(createdDate != null) {
			SimpleDateFormat dateFormat = projectId.getOrgLocationsId().getLocFullFormDateFormatter();
			createdDateStr = dateFormat.format(createdDate);	
		}
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public Integer getNewCount() {
		return newCount;
	}

	public Integer getInProgressCount() {
		return inProgressCount;
	}

	public Integer getOnHoldCount() {
		return onHoldCount;
	}

	public Integer getDoneCount() {
		return doneCount;
	}

	public void setNewCount(Integer newCount) {
		this.newCount = newCount;
	}

	public void setInProgressCount(Integer inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public void setOnHoldCount(Integer onHoldCount) {
		this.onHoldCount = onHoldCount;
	}

	public void setDoneCount(Integer doneCount) {
		this.doneCount = doneCount;
	}

	public Integer getP1PriorityCount() {
		return p1PriorityCount;
	}

	public Integer getP2PriorityCount() {
		return p2PriorityCount;
	}

	public Integer getP3PriorityCount() {
		return p3PriorityCount;
	}

	public Integer getP4PriorityCount() {
		return p4PriorityCount;
	}

	public void setP1PriorityCount(Integer p1PriorityCount) {
		this.p1PriorityCount = p1PriorityCount;
	}

	public void setP2PriorityCount(Integer p2PriorityCount) {
		this.p2PriorityCount = p2PriorityCount;
	}

	public void setP3PriorityCount(Integer p3PriorityCount) {
		this.p3PriorityCount = p3PriorityCount;
	}

	public void setP4PriorityCount(Integer p4PriorityCount) {
		this.p4PriorityCount = p4PriorityCount;
	}

	public List<TasksGraphBean> getTasksGraphBean() {
		return tasksGraphBean;
	}

	public void setTasksGraphBean(List<TasksGraphBean> tasksGraphBean) {
		this.tasksGraphBean = tasksGraphBean;
	}

	public String getUserFileUrl() {
		return userFileUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserFileUrl(String userFileUrl) {
		this.userFileUrl = userFileUrl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActionTimeStr() {
		return actionTimeStr;
	}

	public void setActionTimeStr(String actionTimeStr) {
		this.actionTimeStr = actionTimeStr;
	}

	public List<ProjectTaskActivity> getProjectTaskActivity() {
		return projectTaskActivity;
	}

	public void setProjectTaskActivity(List<ProjectTaskActivity> projectTaskActivity) {
		this.projectTaskActivity = projectTaskActivity;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public Holiday getHoliday() {
		return holiday;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

	public String getTaskSeverity() {
		String taskSeverity = null;
		if (severity != null) {
			taskSeverity = severity.getName();
		}
		return taskSeverity;
	}

	public String getTaskOwnerName() {
		String taskOwnerName = null;
		if(ownerUserId != null) {
			taskOwnerName = ownerUserId.getFullName();
		}
		return taskOwnerName;
	}

	public void setTaskSeverity(String taskSeverity) {
		this.taskSeverity = taskSeverity;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getTaskTags() {
		return taskTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setTaskTags(String taskTags) {
		this.taskTags = taskTags;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public List<String> getAssignedTaskUsers() {
		return assignedTaskUsers;
	}

	public void setAssignedTaskUsers(List<String> assignedTaskUsers) {
		this.assignedTaskUsers = assignedTaskUsers;
	}

	@SuppressWarnings("unused")
	public Long getTaskStausId() {
		Long  taskStausId = null;
		if(taskStatus != null) {
			taskStausId = taskStatus.getId();
		}
		return taskStausId;
	}

	public void setTaskStausId(Long taskStausId) {
		this.taskStausId = taskStausId;
	}
	
	
}
