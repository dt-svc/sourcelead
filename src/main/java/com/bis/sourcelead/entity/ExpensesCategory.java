package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "EXPENSE_CATEGORY")
public class ExpensesCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "NAME")
	private String expensesName;
	
	@Column(name = "AMOUNT_TYPE")
	private String amountType;
	
	@Column(name = "ALLOWED_AMOUNT")
	private float allowedAmount;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "IS_ATTACHEMT_REQUIRED")
	private String isAttachemtRequired;
	
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
	
	
	@Transient
	private List<LookUpData> expenseCategoryList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getExpensesName() {
		return expensesName;
	}

	public void setExpensesName(String expensesName) {
		this.expensesName = expensesName;
	}

	
	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public float getAllowedAmount() {
		return allowedAmount;
	}

	public void setAllowedAmount(float allowedAmount) {
		this.allowedAmount = allowedAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<LookUpData> getExpenseCategoryList() {
		return expenseCategoryList;
	}

	public void setExpenseCategoryList(List<LookUpData> expenseCategoryList) {
		this.expenseCategoryList = expenseCategoryList;
	}

	public String getIsAttachemtRequired() {
		return isAttachemtRequired;
	}

	public void setIsAttachemtRequired(String isAttachemtRequired) {
		this.isAttachemtRequired = isAttachemtRequired;
	}
	
}
