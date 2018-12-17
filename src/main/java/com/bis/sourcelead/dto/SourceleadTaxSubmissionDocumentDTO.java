package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class SourceleadTaxSubmissionDocumentDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private long taxITSubmissionId;
	private boolean updateFile;
	private List<SourceleadTaxSubmissionDocumentAttachmentDTO> attachmentList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTaxITSubmissionId() {
		return taxITSubmissionId;
	}

	public void setTaxITSubmissionId(long taxITSubmissionId) {
		this.taxITSubmissionId = taxITSubmissionId;
	}

	public boolean isUpdateFile() {
		return updateFile;
	}

	public void setUpdateFile(boolean updateFile) {
		this.updateFile = updateFile;
	}

	public List<SourceleadTaxSubmissionDocumentAttachmentDTO> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<SourceleadTaxSubmissionDocumentAttachmentDTO> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
