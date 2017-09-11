

package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

public class CandidateDocumentsDTO {

	private String candidateId;
	
	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String name;
	
	private String documentOtherType;
	
	private String description;
	
	private String isConfidential;
	
	private String requestDocUUID;
	
	private String docuemntMaxRowNumber;
	
	private List<RequestDocumentList> requestDocumentList;
	
	private String requestedCandidateId;
	
	private String checked;
	
	private String doctype;
	
	private String otherDocType;

	private String requestDocType;

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

	

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
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

	public String getDocuemntMaxRowNumber() {
		return docuemntMaxRowNumber;
	}

	public void setDocuemntMaxRowNumber(String docuemntMaxRowNumber) {
		this.docuemntMaxRowNumber = docuemntMaxRowNumber;
	}

	public String getRequestedCandidateId() {
		return requestedCandidateId;
	}

	public void setRequestedCandidateId(String requestedCandidateId) {
		this.requestedCandidateId = requestedCandidateId;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getOtherDocType() {
		return otherDocType;
	}

	public void setOtherDocType(String otherDocType) {
		this.otherDocType = otherDocType;
	}

	public List<RequestDocumentList> getRequestDocumentList() {
		return requestDocumentList;
	}

	public void setRequestDocumentList(List<RequestDocumentList> requestDocumentList) {
		this.requestDocumentList = requestDocumentList;
	}

	public String getRequestDocType() {
		return requestDocType;
	}

	public void setRequestDocType(String requestDocType) {
		this.requestDocType = requestDocType;
	}
	
}
