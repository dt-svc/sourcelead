package com.bis.sourcelead.dto;

public class ContractDocumentsDTO {
	private String documentName;
	private String candiDoc;
	private String description;
	private String fileUrl;
	
	public String getDocumentName()
	{
		return documentName;
	}
	
	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}
	
	public String getCandiDoc()
	{
		return candiDoc;
	}
	
	public void setCandiDoc(String candiDoc)
	{
		this.candiDoc = candiDoc;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
