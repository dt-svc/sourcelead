package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TimesheetDashboardListDTO extends SearchDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String status;
	private String tdSpanLength;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTdSpanLength() {
		return tdSpanLength;
	}
	public void setTdSpanLength(String tdSpanLength) {
		this.tdSpanLength = tdSpanLength;
	}
	
	
}
