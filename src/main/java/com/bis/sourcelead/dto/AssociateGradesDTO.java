package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class AssociateGradesDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	@Required(desc = "gradeName")
	private String gradeName;
	
	private String designation;
	
	private String minSalary;
	
	private String minSalaryRateType;
	
	private String minSalaryRatePer;
	
	private String midSalary;
	
	private String midSalaryRateType;
	
	private String midSalaryRatePer;
	
	private String maxSalary;
	
	private String maxSalaryRateType;
	
	private String maxSalaryRatePer;
	
	private String uuid;
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(String minSalary) {
		this.minSalary = minSalary;
	}

	public String getMinSalaryRateType() {
		return minSalaryRateType;
	}

	public void setMinSalaryRateType(String minSalaryRateType) {
		this.minSalaryRateType = minSalaryRateType;
	}

	public String getMinSalaryRatePer() {
		return minSalaryRatePer;
	}

	public void setMinSalaryRatePer(String minSalaryRatePer) {
		this.minSalaryRatePer = minSalaryRatePer;
	}

	public String getMidSalary() {
		return midSalary;
	}

	public void setMidSalary(String midSalary) {
		this.midSalary = midSalary;
	}

	public String getMidSalaryRateType() {
		return midSalaryRateType;
	}

	public void setMidSalaryRateType(String midSalaryRateType) {
		this.midSalaryRateType = midSalaryRateType;
	}

	public String getMidSalaryRatePer() {
		return midSalaryRatePer;
	}

	public void setMidSalaryRatePer(String midSalaryRatePer) {
		this.midSalaryRatePer = midSalaryRatePer;
	}

	public String getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(String maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getMaxSalaryRateType() {
		return maxSalaryRateType;
	}

	public void setMaxSalaryRateType(String maxSalaryRateType) {
		this.maxSalaryRateType = maxSalaryRateType;
	}

	public String getMaxSalaryRatePer() {
		return maxSalaryRatePer;
	}

	public void setMaxSalaryRatePer(String maxSalaryRatePer) {
		this.maxSalaryRatePer = maxSalaryRatePer;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
