package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;

@Entity
@Table(name = "REQUIREMENT_APPROVER_HIERARCHY")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="requirementApproverHierarchy")
public class RequirementApproverHierarchy {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "LEVEL")
	private String level;
	
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )        
    private Requirement requirementId;
	
	@ManyToOne
    @JoinColumn(name="APPROVER_USER_ID", nullable=false )        
    private User approverUserId;
	
	@Column(name = "HIERARCHY_TYPE")
	@Enumerated(EnumType.STRING)
	private HIERARCHY_TYPE hierarchyType;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private REQUIREMENT_APPROVER_HIERARCHY_STATUS status;
	
	@Column(name = "REJECTED_REASON")
	private String rejectedReason;
	
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public User getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(User approverUserId) {
		this.approverUserId = approverUserId;
	}

	public HIERARCHY_TYPE getHierarchyType() {
		return hierarchyType;
	}

	public void setHierarchyType(HIERARCHY_TYPE hierarchyType) {
		this.hierarchyType = hierarchyType;
	}

	public REQUIREMENT_APPROVER_HIERARCHY_STATUS getStatus() {
		return status;
	}

	public void setStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS status) {
		this.status = status;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
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
