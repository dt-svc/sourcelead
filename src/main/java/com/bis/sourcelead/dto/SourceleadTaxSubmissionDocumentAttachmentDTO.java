package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadTaxSubmissionDocumentAttachmentDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String uuid;
	private String attachment;
	private boolean active;
	private long taxSubmissionDocumentId;
	private String fileURL;
	private String contentType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getTaxSubmissionDocumentId() {
		return taxSubmissionDocumentId;
	}

	public void setTaxSubmissionDocumentId(long taxSubmissionDocumentId) {
		this.taxSubmissionDocumentId = taxSubmissionDocumentId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String attachmentPath) {
		this.fileURL = attachmentPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
