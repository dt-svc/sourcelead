package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ExpensesCategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String expensesName;
	
	private String amountType;
	
	private float allowedAmount;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
	

	
}
