package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.Date;

public class ProbationDTO extends CommonDTO {

	private String associateFirstName;
	
	private String associateLastName;
	
	private String departmentName;
	
	private String associateRole;
	
	private Date hireDate;
	
	private String probationPeriod;
	
	private Date confirmationDate;
	
	private BigInteger resourceManagerId;
	
	private int noOfDays;

	

	public String getAssociateFirstName() {
		return associateFirstName;
	}

	public void setAssociateFirstName(String associateFirstName) {
		this.associateFirstName = associateFirstName;
	}

	public String getAssociateLastName() {
		return associateLastName;
	}

	public void setAssociateLastName(String associateLastName) {
		this.associateLastName = associateLastName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAssociateRole() {
		return associateRole;
	}

	public void setAssociateRole(String associateRole) {
		this.associateRole = associateRole;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(String probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public BigInteger getResourceManagerId() {
		return resourceManagerId;
	}

	public void setResourceManagerId(BigInteger resourceManagerId) {
		this.resourceManagerId = resourceManagerId;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	
}
