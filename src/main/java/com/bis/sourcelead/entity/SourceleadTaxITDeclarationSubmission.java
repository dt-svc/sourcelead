package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "SOURCELEAD_TAX_IT_DECLARATION_SUBMISSION")
public class SourceleadTaxITDeclarationSubmission implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "FINANCIAL_YEAR")
	private String financialYear;

	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", referencedColumnName = "ID")
	private OrgLocations orgLocations;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "SUBMISSION_ITEMS")
	private String submissionItems;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_FORM_ID", nullable = false)
	private SourceleadTaxForm sourceleadTaxForm;

	/*
	 * @OneToMany(mappedBy = "sourceleadTaxITDeclarationSubmission", fetch =
	 * FetchType.LAZY, cascade = CascadeType.ALL) private
	 * List<SourceleadTaxITDeclarationSubmissionItem> submissionItemList;
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSubmissionItems() {
		return submissionItems;
	}

	public void setSubmissionItems(String submissionItems) {
		this.submissionItems = submissionItems;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SourceleadTaxForm getSourceleadTaxForm() {
		return sourceleadTaxForm;
	}

	public void setSourceleadTaxForm(SourceleadTaxForm sourceleadTaxForm) {
		this.sourceleadTaxForm = sourceleadTaxForm;
	}

}
