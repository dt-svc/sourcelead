package com.bis.sourcelead.dto;

import java.io.Serializable;

public class OrgLicenseSubscriptionDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
	private long orgLinceseSubscriptionId;
	
	private String registeredModules;
	
	private Double invoiceAmount;
	
	private Long orgLocationId;

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
}
