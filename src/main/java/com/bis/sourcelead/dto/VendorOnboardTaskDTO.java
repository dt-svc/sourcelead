package com.bis.sourcelead.dto;

import java.io.Serializable;

public class VendorOnboardTaskDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vendorOnboardTaskId;

	public String getVendorOnboardTaskId() {
		return vendorOnboardTaskId;
	}

	public void setVendorOnboardTaskId(String vendorOnboardTaskId) {
		this.vendorOnboardTaskId = vendorOnboardTaskId;
	}
}
