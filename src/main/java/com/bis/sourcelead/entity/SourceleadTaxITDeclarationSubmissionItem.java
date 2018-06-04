package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_IT_DECLARATION_SUBMISSION_ITEM")
public class SourceleadTaxITDeclarationSubmissionItem implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "MAX_LIMIT")
	private double maxLimit;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_IT_DECLARATION_SUBMISSION_ID", nullable = false)
	private SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_IT_DECLARATION_ID", nullable = false)
	private SourceleadTaxITDeclaration sourceleadTaxITDeclaration;

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

	public SourceleadTaxITDeclarationSubmission getSourceleadTaxITDeclarationSubmission() {
		return sourceleadTaxITDeclarationSubmission;
	}

	public void setSourceleadTaxITDeclarationSubmission(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission) {
		this.sourceleadTaxITDeclarationSubmission = sourceleadTaxITDeclarationSubmission;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SourceleadTaxITDeclaration getSourceleadTaxITDeclaration() {
		return sourceleadTaxITDeclaration;
	}

	public void setSourceleadTaxITDeclaration(SourceleadTaxITDeclaration sourceleadTaxITDeclaration) {
		this.sourceleadTaxITDeclaration = sourceleadTaxITDeclaration;
	}

}
