package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class ProfessionalTaxDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long stateTaxId;
	private String taxType;
	@Required(desc = "From Amount", isPositiveNumber = true)
	private String amountFrom;
	@Required(desc = "To Amount", isPositiveNumber = true)
	private String amountTo;
	@Required(desc = "Amount", isPositiveNumber = true)
	private String amount;
	@Required(desc = "month")
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

	public String getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}

	public String getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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
