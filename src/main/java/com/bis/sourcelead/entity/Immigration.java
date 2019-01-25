package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "IMMIGRATION")
public class Immigration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PETITION_TYPE")
	private LookUpData petitionType;
	
	@Column(name = "RECEIPT_NO ")
	private String receiptNo;
	
	@Column(name = "PROCESS_TYPE")
	private String processType;

	@ManyToOne
	@JoinColumn(name = "CURRENT_USCIS_STATUS")
	private LookUpData currUscisStauts;

	@Column(name = "OPEN_DATE")
	private Date openDate;
	
	@Column(name = "DATE_OF_ARRIVAL ")
	private Date dateOfArrival;
	
	@Column(name = "PLACE_OF_ENTRY ")
	private String placeOfEntry;
	
	@Column(name = "GENERATED_ID")
	private String generatedId;
	
	@ManyToOne
	@JoinColumn(name = "IMMIGRATION_TEMPLATE_ID")
	private ImmigrationTemplate immigrationTemplateId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@ManyToOne
	@JoinColumn(name = "ATTORNEY_LOCATION_ID")
	private OrgLocations attorneyOrgLocationId;
	
	@ManyToOne
	@JoinColumn(name = "SPOC_USER_ID")
	private User spocUserId;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_USER_ID")
	private User candidateUserId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "JOB_ID")
	private Requirement job;


	@ManyToOne
	@JoinColumn(name = "ORG_REL_ID") // used for attorney name
    private OrgRelations orgRelations;
	
	
	@ManyToOne
	@JoinColumn(name= "ORG_SPOC_ID") // used for attorney contact
	private OrgSpoc orgSpoc;
	
	
	@Column(name = "COUNTRY ")
	private String country;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IMMIGRATION_STATUS ")
	private String immigrationStatus;
	
	@Column(name = "IMMIGRATION_UUID ")
	private String immigrationUuid;
	

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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigrationId",orphanRemoval=true)
	private List<ImmigrationCandidateDocuments> candidateDocuments;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigrationId",orphanRemoval=true)
	private List<ImmigrationCandidateDocumentsHistory> candidateDocumentsHistories;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigration",orphanRemoval=true)
	private List<ImmigrationCandidateForms> candidateForms;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigration",orphanRemoval=true)
	private List<SourceleadNotes> notes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigrationId",orphanRemoval=true)
	private List<Tasks> tasks;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigrationId",orphanRemoval=true)
	private List<ImmigrationActivityHistory> immigrationActivityHistory;   
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigration",orphanRemoval=true)
	private List<ImmigrationTasks> immigrationTasks;
	
	public List<ImmigrationCandidateDocuments> getCandidateDocuments() {
		return candidateDocuments;
	}

	public void setCandidateDocuments(List<ImmigrationCandidateDocuments> candidateDocuments) {
		this.candidateDocuments = candidateDocuments;
	}
	
	public List<ImmigrationCandidateDocumentsHistory> getCandidateDocumentsHistories() {
		return candidateDocumentsHistories;
	}

	public void setCandidateDocumentsHistories(List<ImmigrationCandidateDocumentsHistory> candidateDocumentsHistories) {
		this.candidateDocumentsHistories = candidateDocumentsHistories;
	}

	public List<ImmigrationCandidateForms> getCandidateForms() {
		return candidateForms;
	}

	public void setCandidateForms(List<ImmigrationCandidateForms> candidateForms) {
		this.candidateForms = candidateForms;
	}

	public List<SourceleadNotes> getNotes() {
		return notes;
	}

	public void setNotes(List<SourceleadNotes> notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPlaceOfEntry() {
		return placeOfEntry;
	}

	public void setPlaceOfEntry(String placeOfEntry) {
		this.placeOfEntry = placeOfEntry;
	}

	public Date getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(Date dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public ImmigrationTemplate getImmigrationTemplateId() {
		return immigrationTemplateId;
	}

	public void setImmigrationTemplateId(ImmigrationTemplate immigrationTemplateId) {
		this.immigrationTemplateId = immigrationTemplateId;
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

	public OrgRelations getOrgRelations() {
		return orgRelations;
	}

	public void setOrgRelations(OrgRelations orgRelations) {
		this.orgRelations = orgRelations;
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

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getImmigrationUuid() {
		return immigrationUuid;
	}

	public void setImmigrationUuid(String immigrationUuid) {
		this.immigrationUuid = immigrationUuid;
	}

	public LookUpData getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(LookUpData petitionType) {
		this.petitionType = petitionType;
	}

	public LookUpData getCurrUscisStauts() {
		return currUscisStauts;
	}

	public void setCurrUscisStauts(LookUpData currUscisStauts) {
		this.currUscisStauts = currUscisStauts;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public OrgLocations getAttorneyOrgLocationId() {
		return attorneyOrgLocationId;
	}

	public void setAttorneyOrgLocationId(OrgLocations attorneyOrgLocationId) {
		this.attorneyOrgLocationId = attorneyOrgLocationId;
	}

	public User getSpocUserId() {
		return spocUserId;
	}

	public void setSpocUserId(User spocUserId) {
		this.spocUserId = spocUserId;
	}

	public User getCandidateUserId() {
		return candidateUserId;
	}

	public void setCandidateUserId(User candidateUserId) {
		this.candidateUserId = candidateUserId;
	}

	public List<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}

	public List<ImmigrationActivityHistory> getImmigrationActivityHistory() {
		return immigrationActivityHistory;
	}

	public void setImmigrationActivityHistory(List<ImmigrationActivityHistory> immigrationActivityHistory) {
		this.immigrationActivityHistory = immigrationActivityHistory;
	}

	public List<ImmigrationTasks> getImmigrationTasks() {
		return immigrationTasks;
	}

	public void setImmigrationTasks(List<ImmigrationTasks> immigrationTasks) {
		this.immigrationTasks = immigrationTasks;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

}
