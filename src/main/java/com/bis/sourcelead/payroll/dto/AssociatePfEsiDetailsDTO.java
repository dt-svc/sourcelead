package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.annotations.Required;

public class AssociatePfEsiDetailsDTO {
	
	private Long id;
	
	private String eligibleForPf;
	
	@Required(desc="uanNumber", dependsOn = "eligibleForPf", parentValueAny = true)
	private String uanNumber;
	
	@Required(desc="pfNumber", dependsOn = "eligibleForPf", parentValueAny = true)
	private String pfNumber;
	
	
	private String enroleDate;
	
	private String eligibleForEsi;
	
	private String esinumber;
	
	private String userId;
	
	private String orglocationId;
	
	private String associateUserId;
	
	private String pfesiId;

	public Long getId() {
		return id;
	}

	public String getEligibleForPf() {
		return eligibleForPf;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public String getEnroleDate() {
		return enroleDate;
	}

	public String getEligibleForEsi() {
		return eligibleForEsi;
	}

	public String getEsinumber() {
		return esinumber;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrglocationId() {
		return orglocationId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEligibleForPf(String eligibleForPf) {
		this.eligibleForPf = eligibleForPf;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public void setEnroleDate(String enroleDate) {
		this.enroleDate = enroleDate;
	}

	public void setEligibleForEsi(String eligibleForEsi) {
		this.eligibleForEsi = eligibleForEsi;
	}

	public void setEsinumber(String esinumber) {
		this.esinumber = esinumber;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrglocationId(String orglocationId) {
		this.orglocationId = orglocationId;
	}

	public String getAssociateUserId() {
		return associateUserId;
	}

	public void setAssociateUserId(String associateUserId) {
		this.associateUserId = associateUserId;
	}

	public String getPfesiId() {
		return pfesiId;
	}

	public void setPfesiId(String pfesiId) {
		this.pfesiId = pfesiId;
	}
}
