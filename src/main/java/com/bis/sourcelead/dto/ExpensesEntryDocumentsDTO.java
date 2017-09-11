package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ExpensesEntryDocumentsDTO implements Serializable {
private static final long serialVersionUID = 1L;

private Long id;

@JsonBackReference
private ExpensesEntriesDTO expensesEntriesId;

private  byte[] docFile;
	
private String fileContentType;

private String fileName;

private String fileURL;

private Integer status;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public ExpensesEntriesDTO getExpensesEntriesId() {
	return expensesEntriesId;
}

public void setExpensesEntriesId(ExpensesEntriesDTO expensesEntriesId) {
	this.expensesEntriesId = expensesEntriesId;
}

public byte[] getDocFile() {
	return docFile;
}

public void setDocFile(byte[] docFile) {
	this.docFile = docFile;
}

public String getFileContentType() {
	return fileContentType;
}

public void setFileContentType(String fileContentType) {
	this.fileContentType = fileContentType;
}

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
}

public String getFileURL() {
	return fileURL;
}

public void setFileURL(String fileURL) {
	this.fileURL = fileURL;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}








}
	