package com.bis.sourcelead.dto;

import java.util.List;

public class ReadMailDTO {
	private String body;
	private String subject;
	private String sentDate;
	private String from;
	private String content;
	private List<String> filesList;
	private Long messageId ;
	
	
	public List<String> getFilesList() {
		return filesList;
	}
	public void setFilesList(List<String> filesList) {
		this.filesList = filesList;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String contentType) {
		this.content = contentType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	

}
