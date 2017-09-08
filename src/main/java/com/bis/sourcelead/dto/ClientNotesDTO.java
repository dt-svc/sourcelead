package com.bis.sourcelead.dto;

public class ClientNotesDTO {
	
	private String noteType;
	
	private String orgRelationId;
	
	private String clientLocationId;
	
	private String categoryType;
	
	private String noteDescription;
	
	private String allowAll;

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
	
	

}
