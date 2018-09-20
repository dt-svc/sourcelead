package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;

public class ContractDocumentsDTO {
	private String staticPath;
	private String docId;
	private String documentName;
	private String docFileName;
	private String candiDoc;
	
	@CleanHtml(escape=false)
	private String description;
	
	private String fileUrl;
	private List<String> docFile;
	private List<FileInfo> fileInfo;
	private List<ContractReqDocsDTO> docfiles;
	
	public String getStaticPath()
	{
		return staticPath;
	}

	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}

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

	public List<FileInfo> getFileInfo()
	{
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo)
	{
		this.fileInfo = fileInfo;
	}

	public List<String> getDocFile()
	{
		return docFile;
	}

	public void setDocFile(List<String> docFile)
	{
		this.docFile = docFile;
	}

	public List<ContractReqDocsDTO> getDocfiles()
	{
		return docfiles;
	}

	public void setDocfiles(List<ContractReqDocsDTO> docfiles)
	{
		this.docfiles = docfiles;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

}
