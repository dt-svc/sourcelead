package com.bis.sourcelead.dto;

import java.util.List;

public class ReadMailDTO {
	private String body;
	private String subject;
	private String sentDate;
	private String from;
	private String to;
	private String cc;
	private String content;
	private List<AttachmentDTO> filesList;
	private Long messageId;
	private String folderName;
	private int attachmentsCount;
	private int unreadCount;

	private long emailAccountId;
	private String sendType; // compose, reply, forward
	private boolean seen;

	public List<AttachmentDTO> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<AttachmentDTO> filesList) {
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

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getAttachmentsCount() {
		return attachmentsCount;
	}

	public void setAttachmentsCount(int attachmentsCount) {
		this.attachmentsCount = attachmentsCount;
	}

	public long getEmailAccountId() {
		return emailAccountId;
	}

	public void setEmailAccountId(long emailAccountId) {
		this.emailAccountId = emailAccountId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendingType) {
		this.sendType = sendingType;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

}
