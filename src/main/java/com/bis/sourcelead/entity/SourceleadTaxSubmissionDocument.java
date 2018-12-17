package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOURCELEAD_TAX_SUBMISSION_DOCUMENT")
public class SourceleadTaxSubmissionDocument implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "NAME")
	private String name;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_IT_DECLARATION_SUBMISSION_ID", nullable = false)
	private SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission;

	@OneToMany(mappedBy = "sourceleadTaxSubmissionDocument", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SourceleadTaxSubmissionDocumentAttachment> attachmentList;

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

	public SourceleadTaxITDeclarationSubmission getSourceleadTaxITDeclarationSubmission() {
		return sourceleadTaxITDeclarationSubmission;
	}

	public void setSourceleadTaxITDeclarationSubmission(SourceleadTaxITDeclarationSubmission sourceleadTaxITDeclarationSubmission) {
		this.sourceleadTaxITDeclarationSubmission = sourceleadTaxITDeclarationSubmission;
	}

	public List<SourceleadTaxSubmissionDocumentAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<SourceleadTaxSubmissionDocumentAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
