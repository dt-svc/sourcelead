package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadTaxITDeclarationSubmissionItemDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double maxLimit;
	private double amount;
	private String description;
	private long taxSectionId;

	private long sourceleadTaxITDeclarationSubmissionId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public long getSourceleadTaxITDeclarationSubmissionId() {
		return sourceleadTaxITDeclarationSubmissionId;
	}

	public void setSourceleadTaxITDeclarationSubmissionId(long sourceleadTaxITDeclarationSubmissionId) {
		this.sourceleadTaxITDeclarationSubmissionId = sourceleadTaxITDeclarationSubmissionId;
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

}
