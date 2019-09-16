package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Ajith Matta
 *
 */

@Entity
@Table(name = "CANDIDATE_RESUMES")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateResumes")
public class CandidateResumes implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable=false)
	private Candidate candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_GLOBAL_ID", nullable = false)
	private CandidateGlobal candidateGlobalId;
	
	@Transient
	private Long candidateJsonId;
		
	
	@Column(name = "TITLE", columnDefinition = "TEXT default NULL")
	private String title ;
	
	@Column(name = "DESCRIPTION ")
	private String description ;
	
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
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String msg;
	
	@Transient
	private String candidateResumeUpdateDateToString;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@OneToMany(mappedBy = "candidateResumes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateResumesHistory> candidateResumesHistoryList = new HashSet<CandidateResumesHistory>(0);

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public Long getCandidateJsonId() {
		if(candidateId != null && candidateId.getId() != null){
			candidateJsonId = candidateId.getId();
		}
		return candidateJsonId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResumeStatus() {
		return resumeStatus;
	}

	public void setResumeStatus(String resumeStatus) {
		this.resumeStatus = resumeStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResumeUUID() {
		return resumeUUID;
	}

	public void setResumeUUID(String resumeUUID) {
		this.resumeUUID = resumeUUID;
	}

	public String getCandidateResumeUpdateDateToString() {
		return candidateResumeUpdateDateToString;
	}

	public void setCandidateResumeUpdateDateToString(String candidateResumeUpdateDateToString) {
		this.candidateResumeUpdateDateToString = candidateResumeUpdateDateToString;
	}

	public Set<CandidateResumesHistory> getCandidateResumesHistoryList() {
		return candidateResumesHistoryList;
	}

	public void setCandidateResumesHistoryList(Set<CandidateResumesHistory> candidateResumesHistoryList) {
		this.candidateResumesHistoryList = candidateResumesHistoryList;
	}

	public CandidateGlobal getCandidateGlobalId() {
		return candidateGlobalId;
	}

	public void setCandidateGlobalId(CandidateGlobal candidateGlobalId) {
		this.candidateGlobalId = candidateGlobalId;
	}

}
