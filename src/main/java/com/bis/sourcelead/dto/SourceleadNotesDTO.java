package com.bis.sourcelead.dto;

public class SourceleadNotesDTO {
	
	private String noteType;
	
	private String orgRelationId;
	
	private String clientLocationId;
	
	private String categoryType;
	
	private String noteDescription;
	
	private String invoiceUUID;
	
	private String requirementId;
	
	private String contractUUID;
	
	private String allowAll;
	
	private String vendorcontact;

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getClientLocationId() {
		return clientLocationId;
	}

	public void setClientLocationId(String clientLocationId) {
		this.clientLocationId = clientLocationId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getNoteDescription() {
		return noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public String getAllowAll() {
		return allowAll;
	}

	public void setAllowAll(String allowAll) {
		this.allowAll = allowAll;
	}

	public String getInvoiceUUID() {
		return invoiceUUID;
	}

	public void setInvoiceUUID(String invoiceUUID) {
		this.invoiceUUID = invoiceUUID;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getContractUUID() {
		return contractUUID;
	}

	public void setContractUUID(String contractUUID) {
		this.contractUUID = contractUUID;
	}

	public String getVendorcontact() {
		return vendorcontact;
	}

	public void setVendorcontact(String vendorcontact) {
		this.vendorcontact = vendorcontact;
	}
	
	

}
