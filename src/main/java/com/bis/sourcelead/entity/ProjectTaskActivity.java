package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_TASK_ACTIVITY")
public class ProjectTaskActivity implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
	@JoinColumn(name = "PROJECT_TASK_ID", nullable = false)
	private ProjectTasks projectTaskId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projecId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_USER_ID", nullable = false)
   	private User userId;
    
	@Column(name = "CREATE_USER_NAME")
   	private String createdUserName;
	
	@Column(name = "ACTIVITY")
    @Type(type="text")
	private String activity;
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRIORITY")	
	private LookUpData priority;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TASK_STATUS")	
	private LookUpData taskStatus;
	
	@Column(name = "ACTIVITY_DESCRIPTION")
	@Type(type="text")
	private String activityDescription;
	 
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @JsonProperty
	@Transient
	private String actionTimeStr;
    
    @JsonProperty
    @Transient
	private String createdStr;
    
    @JsonProperty
    @Transient
   	private String activityTimezoneStr;
    
    @JsonProperty
	@Transient
	private Long loginUserId;
    
    @JsonProperty
	@Transient
	private String fileUrl;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectTasks getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(ProjectTasks projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public Projects getProjecId() {
		return projecId;
	}

	public void setProjecId(Projects projecId) {
		this.projecId = projecId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getActionTimeStr() {
		return actionTimeStr;
	}

	public void setActionTimeStr(String actionTimeStr) {
		this.actionTimeStr = actionTimeStr;
	}

	public String getCreatedStr() {
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	 
	public String getActivityTimezoneStr() {
		return activityTimezoneStr;
	}

	public void setActivityTimezoneStr(String activityTimezoneStr) {
		this.activityTimezoneStr = activityTimezoneStr;
	}
	
	public Long getLoginUserId() {
		Long loginUserId = null;
		if(userId != null) {
			loginUserId = userId.getId();
		}
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public LookUpData getPriority() {
		return priority;
	}

	public LookUpData getTaskStatus() {
		return taskStatus;
	}

	public void setPriority(LookUpData priority) {
		this.priority = priority;
	}

	public void setTaskStatus(LookUpData taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
