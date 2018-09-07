package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadTaxSubmissionMasterDTO extends SearchDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String financialYear;
	private long userId;
	private long itDeclarationSubmissionId;
	private long form16Id;
	private long form12BBId;
	private String formType;
	private String fillingDate;
	private int status;
	private String statusText;
	private String taxFormUUID;
	private String formUUID;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getItDeclarationSubmissionId() {
		return itDeclarationSubmissionId;
	}

	public void setItDeclarationSubmissionId(long itDeclarationSubmissionId) {
		this.itDeclarationSubmissionId = itDeclarationSubmissionId;
	}

	public String getFormType() {
		String text = "";
		if (itDeclarationSubmissionId > 0) {
			text = "IT Declaration";
		} else if (form16Id > 0) {
			text = "Form 16";
		} else if (form12BBId > 0) {
			text = "Form 12BB";
		}
		return text;
	}

	public String getFillingDate() {
		return fillingDate;
	}

	public void setFillingDate(String fillingDate) {
		this.fillingDate = fillingDate;
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

	public String getTaxFormUUID() {
		return taxFormUUID;
	}

	public void setTaxFormUUID(String taxFormUUID) {
		this.taxFormUUID = taxFormUUID;
	}

	public String getFormUUID() {
		return formUUID;
	}

	public void setFormUUID(String formUUID) {
		this.formUUID = formUUID;
	}

}
