package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class PayrollBonusDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 @Required(desc="paymentDate")
	 private String paymentDate;
	 
	 @Required(desc="minPercentage")
	 private String minPercentage;
	 
	 @Required(desc="maxPercentage")
	 private String maxPercentage;
	 
	 private String paymentMonth;
	 
	 private String paymentYear;
	 
	 @Required (desc = "fromMonth", dependsOn = "toMonth", parentValueAny = true)
	 private String fromMonth;
	 
	 @Required (desc = "toMonth", dependsOn = "fromMonth", parentValueAny = true)
	 private String toMonth;
	 
	 private Double maxMonthSalary;
	 
	 private String paymentFrequency;
	 
	 private String description;
	 
	 private String uuid;
	 
	 

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getMinPercentage() {
		return minPercentage;
	}

	public void setMinPercentage(String minPercentage) {
		this.minPercentage = minPercentage;
	}

	public String getMaxPercentage() {
		return maxPercentage;
	}

	public void setMaxPercentage(String maxPercentage) {
		this.maxPercentage = maxPercentage;
	}

	public String getPaymentMonth() {
		return paymentMonth;
	}

	public void setPaymentMonth(String paymentMonth) {
		this.paymentMonth = paymentMonth;
	}

	public String getPaymentYear() {
		return paymentYear;
	}

	public void setPaymentYear(String paymentYear) {
		this.paymentYear = paymentYear;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public Double getMaxMonthSalary() {
		return maxMonthSalary;
	}

	public void setMaxMonthSalary(Double maxMonthSalary) {
		this.maxMonthSalary = maxMonthSalary;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
