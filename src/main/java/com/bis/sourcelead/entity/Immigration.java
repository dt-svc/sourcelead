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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATION", schema = "SOURCELEAD")
public class Immigration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "PETITION_TYPE")
	private Long petitionType;
	
	@Column(name = "RECEIPT_NO ")
	private String receiptNo;

	@Column(name = "CURRENT_USCIS_STATUS")
	private Long currUscisStauts;


	@Column(name = "DATE_OF_ARRIVAL ")
	private Date dateOf;
	
	@Column(name = "PLACE_OF_ENTRY ")
	private String placeOfEntry;
	
	
	@Column(name = "CHOOSE_TEMPLATE_ID")
	private Long chooseTemplateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_ID")
	private Requirement job;;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID")
    private Org org;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "ORG_SPOC_ID")
	private OrgSpoc orgSpoc;;
	
	
	@Column(name = "COUNTRY ")
	private String country;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IMMIGRATION_STATUS ")
	private String immigrationStatus;
	

	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(Long petitionType) {
		this.petitionType = petitionType;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getCurrUscisStauts() {
		return currUscisStauts;
	}

	public void setCurrUscisStauts(Long currUscisStauts) {
		this.currUscisStauts = currUscisStauts;
	}

	public Date getDateOf() {
		return dateOf;
	}

	public void setDateOf(Date dateOf) {
		this.dateOf = dateOf;
	}

	public String getPlaceOfEntry() {
		return placeOfEntry;
	}

	public void setPlaceOfEntry(String placeOfEntry) {
		this.placeOfEntry = placeOfEntry;
	}

	public Long getChooseTemplateId() {
		return chooseTemplateId;
	}

	public void setChooseTemplateId(Long chooseTemplateId) {
		this.chooseTemplateId = chooseTemplateId;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Requirement getJob() {
		return job;
	}

	public void setJob(Requirement job) {
		this.job = job;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public OrgSpoc getOrgSpoc() {
		return orgSpoc;
	}

	public void setOrgSpoc(OrgSpoc orgSpoc) {
		this.orgSpoc = orgSpoc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImmigrationStatus() {
		return immigrationStatus;
	}

	public void setImmigrationStatus(String immigrationStatus) {
		this.immigrationStatus = immigrationStatus;
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
