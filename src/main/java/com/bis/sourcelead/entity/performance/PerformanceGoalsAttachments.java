package com.bis.sourcelead.entity.performance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERFORMANCE_GOALS_ATTACHMENTS")
public class PerformanceGoalsAttachments implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GOAL_ID", nullable=false )        
    private PerformanceGoals goalId;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "FILE_TYPE", columnDefinition = "TEXT default NULL")
	private String fileType;
	
	@Column(name = "FILE_URL", columnDefinition = "TEXT default NULL")
	private String fileUrl;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS")
	private Integer status;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerformanceGoals getGoalId() {
		return goalId;
	}

	public void setGoalId(PerformanceGoals goalId) {
		this.goalId = goalId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
