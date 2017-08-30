package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PROJECT_CLIENTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projectClients")
public class ProjectClients implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
	@JsonBackReference
    private Projects projectId;
	
    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "ORG_RELATION_ID")
    private OrgRelations orgRelationId;
    
    @ManyToOne
    @JoinColumn(name = "ORG_LOCATIONS_ID")
    private OrgLocations orgLocationsId;
    
    @Column(name = "UUID")
    private String uuid;
    
    @Column(name = "STATUS")
    private Integer status;
    
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
	
	@OneToMany(mappedBy="projectClientsId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
	@JsonBackReference
    private Set<ProjectApproverHierarchy> projectApproverHierarchyList;
	
	
	@OneToMany(mappedBy="projectClientsId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
	@JsonBackReference
    private Set<ProjectMembers> projectMembersList;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<ProjectApproverHierarchy> getProjectApproverHierarchyList() {
		return projectApproverHierarchyList;
	}

	public void setProjectApproverHierarchyList(Set<ProjectApproverHierarchy> projectApproverHierarchyList) {
		this.projectApproverHierarchyList = projectApproverHierarchyList;
	}

	public Set<ProjectMembers> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(Set<ProjectMembers> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}
}
