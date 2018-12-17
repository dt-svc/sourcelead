package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class SourceleadNotesDTO {
	
	private String noteType;
	
	private String orgRelationId;
	
	private String assetId;
	
	private String clientLocationId;
	
	private String categoryType;
	
	@Required(desc = "noteDescription")
	@CleanHtml(escape=false)
	private String noteDescription;
	
	private String invoiceUUID;
	
	private String requirementId;
	
	private String contractUUID;
	
	private String allowAll;
	
	private String vendorcontact;
	
	private String immigrationId;
	
	private String candidateId;

	public String getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(String immigrationId) {
		this.immigrationId = immigrationId;
	}

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

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	
	

}
