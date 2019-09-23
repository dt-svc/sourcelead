package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadTaxITDeclarationItemDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String maxLimit;
	private String amount;
	private String description;
	private boolean active;
	private long taxSectionId;
	private String taxSectionName;
	private long taxFormId;
	private String taxFormName;

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

	public String getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public long getTaxFormId() {
		return taxFormId;
	}

	public void setTaxFormId(long taxFormId) {
		this.taxFormId = taxFormId;
	}

	public String getTaxFormName() {
		return taxFormName;
	}

	public void setTaxFormName(String taxFormName) {
		this.taxFormName = taxFormName;
	}

}
