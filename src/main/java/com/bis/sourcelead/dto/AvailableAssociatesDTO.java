package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

	
	public class AvailableAssociatesDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String associates;
	
	private String projecs;
	
    private Date fromDate;
	
	private Date toDate;
	
	private List<Long> userIds;

	public String getAssociates() {
		return associates;
	}

	public void setAssociates(String associates) {
		this.associates = associates;
	}

	public String getProjecs() {
		return projecs;
	}

	public void setProjecs(String projecs) {
		this.projecs = projecs;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	
	
	
	
	
}
