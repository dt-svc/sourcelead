package com.bis.sourcelead.dto;

import java.io.Serializable;

public class StateTaxDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private String financialYear;

	private long taxTypeId;
	private String taxType;

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
	public long getTaxTypeId() {
		return taxTypeId;
	}
	public void setTaxTypeId(long taxTypeId) {
		this.taxTypeId = taxTypeId;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private long stateId;
	private String stateName;
}
