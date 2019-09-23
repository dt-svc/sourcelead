package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class StateTaxDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	@Required(desc = "Financial Year")
	private String financialYear;

	@Required(desc = "Tax Type", isPositiveNumber = true)
	private long taxTypeId;
	private String taxType;

	@Required(desc = "State", isPositiveNumber = true)
	private long stateId;
	private String stateName;

	private boolean active;

	private String statetaxuuid;

	@Required(desc = "Professional Tax List")
	private List<ProfessionalTaxDTO> professionalTaxList;

	private String removeIds;

	public String getRemoveIds() {
		return removeIds;
	}

	public void setRemoveIds(String removeIds) {
		this.removeIds = removeIds;
	}

	public List<ProfessionalTaxDTO> getProfessionalTaxList() {
		return professionalTaxList;
	}

	public void setProfessionalTaxList(List<ProfessionalTaxDTO> professionalTaxList) {
		this.professionalTaxList = professionalTaxList;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public String getStatetaxuuid() {
		return statetaxuuid;
	}

	public void setStatetaxuuid(String statetaxuuid) {
		this.statetaxuuid = statetaxuuid;
	}

}
