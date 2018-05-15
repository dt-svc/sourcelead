package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
* This class is used to store all the Attachments of every Expense that is added in Add Expense Screen.
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version     1.0       Pradeep Reddy      New_File      New_File
* 
* @since   2017-06-02
*/

@Entity
@Table(name = "EXPENSES_ENTRIES_DOCUMENTS")
public class ExpensesEntryDocuments implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="EXPENSES_ENTRIES_ID", nullable=false )        
private ExpensesEntries expensesEntriesId;

@Column(name = "FILE_CONTENT_TYPE")
private String fileContentType;

@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
private String fileName;

@Column(name = "STATUS")
private int status;

@Column(name = "CREATED_DATE")
private Date createdDate;

@Column(name = "CREATED_BY")
private String createdBy;

@Column(name = "UPDATED_DATE")
private Date updatedDate;

@Column(name = "UPDATED_BY")
private String updatedBy;

@Column(name="FILEURL")
private String fileURL;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public ExpensesEntries getExpensesEntriesId() {
	return expensesEntriesId;
}

public void setExpensesEntriesId(ExpensesEntries expensesEntriesId) {
	this.expensesEntriesId = expensesEntriesId;
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

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public Date getUpdatedDate() {
	return updatedDate;
}

public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
}

public String getUpdatedBy() {
	return updatedBy;
}

public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public String getFileURL() {
	return fileURL;
}

public void setFileURL(String fileURL) {
	this.fileURL = fileURL;
}



}
	