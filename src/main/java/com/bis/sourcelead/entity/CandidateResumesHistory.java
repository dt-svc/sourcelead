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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CANDIDATE_RESUMES_HISTORY")
public class CandidateResumesHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_RESUMES", nullable = false)
	private CandidateResumes candidateResumes;
	@Transient
	private Long candidateJsonId;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DESCRIPTION ")
	private String description;
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	@Column(name = "RESUME_STATUS ")
	private String resumeStatus;
	@Column(name = "RESUME_UUID ")
	private String resumeUUID;
	@Column(name = "STATUS ")
	private Integer status;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getFileContentType()
	{
		return fileContentType;
	}
	
	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getResumeStatus()
	{
		return resumeStatus;
	}
	
	public void setResumeStatus(String resumeStatus)
	{
		this.resumeStatus = resumeStatus;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public CandidateResumes getCandidateResumes()
	{
		return candidateResumes;
	}
	
	public void setCandidateResumes(CandidateResumes candidateResumes)
	{
		this.candidateResumes = candidateResumes;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
	
	public String getResumeUUID()
	{
		return resumeUUID;
	}
	
	public void setResumeUUID(String resumeUUID)
	{
		this.resumeUUID = resumeUUID;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}
}
