

package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;

public class CandidateDocumentsDTO {

	private String candidateId;
	
	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String name;
	
	private String documentOtherType;
	
	@CleanHtml(escape=false)
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
	
	private String id;
	
	private String documentUUID;
	
	private String fileName;
	
	private String documentType;
	
	private String fileURL;
	
	private String fileContentType;
	
	private String userIdStr;
	
	private String requestDocumentId;
	
	private String requestedDocStatus;
	
	private String docUploadStatus;
	
	private String requestedDocRejectedReason;
	
	private long loginUserId;
	
	private long orgLocationId;
	
	private String documentId;
	
	private String candidateType;
	
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDocumentUUID()
	{
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID)
	{
		this.documentUUID = documentUUID;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getUserIdStr()
	{
		return userIdStr;
	}

	public void setUserIdStr(String userIdStr)
	{
		this.userIdStr = userIdStr;
	}

	

	
	public String getRequestDocumentId() {
		return requestDocumentId;
	}

	public void setRequestDocumentId(String requestDocumentId) {
		this.requestDocumentId = requestDocumentId;
	}

	public String getRequestedDocStatus() {
		return requestedDocStatus;
	}

	public void setRequestedDocStatus(String requestedDocStatus) {
		this.requestedDocStatus = requestedDocStatus;
	}

	public String getDocUploadStatus()
	{
		return docUploadStatus;
	}

	public void setDocUploadStatus(String docUploadStatus)
	{
		this.docUploadStatus = docUploadStatus;
	}

	public String getRequestedDocRejectedReason()
	{
		return requestedDocRejectedReason;
	}

	public void setRequestedDocRejectedReason(String requestedDocRejectedReason)
	{
		this.requestedDocRejectedReason = requestedDocRejectedReason;
	}

	public long getLoginUserId()
	{
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId)
	{
		this.loginUserId = loginUserId;
	}

	public long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}
	
	
	
}
