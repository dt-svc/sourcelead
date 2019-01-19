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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "IMMIGRATION_CANDIDATE_DOCS_HISTORY")
public class ImmigrationCandidateDocumentsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "IMMIGRATION_ID")
	private Immigration immigrationId;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidateId;
	
	@ManyToOne
	@JoinColumn(name = "IMMIGRATION_CANDIDATE_DOCS_ID")
	private ImmigrationCandidateDocuments immigrationCandidateDocumentId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "UPLOADED_FILE_URL",columnDefinition = "TEXT default NULL")
	private String uplodedFileUrl;
	
	@Column(name = "UPLOADED_FILE_NAME")
	private String upldedFileName;
	
	@Column(name = "UPLOADED_FILE_CONTENT_TYPE")
	private String upldedFileContentType;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Immigration getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(Immigration immigrationId) {
		this.immigrationId = immigrationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public ImmigrationCandidateDocuments getImmigrationCandidateDocumentId() {
		return immigrationCandidateDocumentId;
	}

	public void setImmigrationCandidateDocumentId(ImmigrationCandidateDocuments immigrationCandidateDocumentId) {
		this.immigrationCandidateDocumentId = immigrationCandidateDocumentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUplodedFileUrl() {
		return uplodedFileUrl;
	}

	public void setUplodedFileUrl(String uplodedFileUrl) {
		this.uplodedFileUrl = uplodedFileUrl;
	}

	public String getUpldedFileName() {
		return upldedFileName;
	}

	public void setUpldedFileName(String upldedFileName) {
		this.upldedFileName = upldedFileName;
	}

	public String getUpldedFileContentType() {
		return upldedFileContentType;
	}

	public void setUpldedFileContentType(String upldedFileContentType) {
		this.upldedFileContentType = upldedFileContentType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
		
}
