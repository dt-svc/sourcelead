package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	 
	 @Required (desc = "maxSalary", dependsOn = "paymentFrequency", parentValueAny = true)
	 private String maxSalary;
	 
	 @Required (desc = "paymentFrequency", dependsOn = "maxSalary", parentValueAny = true)
	 private String paymentFrequency;
	 
	 private String description;
	 
	 private String uuid;
	 
	 private int start;
		
	 private int length;
		
	 private String searchValue;
		
	 private Map<String, String> search;
		
	 private List<Map<String, String>> order;
		
	 private String sortColumn;
		
	 private String sortOrder;
	 
	 

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

	public String getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(String maxSalary) {
		this.maxSalary = maxSalary;
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

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Map<String, String> getSearch() {
		return search;
	}

	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
