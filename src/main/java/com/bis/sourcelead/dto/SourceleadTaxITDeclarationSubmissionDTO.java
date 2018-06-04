package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SourceleadTaxITDeclarationSubmissionDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String uuid;
	private String financialYear;
	private long userId;
	private String userName;
	private long taxFormId;
	private int status;
	private String statusText;
	private long taxSectionId;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;

	private List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList;

	private List<SourceleadTaxITDeclarationSubmissionSectionDTO> submittedSectionList;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		String text = "";
		switch (status) {
		case 0:
			text = "SUBMITTED";
			break;
		case 1:
			text = "APPROVED";
			break;
		case -1:
			text = "REJECTED";
			break;
		default:
			break;
		}
		return text;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
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

	public List<SourceleadTaxITDeclarationSubmissionItemDTO> getSubmissionItemList() {
		return submissionItemList;
	}

	public void setSubmissionItemList(List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList) {
		this.submissionItemList = submissionItemList;
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

	public long getTaxFormId() {
		return taxFormId;
	}

	public void setTaxFormId(long taxFormId) {
		this.taxFormId = taxFormId;
	}

	public List<SourceleadTaxITDeclarationSubmissionSectionDTO> getSubmittedSectionList() {
		return submittedSectionList;
	}

	public void setSubmittedSectionList(List<SourceleadTaxITDeclarationSubmissionSectionDTO> submittedSectionList) {
		this.submittedSectionList = submittedSectionList;
	}

}
