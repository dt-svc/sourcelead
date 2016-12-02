package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;

public class BackOfficeDTO implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;

	private List<BoPaymentTypes> boPaymentTypesList;
	
	private List<Object> finalcialInstituesList;

	public List<BoPaymentTypes> getBoPaymentTypesList() {
		return boPaymentTypesList;
	}

	public void setBoPaymentTypesList(List<BoPaymentTypes> boPaymentTypesList) {
		this.boPaymentTypesList = boPaymentTypesList;
	}

	public List<Object> getFinalcialInstituesList() {
		return finalcialInstituesList;
	}

	public void setFinalcialInstituesList(List<Object> finalcialInstituesList) {
		this.finalcialInstituesList = finalcialInstituesList;
	}

	
    
}
