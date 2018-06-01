package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class SourceleadTaxITDeclarationItemSubmissionDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double maxLimit;
	private double amount;
	private String description;
	private int status;
	private long sourceleadTaxITDeclarationId;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getSourceleadTaxITDeclarationId() {
		return sourceleadTaxITDeclarationId;
	}

	public void setSourceleadTaxITDeclarationId(long sourceleadTaxITDeclarationId) {
		this.sourceleadTaxITDeclarationId = sourceleadTaxITDeclarationId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
