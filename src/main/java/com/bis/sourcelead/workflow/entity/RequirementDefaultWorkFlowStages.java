package com.bis.sourcelead.workflow.entity;

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

import com.bis.sourcelead.entity.User;

	
	//@Entity
	//@Table(name = "REQUIREMENT_DEFAULT_WORKFLOW_STAGES")
	public class RequirementDefaultWorkFlowStages implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "STAGE_NAME")
	private String stageName;
	
	@Column(name = "DESCRIPTION",  columnDefinition = "LONGTEXT default NULL")
	private String  description;
	
	@Column(name = "STAGE_SHORT_CODE")
	private String stageShortCode;
	
	@Column(name = "SEQ_ORDER")
	private Integer seqOrder;
	
	@Column(name = "IS_EDITABLE")
	private String editable;
	
	@Column(name = "STATUS")
	private Integer status;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStageShortCode() {
		return stageShortCode;
	}

	public void setStageShortCode(String stageShortCode) {
		this.stageShortCode = stageShortCode;
	}

	public Integer getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(Integer seqOrder) {
		this.seqOrder = seqOrder;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
