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

@Entity
@Table(name = "SOURCELEAD_TAX_SUBMISSION_MASTER")
public class SourceleadTaxSubmissionMaster implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;

	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// For now we are using IT Declaration submission.
	// In future, add form16,form12BB etc forms also
	@JoinColumn(name = "SOURCELEAD_TAX_IT_DECLARATION_SUBMISSION_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SourceleadTaxITDeclarationSubmission getSourceleadTaxITDeclarationSubmission() {
		return sourceleadTaxITDeclarationSubmission;
	}

	public void setSourceleadTaxITDeclarationSubmission(
			SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission) {
		this.sourceleadTaxITDeclarationSubmission = sourceleadTaxITDeclarationSubmission;
	}

}
