

package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

public class CandidateDocumentsDTO {

	private String candidateId;
	
	private String staticPath;
	
	private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String name;
	
	private String documentOtherType;
	
	private String description;
	
	private String isConfidential;
	
	private String requestDocUUID;

	public String getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(String candidateId)
	{
		this.candidateId = candidateId;
	}

	public String getStaticPath()
	{
		return staticPath;
	}

	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo()
	{
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo)
	{
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo()
	{
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo)
	{
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getDocumentOtherType()
	{
		return documentOtherType;
	}

	public void setDocumentOtherType(String documentOtherType)
	{
		this.documentOtherType = documentOtherType;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIsConfidential()
	{
		return isConfidential;
	}

	public void setIsConfidential(String isConfidential)
	{
		this.isConfidential = isConfidential;
	}

	public String getRequestDocUUID()
	{
		return requestDocUUID;
	}

	public void setRequestDocUUID(String requestDocUUID)
	{
		this.requestDocUUID = requestDocUUID;
	}
	
	
}
