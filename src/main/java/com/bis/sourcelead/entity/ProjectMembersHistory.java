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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PROJECT_MEMBERS_HISTORY")
public class ProjectMembersHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_MEMBERS_ID", nullable=false )	
	private ProjectMembers projectMemberId;
	
	/*
	@ManyToOne
	@JoinColumn(name = "PROJECT_CLIENTS_ID")
	@JsonBackReference
    private ProjectClients projectClientsId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CANDIDATE_ID", nullable=false )	
	private Candidate candidateId;*/
	
	
	@Column(name = "PROJECT_ASSIGNED_DATE")
    private Date projectAssignedDate;
	
	@Column(name = "PROJECT_TERMINATION_DATE")
    private Date projectTerminationDate;
	
	@Column(name = "STATUS")
	private String status;
	
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

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Date getProjectAssignedDate() {
		return projectAssignedDate;
	}

	public void setProjectAssignedDate(Date projectAssignedDate) {
		this.projectAssignedDate = projectAssignedDate;
	}

	public Date getProjectTerminationDate() {
		return projectTerminationDate;
	}

	public void setProjectTerminationDate(Date projectTerminationDate) {
		this.projectTerminationDate = projectTerminationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

/*	public ProjectClients getProjectClientsId() {
		return projectClientsId;
	}

	public void setProjectClientsId(ProjectClients projectClientsId) {
		this.projectClientsId = projectClientsId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}*/


}
