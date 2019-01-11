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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_TASK_COMMENTS")
public class ProjectTaskComments implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_TASK_ID", nullable = false)
	private ProjectTasks projectTaskId;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_USER_ID", nullable = false)
   	private User userId;
    
	@Column(name = "CREATE_USER_NAME")
   	private String createdUserName;
	
	@Column(name = "COMMENT")
	@Type(type="text")
	private String comment;
	 
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @JsonProperty
	@Transient
	private Long loginUserId;
    
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
