package com.bis.sourcelead.dto;

import java.util.List;

public class FederalAndStateTaxDTO {

	private String fedralEIN;
	
	private String depositeSchedule;
	
	private String fillingForm;
	
	private String unemploymentTax;
	
	private List<StateTaxesList> stateTaxesList;
	
	

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
	
	
}
