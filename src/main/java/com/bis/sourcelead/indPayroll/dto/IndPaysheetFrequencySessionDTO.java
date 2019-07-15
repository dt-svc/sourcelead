package com.bis.sourcelead.indPayroll.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IndPaysheetFrequencySessionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String paySheetFrequencyUUID;
	private List<INDPayrollDTO> indPayrollList;
	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}
	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}
	public List<INDPayrollDTO> getIndPayrollList() {
		return indPayrollList;
	}
	public void setIndPayrollList(List<INDPayrollDTO> indPayrollList) {
		this.indPayrollList = indPayrollList;
	}
	
	
}
