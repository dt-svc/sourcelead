package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class SourceleadTaxITDeclarationSubmissionSectionDTO implements Serializable {
	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String description;
	private boolean active;
	private List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<SourceleadTaxITDeclarationSubmissionItemDTO> getSubmissionItemList() {
		return submissionItemList;
	}

	public void setSubmissionItemList(List<SourceleadTaxITDeclarationSubmissionItemDTO> submissionItemList) {
		this.submissionItemList = submissionItemList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
