package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ProfessionalTaxDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long stateTaxId;
	private String taxType;
	private double amountFrom;
	private double amountTo;
	private double amount;
	private String monthApplicable;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStateTaxId() {
		return stateTaxId;
	}

	public void setStateTaxId(long stateTaxId) {
		this.stateTaxId = stateTaxId;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String stateTax) {
		this.taxType = stateTax;
	}

	public double getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}

	public double getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMonthApplicable() {
		return monthApplicable;
	}

	public void setMonthApplicable(String monthApplicable) {
		this.monthApplicable = monthApplicable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
