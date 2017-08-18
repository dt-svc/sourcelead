package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.constants.Constants;

@Entity
@Table(name = "PROJECT_DOCUMENTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projectDocuments")
public class ProjectDocuments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projectId;
	
	@Lob
    @Column(name="FILE_CONTENTS",length=100000)
    private byte [] fileContents;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS")
	private Integer status;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public byte[] getFileContents() {
		return fileContents;
	}

	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
	public ProjectDocuments(){
		
	}

	public ProjectDocuments(String fileName, String fileType, byte[] fileContents, Projects projects,Integer status) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContents = fileContents;
		this.status = Constants.CANDIDATE_ACTIVE;
		this.projectId = projects;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
	
}
