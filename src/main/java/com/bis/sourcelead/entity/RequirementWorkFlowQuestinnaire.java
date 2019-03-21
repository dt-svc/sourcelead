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

	
	@Entity
	@Table(name = "REQUIREMENT_WORKFLOW_QUESTIONNAIRE")
	public class RequirementWorkFlowQuestinnaire implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_WORKFLOW_ID", nullable = false)
	private RequirementWorkFlow requirementWorkFlowId;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String 	description;
	
	@Column(name = "CREATED_BY_USER_NAME")
	private String createdByUserName;
	
	@Column(name = "STATUS")
	private Byte status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementWorkFlow getRequirementWorkFlowId() {
		return requirementWorkFlowId;
	}

	public void setRequirementWorkFlowId(RequirementWorkFlow requirementWorkFlowId) {
		this.requirementWorkFlowId = requirementWorkFlowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
