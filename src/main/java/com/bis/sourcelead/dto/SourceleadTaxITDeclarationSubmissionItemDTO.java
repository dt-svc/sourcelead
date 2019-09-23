package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadTaxITDeclarationSubmissionItemDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	// private long id;
	private String name;
	private double maxLimit;
	private double amount;
	private String description;
	private String comment;
	private long taxSectionId;
	private String taxSectionName;

	// private long sourceleadTaxITDeclarationSubmissionId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(double maxLimit) {
		this.maxLimit = maxLimit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getTaxSectionId() {
		return taxSectionId;
	}

	public void setTaxSectionId(long taxSectionId) {
		this.taxSectionId = taxSectionId;
	}

	public String getTaxSectionName() {
		return taxSectionName;
	}

	public void setTaxSectionName(String taxSectionName) {
		this.taxSectionName = taxSectionName;
	}

}
