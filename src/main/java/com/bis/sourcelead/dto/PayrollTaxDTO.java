package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class PayrollTaxDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long orgLocationId;
	private String employeeESIContribution;
	private String employerESIContribution;
	private String employeePFContribution;
	private String employerPFContribution;
	private String edli;
	private String pension;
	private String pfAdminCharges;
	private String edliAdminCharges;
	private String pfCeilValue;
	// private String itDeclarationFromDate;
	// private String itDeclarationToDate;
	private String financialStartMonth;
	private String financialEndMonth;
	private String createdDate;
	private long createdByUserId;
	private String updatedDate;
	private long updatedByUserId;
	private long stateTaxId;
	private String financialYear;
	private List<ProfessionalTaxDTO> ptList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getEmployeeESIContribution() {
		return employeeESIContribution;
	}

	public void setEmployeeESIContribution(String employeeESIContribution) {
		this.employeeESIContribution = employeeESIContribution;
	}

	public String getEmployerESIContribution() {
		return employerESIContribution;
	}

	public void setEmployerESIContribution(String employerESIContribution) {
		this.employerESIContribution = employerESIContribution;
	}

	public String getEmployeePFContribution() {
		return employeePFContribution;
	}

	public void setEmployeePFContribution(String employeePFContribution) {
		this.employeePFContribution = employeePFContribution;
	}

	public String getEmployerPFContribution() {
		return employerPFContribution;
	}

	public void setEmployerPFContribution(String employerPFContribution) {
		this.employerPFContribution = employerPFContribution;
	}

	public String getEdli() {
		return edli;
	}

	public void setEdli(String edli) {
		this.edli = edli;
	}

	public String getPension() {
		return pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}

	public String getPfAdminCharges() {
		return pfAdminCharges;
	}

	public void setPfAdminCharges(String pfAdminCharges) {
		this.pfAdminCharges = pfAdminCharges;
	}

	public String getEdliAdminCharges() {
		return edliAdminCharges;
	}

	public void setEdliAdminCharges(String edliAdminCharges) {
		this.edliAdminCharges = edliAdminCharges;
	}

	public String getPfCeilValue() {
		return pfCeilValue;
	}

	public void setPfCeilValue(String pfCeilValue) {
		this.pfCeilValue = pfCeilValue;
	}

	/*
	 * public String getItDeclarationFromDate() { return itDeclarationFromDate; }
	 * 
	 * public void setItDeclarationFromDate(String itDeclarationFromDate) {
	 * this.itDeclarationFromDate = itDeclarationFromDate; }
	 * 
	 * public String getItDeclarationToDate() { return itDeclarationToDate; }
	 * 
	 * public void setItDeclarationToDate(String itDeclarationToDate) {
	 * this.itDeclarationToDate = itDeclarationToDate; }
	 */

	public String getFinancialStartMonth() {
		return financialStartMonth;
	}

	public void setFinancialStartMonth(String financialStartMonth) {
		this.financialStartMonth = financialStartMonth;
	}

	public String getFinancialEndMonth() {
		return financialEndMonth;
	}

	public void setFinancialEndMonth(String financialEndMonth) {
		this.financialEndMonth = financialEndMonth;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getUpdatedByUserId() {
		return updatedByUserId;
	}

	public void setUpdatedByUserId(long updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	public long getStateTaxId() {
		return stateTaxId;
	}

	public void setStateTaxId(long stateTaxId) {
		this.stateTaxId = stateTaxId;
	}

	public List<ProfessionalTaxDTO> getPtList() {
		return ptList;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public void setPtList(List<ProfessionalTaxDTO> ptList) {
		this.ptList = ptList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
