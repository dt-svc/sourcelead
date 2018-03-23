package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class OrgLicenseSubscriptionDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
	private long orgLinceseSubscriptionId;
	
	private String registeredModules;
	
	private Double invoiceAmount;
	
	private Long orgLocationId;
	
	private String currency;
	
	private String country;
	
	private String fromDateStr;
	
	private String toDateStr;
	
	private List<String> modulesList;
	
	private String subscriptionType;
	
	private String storageType;

	public long getOrgLinceseSubscriptionId() {
		return orgLinceseSubscriptionId;
	}

	public void setOrgLinceseSubscriptionId(long orgLinceseSubscriptionId) {
		this.orgLinceseSubscriptionId = orgLinceseSubscriptionId;
	}

	public String getRegisteredModules() {
		return registeredModules;
	}

	public void setRegisteredModules(String registeredModules) {
		this.registeredModules = registeredModules;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public List<String> getModulesList() {
		return modulesList;
	}

	public void setModulesList(List<String> modulesList) {
		this.modulesList = modulesList;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
}
