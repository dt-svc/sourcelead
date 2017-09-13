package com.bis.sourcelead.dto;

public class ContractReqDocsDTO {
	private String docId;
	private String documentName;
	private String docFileName;
	private String description;
	
	public String getDocId()
	{
		return docId;
	}
	
	public void setDocId(String docId)
	{
		this.docId = docId;
	}
	
	public String getDocumentName()
	{
		return documentName;
	}
	
	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}
	
	public String getDocFileName()
	{
		return docFileName;
	}

	public void setDocFileName(String docFileName)
	{
		this.docFileName = docFileName;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
}
