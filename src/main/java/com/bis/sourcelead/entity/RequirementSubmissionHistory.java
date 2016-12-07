package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "REQUIREMENT_SUBMISSION_HISTORY", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementSubmissionHistory")
public class RequirementSubmissionHistory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@Column(name = "FROM_STAGE")
	private String fromStage;
	
	@Column(name = "TO_STAGE")
	private String toStage;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public String getFromStage() {
		return fromStage;
	}

	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}

	public String getToStage() {
		return toStage;
	}

	public void setToStage(String toStage) {
		this.toStage = toStage;
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
	
	
}
