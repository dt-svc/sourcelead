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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "REQUIREMENT_INCENTIVE")
public class RequirementIncentive implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )        
    private Requirement requirementId;
	
	@Column(name = "JOINERS")
	private String joiners;
	
	@Column(name = "JOINERS_AMOUNT_TYPE")
	private String joinersAmountType;
	
	@Column(name = "JOINERS_AMOUNT")
	private String joinersAmount;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CREATED_BY", nullable=false )        
    private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="UPDATED_BY", nullable=false )        
    private User updatedBy;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public String getJoiners() {
		return joiners;
	}

	public void setJoiners(String joiners) {
		this.joiners = joiners;
	}

	public String getJoinersAmountType() {
		return joinersAmountType;
	}

	public void setJoinersAmountType(String joinersAmountType) {
		this.joinersAmountType = joinersAmountType;
	}

	public String getJoinersAmount() {
		return joinersAmount;
	}

	public void setJoinersAmount(String joinersAmount) {
		this.joinersAmount = joinersAmount;
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
