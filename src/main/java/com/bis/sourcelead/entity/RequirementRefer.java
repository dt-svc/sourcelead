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
@Table(name = "REQUIREMENT_REFER", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementRefer")
public class RequirementRefer implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="REFER_FROM_USER_ID", nullable=false )        
    private User referFromUser;
	
	@ManyToOne
    @JoinColumn(name="REFER_TO_USER_ID", nullable=false )        
    private User referToUser;
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )        
    private Requirement requirementId;
	
	@ManyToOne
    @JoinColumn(name="REFER_ID", nullable=false )        
    private RequirementRefer referId;
	
	@Column(name = "REFER_FROM_EMAIL")
	private String referFromEmail;
	
	@Column(name = "REFER_TO_EMAIL")
	private String referToEmail;
	
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

	public User getReferFromUser() {
		return referFromUser;
	}

	public void setReferFromUser(User referFromUser) {
		this.referFromUser = referFromUser;
	}

	public User getReferToUser() {
		return referToUser;
	}

	public void setReferToUser(User referToUser) {
		this.referToUser = referToUser;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public RequirementRefer getReferId() {
		return referId;
	}

	public void setReferId(RequirementRefer referId) {
		this.referId = referId;
	}

	public String getReferFromEmail() {
		return referFromEmail;
	}

	public void setReferFromEmail(String referFromEmail) {
		this.referFromEmail = referFromEmail;
	}

	public String getReferToEmail() {
		return referToEmail;
	}

	public void setReferToEmail(String referToEmail) {
		this.referToEmail = referToEmail;
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
