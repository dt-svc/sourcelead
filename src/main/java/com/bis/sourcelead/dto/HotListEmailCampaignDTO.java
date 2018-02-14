package com.bis.sourcelead.dto;

import java.util.List;

public class HotListEmailCampaignDTO {

	private String staticPath;
	 
	private List<FileInfo> fileInfo;
	
	private String Toemails;
	
	private String otherEmails;
	
	private String hotlistcandidates;
	
	private String namefield;
	
	private String saluation;
	
	private String bodytext;
	
	private String subject;

	private String email;
	
	private String phone;
	
	private String comment;
	
	private String senderIdentity;
	
	public String getStaticPath() {
		return staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public String getToemails() {
		return Toemails;
	}

	public String getOtherEmails() {
		return otherEmails;
	}

	public String getHotlistcandidates() {
		return hotlistcandidates;
	}

	public String getNamefield() {
		return namefield;
	}

	public String getSaluation() {
		return saluation;
	}

	public String getBodytext() {
		return bodytext;
	}

	public String getSubject() {
		return subject;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setToemails(String toemails) {
		Toemails = toemails;
	}

	public void setOtherEmails(String otherEmails) {
		this.otherEmails = otherEmails;
	}

	public void setHotlistcandidates(String hotlistcandidates) {
		this.hotlistcandidates = hotlistcandidates;
	}

	public void setNamefield(String namefield) {
		this.namefield = namefield;
	}

	public void setSaluation(String saluation) {
		this.saluation = saluation;
	}

	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getComment() {
		return comment;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSenderIdentity()
	{
		return senderIdentity;
	}

	public void setSenderIdentity(String senderIdentity)
	{
		this.senderIdentity = senderIdentity;
	}
	
}
