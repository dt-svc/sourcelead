package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class PayrollLoanAndAdvancesDTO implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
	 @Required(desc="type")
	 private String type;
	 
	 @Required(desc="name")
	 private String name;
	 
	 @Required (desc = "minAmount", dependsOn = "maxAmount", parentValueAny = true)
	 private String minAmount;
	 
	 @Required (desc = "maxAmount", dependsOn = "minAmount", parentValueAny = true)
	 private String maxAmount;
	 
	 private String interestRate;
	 
	 private String payHeadId;
	 
	 private String applicableFor;
	 
	 @Required (desc = "experienceYears", dependsOn = "applicableFor", parentValue = "Experienced")
	 private String experienceYears;
	 
	 @Required (desc = "experienceMonths", dependsOn = "applicableFor", parentValue = "Experienced")
	 private String experienceMonths;
	 
	 private String description;
	 
	 private String uuid;
	 
	 private int start;
		
	 private int length;
		
	 private String searchValue;
		
	 private Map<String, String> search;
		
	 private List<Map<String, String>> order;
		
	 private String sortColumn;
		
	 private String sortOrder;
	 
	 private long id;

	 
	 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
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

	public String getPayHeadId() {
		return payHeadId;
	}

	public void setPayHeadId(String payHeadId) {
		this.payHeadId = payHeadId;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
