package com.bis.sourcelead.indPayroll.dto;

import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.Tds;

public class PreCheckINDPayrollDTO extends SearchDTO {

	/**
	 * @author Venkat Kesav
	 */
	private static final long serialVersionUID = 1L;

	private Boolean isPTConfigured;

	private Boolean isTDSConfigured;
	
	private StateTaxDTO stateTaxDTO;
	
	private List<Tds> tdsList;
	
	public Boolean getIsPTConfigured() {
		return isPTConfigured;
	}

	public void setIsPTConfigured(Boolean isPTConfigured) {
		this.isPTConfigured = isPTConfigured;
	}

	public Boolean getIsTDSConfigured() {
		return isTDSConfigured;
	}

	public void setIsTDSConfigured(Boolean isTDSConfigured) {
		this.isTDSConfigured = isTDSConfigured;
	}

	public StateTaxDTO getStateTaxDTO() {
		return stateTaxDTO;
	}

	public void setStateTaxDTO(StateTaxDTO stateTaxDTO) {
		this.stateTaxDTO = stateTaxDTO;
	}

	public List<Tds> getTdsList() {
		return tdsList;
	}

	public void setTdsList(List<Tds> tdsList) {
		this.tdsList = tdsList;
	}
}
