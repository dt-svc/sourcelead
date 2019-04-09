package com.bis.sourcelead.indPayroll.dto;

import com.bis.sourcelead.dto.SearchDTO;

public class PreCheckINDPayrollDTO extends SearchDTO {

	/**
	 * @author Venkat Kesav
	 */
	private static final long serialVersionUID = 1L;

	private Boolean isPTConfigured;

	private Boolean isTDSConfigured;
	

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
}
