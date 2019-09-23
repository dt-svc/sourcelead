package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class FederalAndStateTaxDTO {

	@Required(desc = "fedralEIN")
	private String fedralEIN;
	
	@Required(desc="depositeSchedule")
	private String depositeSchedule;
	
	@Required(desc="fillingForm")
	private String fillingForm;
	
	private String unemploymentTax;
	
	private List<StateTaxesList> stateTaxesList;
	
	private List<StateTaxesList> savedStateTaxesList;
	
	private String fillerType;
	
	private String federalDeposit;
	
	private String taxDepositSchedule;
	
	
	
	public String getFedralEIN() {
		return fedralEIN;
	}

	public void setFedralEIN(String fedralEIN) {
		this.fedralEIN = fedralEIN;
	}

	public String getDepositeSchedule() {
		return depositeSchedule;
	}

	public void setDepositeSchedule(String depositeSchedule) {
		this.depositeSchedule = depositeSchedule;
	}

	public String getFillingForm() {
		return fillingForm;
	}

	public void setFillingForm(String fillingForm) {
		this.fillingForm = fillingForm;
	}

	public String getUnemploymentTax() {
		return unemploymentTax;
	}

	public void setUnemploymentTax(String unemploymentTax) {
		this.unemploymentTax = unemploymentTax;
	}

	public List<StateTaxesList> getStateTaxesList() {
		return stateTaxesList;
	}

	public void setStateTaxesList(List<StateTaxesList> stateTaxesList) {
		this.stateTaxesList = stateTaxesList;
	}

	public List<StateTaxesList> getSavedStateTaxesList() {
		return savedStateTaxesList;
	}

	public void setSavedStateTaxesList(List<StateTaxesList> savedStateTaxesList) {
		this.savedStateTaxesList = savedStateTaxesList;
	}

	public String getFillerType() {
		return fillerType;
	}

	public String getFederalDeposit() {
		return federalDeposit;
	}

	public void setFillerType(String fillerType) {
		this.fillerType = fillerType;
	}

	public void setFederalDeposit(String federalDeposit) {
		this.federalDeposit = federalDeposit;
	}

	public String getTaxDepositSchedule() {
		return taxDepositSchedule;
	}

	public void setTaxDepositSchedule(String taxDepositSchedule) {
		this.taxDepositSchedule = taxDepositSchedule;
	}
}
