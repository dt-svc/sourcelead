package com.bis.sourcelead.entity;

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
@Table(name = "REQUIREMENT_SUBMISSION_RATE_NEGOTIATION", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "RequirementRateSubmissionNegotiation")
public class RequirementRateSubmissionNegotiation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID", nullable=false )        
    private RequirementSubmission requirementsubmissionId;
	
	@Column(name = "CURRENTRATE")
	private float currentRate;
	
	@Column(name = "EXPECTEDRATE")
	private float expectedRate;
	
	@Column(name = "FINALIZEDRATE")
	private float finalizedRate;
	
	@Column(name = "STATUS")
	private String status;
	
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

	public RequirementSubmission getRequirementsubmissionId() {
		return requirementsubmissionId;
	}

	public void setRequirementsubmissionId(RequirementSubmission requirementsubmissionId) {
		this.requirementsubmissionId = requirementsubmissionId;
	}

	public float getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(float currentRate) {
		this.currentRate = currentRate;
	}

	public float getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(float expectedRate) {
		this.expectedRate = expectedRate;
	}

	public float getFinalizedRate() {
		return finalizedRate;
	}

	public void setFinalizedRate(float finalizedRate) {
		this.finalizedRate = finalizedRate;
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

	

	
}
