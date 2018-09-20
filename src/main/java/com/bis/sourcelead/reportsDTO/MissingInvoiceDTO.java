package com.bis.sourcelead.reportsDTO;

import com.bis.sourcelead.dto.SearchDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class MissingInvoiceDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String invoiceReferId;
	private String billReferId;
	public String getGraphDateType() {
		return graphDateType;
	}

	public void setGraphDateType(String graphDateType) {
		this.graphDateType = graphDateType;
	}

	private String orgName;
	private String projectName;
	private String invoiceDate;
	private String billDate;
	private String totalAmount;
	private String invoiceType;
	private String filterType;
	private String orgIds;
	private String fromDate;
	private String toDate;
	private String frequencyDate;
	private double billAmount;
	private double invoiceAmount;
	private double totalCalAmount;
	private String candidateName;
	private String contractType;
	private String graphDateType;

	public String getInvoiceReferId() {
		return invoiceReferId;
	}

	public void setInvoiceReferId(String invoiceReferId) {
		this.invoiceReferId = invoiceReferId;
	}

	public String getBillReferId() {
		return billReferId;
	}


	public double getTotalCalAmount() {
		return totalCalAmount;
	}

	public void setTotalCalAmount(double totalCalAmount) {
		this.totalCalAmount = totalCalAmount;
	}

	public void setBillReferId(String billReferId) {
		this.billReferId = billReferId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFrequencyDate() {
		return frequencyDate;
	}

	public void setFrequencyDate(String frequencyDate) {
		this.frequencyDate = frequencyDate;
	}

	

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}




}
