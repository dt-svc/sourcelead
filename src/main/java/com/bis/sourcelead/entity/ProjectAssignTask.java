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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "PROJECT_ASSIGN_TASK")
public class ProjectAssignTask implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
		 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_TASK_ID", nullable=false )	
	private ProjectTasks projectTaskId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_MEMBER_ID", nullable=false )	
	private ProjectMembers projectMemberId;
 
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

	@JsonProperty
	@Transient
	private String assignedUserName;
	
	@JsonProperty
	@Transient
	private String projctName;
	
	@JsonProperty
	@Transient
	private String taskName;

	@JsonProperty
	@Transient
	private String priority;
	
	@JsonProperty
	@Transient
	private String colorPic;
	
	@JsonProperty
	@Transient
	private Integer percentage;
	
	@JsonProperty
	@Transient
	private String dueDate;
	
	@JsonProperty
	@Transient
	private String taskStatus;
	
	@JsonProperty
	@Transient
	private String assignedUserUrl;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public ProjectTasks getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(ProjectTasks projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
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

	public String getAssignedUserName() {
		return assignedUserName;
	}

	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	public String getProjctName() {
		return projctName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getPriority() {
		return priority;
	}

	public String getColorPic() {
		return colorPic;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setProjctName(String projctName) {
		this.projctName = projctName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setColorPic(String colorPic) {
		this.colorPic = colorPic;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getAssignedUserUrl() {
		return assignedUserUrl;
	}

	public void setAssignedUserUrl(String assignedUserUrl) {
		this.assignedUserUrl = assignedUserUrl;
	}
}
