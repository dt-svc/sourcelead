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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "REQUESTED_DOCUMENTS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requestedDocuments")
public class RequestedDocuments implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")        
    private User userId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CANDIDATE_ID")        
    private Candidate candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CANDIDATE_GLOBAL_ID")        
    private CandidateGlobal candidateGlobalId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID")        
    private OrgLocations orgLocationId;
	
	@Column(name = "REQUEST_DOC_UUID")
	private String requestDocUUID;
	
	@Column(name = "IS_DOCS_UPLOADED")
	private String isDocsUploaded;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
	}

	public Candidate getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId)
	{
		this.candidateId = candidateId;
	}

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getRequestDocUUID()
	{
		return requestDocUUID;
	}

	public void setRequestDocUUID(String requestDocUUID)
	{
		this.requestDocUUID = requestDocUUID;
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

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getIsDocsUploaded()
	{
		return isDocsUploaded;
	}

	public void setIsDocsUploaded(String isDocsUploaded)
	{
		this.isDocsUploaded = isDocsUploaded;
	}

	public CandidateGlobal getCandidateGlobalId() {
		return candidateGlobalId;
	}

	public void setCandidateGlobalId(CandidateGlobal candidateGlobalId) {
		this.candidateGlobalId = candidateGlobalId;
	}
	
	
}
