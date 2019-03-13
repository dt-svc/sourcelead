package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_SUBMISSION_DOCUMENT_ATTACHMENT")
public class SourceleadTaxSubmissionDocumentAttachment implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "ATTACHMENT")
	private String attachment;

	@Column(name = "FILE_URL")
	private String fileURL;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	@Column(name = "ACTIVE")
	private boolean active;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_SUBMISSION_DOCUMENT_ID", nullable = false)
	private SourceleadTaxSubmissionDocument sourceleadTaxSubmissionDocument;

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

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public SourceleadTaxSubmissionDocument getSourceleadTaxSubmissionDocument() {
		return sourceleadTaxSubmissionDocument;
	}

	public void setSourceleadTaxSubmissionDocument(SourceleadTaxSubmissionDocument sourceleadTaxSubmissionDocument) {
		this.sourceleadTaxSubmissionDocument = sourceleadTaxSubmissionDocument;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
