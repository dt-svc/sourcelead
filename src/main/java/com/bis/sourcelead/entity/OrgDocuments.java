package com.bis.sourcelead.entity;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */

@Entity
@Table(name = "ORG_DOCUMENTS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "orgDocuments")
public class OrgDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )        
    private OrgLocations responderLocationId;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations locationId;	
	
	@Column(name = "DOC_NAME")
	private String docName;

	@Column(name = "CONTRACT_TYPE")
	private String contractType;

	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPOC_ID")
	private User spocUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OWNER_ID")
	private User ownerUser;
	
	@Column(name = "SOURCE_TYPE")
	private String sourceType;
	
	@Column(name = "IS_DOC_PRIVATE")
	private String isDocPrivate;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getResponderLocationId() {
		return responderLocationId;
	}

	public void setResponderLocationId(OrgLocations responderLocationId) {
		this.responderLocationId = responderLocationId;
	}

	public OrgLocations getLocationId() {
		return locationId;
	}

	public void setLocationId(OrgLocations locationId) {
		this.locationId = locationId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public User getSpocUser() {
		return spocUser;
	}

	public void setSpocUser(User spocUser) {
		this.spocUser = spocUser;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getIsDocPrivate() {
		return isDocPrivate;
	}

	public void setIsDocPrivate(String isDocPrivate) {
		this.isDocPrivate = isDocPrivate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	
	
}
