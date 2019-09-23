package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.annotations.Required;

public class AssociatePfEsiDetailsDTO {
	
	private Long id;
	
	private String eligibleForPf;
	
	@Required(desc="uanNumber", dependsOn = "eligibleForPf", parentValueAny = true)
	private String uanNumber;
	
	@Required(desc="pfNumber", dependsOn = "eligibleForPf", parentValueAny = true)
	private String pfNumber;
	
	@Required(desc="aadharNumber", dependsOn = "eligibleForPf", parentValueAny = true)
	private String aadharNumber;
	
	private String enroleDate;
	
	private String eligibleForEsi;
	
	private String esinumber;
	
	private String userId;
	
	private String orglocationId;
	
	private String associateUserId;
	
	private String pfesiId;
	
	private String nominee;
	
	private String reason;
	
	private String email;

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

	public String getNominee() {
		return nominee;
	}

	public void setNominee(String nominee) {
		this.nominee = nominee;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
