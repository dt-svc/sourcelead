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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_DOCUMENT_FOLDER")
public class ProjectDocumentFolder implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projectId;
	
    @Column(name = "FOLDER_NAME")
   	private String foderName;
    
    @Column(name = "STATUS")
   	private String status;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private User updatedBy;
	
	
	@OneToMany(mappedBy="projectDocumentFolderId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<ProjectDocumentPermission> projectDocumentPermissionList;

	@Transient
    @JsonProperty
	private int count;
    
	@Transient
    @JsonProperty
	private String permissionAccess;
	
	
	public Long getId() {
		return id;
	}

	public Projects getProjectId() {
		return projectId;
	}

	public String getFoderName() {
		return foderName;
	}

	public String getStatus() {
		return status;
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

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public void setFoderName(String foderName) {
		this.foderName = foderName;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPermissionAccess() {
		return permissionAccess;
	}

	public void setPermissionAccess(String permissionAccess) {
		this.permissionAccess = permissionAccess;
	}

	public Set<ProjectDocumentPermission> getProjectDocumentPermissionList() {
		return projectDocumentPermissionList;
	}

	public void setProjectDocumentPermissionList(Set<ProjectDocumentPermission> projectDocumentPermissionList) {
		this.projectDocumentPermissionList = projectDocumentPermissionList;
	}

}
