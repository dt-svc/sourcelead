package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.Date;

public class CampaignIndex implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long mailBoxId;
	
	private Long userId;
	
	private Long orgLocationId;
	
	private String signature;
	
	private String toMail;
	
	private String fromMail;
	
	private String mailStatus;
	
	private String subject;
	
	private String body;
	
	private String campaignTableData;
	
	private String candidateId;
	
	private String uuid;
	
	private Integer isFavourite;
	
	private String nameFields;
	
	private String phno;
	
	private String comments;
	
	private Integer isMailRead;
	
	private Integer status;

	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;
	
	private String fileContentType;
	
	private String fileName;
	
	private String calDays;
	
	private String mailType; 
	
	

	public Long getMailBoxId() {
		return mailBoxId;
	}

	public void setMailBoxId(Long mailBoxId) {
		this.mailBoxId = mailBoxId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCampaignTableData() {
		return campaignTableData;
	}

	public void setCampaignTableData(String campaignTableData) {
		this.campaignTableData = campaignTableData;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Integer isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getNameFields() {
		return nameFields;
	}

	public void setNameFields(String nameFields) {
		this.nameFields = nameFields;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getIsMailRead() {
		return isMailRead;
	}

	public void setIsMailRead(Integer isMailRead) {
		this.isMailRead = isMailRead;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getCalDays() {
		return calDays;
	}

	public void setCalDays(String calDays) {
		this.calDays = calDays;
	}

	public String getMailType()
	{
		return mailType;
	}

	public void setMailType(String mailType)
	{
		this.mailType = mailType;
	}
	
}
