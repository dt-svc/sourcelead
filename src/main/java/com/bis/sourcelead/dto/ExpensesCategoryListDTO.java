package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ExpensesCategoryListDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String categoryName;
	
	private String amountType;
	
	private float allowedAmount;
	
	private String description;
	
	private String status;
	
	private String isAttachemtRequired;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getIsAttachemtRequired() {
		return isAttachemtRequired;
	}

	public void setIsAttachemtRequired(String isAttachemtRequired) {
		this.isAttachemtRequired = isAttachemtRequired;
	}


	
}
