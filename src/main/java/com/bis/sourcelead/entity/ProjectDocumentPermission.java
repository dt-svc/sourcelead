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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PROJECT_DOCUMENT_PERMISSIONS")
public class ProjectDocumentPermission implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_DOCUMENT_ID", nullable = false)
	private ProjectDocuments projectDocumentId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_DOCUMENT_FOLDER_ID", nullable = false)
	private ProjectDocumentFolder projectDocumentFolderId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_MEMBER_ID", nullable = false)
	private ProjectMembers projectMemberId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ROLE_ID", nullable = false)
	private Roles userRoleId;
	
    @Column(name = "ACCESS")
   	private String access;
     
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User  createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private User  updatedBy;

	public Long getId() {
		return id;
	}

	public ProjectDocuments getProjectDocumentId() {
		return projectDocumentId;
	}

	public ProjectDocumentFolder getProjectDocumentFolderId() {
		return projectDocumentFolderId;
	}

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public Roles getUserRoleId() {
		return userRoleId;
	}

	public String getAccess() {
		return access;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProjectDocumentId(ProjectDocuments projectDocumentId) {
		this.projectDocumentId = projectDocumentId;
	}

	public void setProjectDocumentFolderId(ProjectDocumentFolder projectDocumentFolderId) {
		this.projectDocumentFolderId = projectDocumentFolderId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public void setUserRoleId(Roles userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
}
