package com.bis.sourcelead.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PROJECTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="projects")
public class Projects {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
	@Column(name = "PROJECT_SEQUENCE_NUMBER")
	private String projectSequeneNumber;
	
    @Column(name = "PROJECT_NAME")
    private String projectName;
 
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
    private String description;
 
    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name = "END_DATE")
    private Date endDate;
    
    @Column(name = "TIMESHEET_FREQUENCY")
    private String timesheetFrequency;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERIOR_USER_ID", nullable = false)
   	private User superiorUser;
    
    
	@Column(name = "PROJECT_STATUS")
	@Enumerated(EnumType.STRING)
	private Project_Status projectStatus;
	
	@Column(name = "IS_MAIL_TO_TEAM_MEMBER")
	@Enumerated(EnumType.STRING)
	private Status isMailToTeamMembers;
	
	@Column(name = "IS_MAIL_TO_PROJECT_MANAGER")
	@Enumerated(EnumType.STRING)
	private Status isMailToProjectManager;
	
	@Column(name = "IS_PRIMARY")
    private Integer isPrimary;
	
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_RELATION_ID")
    private OrgRelations orgRelationId;
    
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_LOCATIONS_ID")
    private OrgLocations orgLocationsId;
    
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
    
    @OneToMany(mappedBy="projects",cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<ProjectMembers> projectMembers;
    
    @OneToMany(mappedBy="projectId",cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<ProjectApproverHierarchy> projectApprovers;
    
    @Transient
    private String managerLevel1;
    
    @Transient
    private String managerLevel2;
    
    @Transient
    private String createdDateStr;
    
    @Transient
    private String updatedDateStr;
    
    @Transient
    private String startDateStr;
    
    @Transient
    private String endDateStr;
    
    @Transient
    private String[] projectManagerName;
    
    @Transient
    private String[] clientName;
    
    @Transient
    private String client;
    
    @Transient
    private String projectMembersCount;
    
    @Transient
    private Long relationId;
    
    
    @Transient
    Collection<ProjectApproverHierarchy>  projectApproverHierarchiesList;
    
    @Transient
    Collection<OrgRelations> clientList;

	public Long getId() {
		return id;
	}
	
	public String getProjectSequeneNumber() {
		return projectSequeneNumber;
	}

	public void setProjectSequeneNumber(String projectSequeneNumber) {
		this.projectSequeneNumber = projectSequeneNumber;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTimesheetFrequency() {
		return timesheetFrequency;
	}

	public void setTimesheetFrequency(String timesheetFrequency) {
		this.timesheetFrequency = timesheetFrequency;
	}
	
	
	@JsonIgnore
	public User getSuperiorUser() {
		return superiorUser;
	}

	public void setSuperiorUser(User superiorUser) {
		this.superiorUser = superiorUser;
	}

	

	public Project_Status getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Project_Status projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Status getIsMailToTeamMembers() {
		return isMailToTeamMembers;
	}

	public void setIsMailToTeamMembers(Status isMailToTeamMembers) {
		this.isMailToTeamMembers = isMailToTeamMembers;
	}

	public Status getIsMailToProjectManager() {
		return isMailToProjectManager;
	}

	public void setIsMailToProjectManager(Status isMailToProjectManager) {
		this.isMailToProjectManager = isMailToProjectManager;
	}

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}

	@JsonIgnore
	public OrgRelations getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId) {
		this.orgRelationId = orgRelationId;
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
	
	@JsonIgnore
	public Set<ProjectMembers> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Set<ProjectMembers> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getManagerLevel1() {
		return managerLevel1;
	}

	public void setManagerLevel1(String managerLevel1) {
		this.managerLevel1 = managerLevel1;
	}

	public String getManagerLevel2() {
		return managerLevel2;
	}

	public void setManagerLevel2(String managerLevel2) {
		this.managerLevel2 = managerLevel2;
	}
	@JsonIgnore
	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}
	@JsonIgnore
	public Set<ProjectApproverHierarchy> getProjectApprovers() {
		return projectApprovers;
	}

	public void setProjectApprovers(Set<ProjectApproverHierarchy> projectApprovers) {
		this.projectApprovers = projectApprovers;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getUpdatedDateStr() {
		return updatedDateStr;
	}

	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String[] getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String[] projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String[] getClientName() {
		return clientName;
	}

	public void setClientName(String[] clientName) {
		this.clientName = clientName;
	}

	public Collection<ProjectApproverHierarchy> getProjectApproverHierarchiesList() {
		return projectApproverHierarchiesList;
	}

	public void setProjectApproverHierarchiesList(Collection<ProjectApproverHierarchy> projectApproverHierarchiesList) {
		this.projectApproverHierarchiesList = projectApproverHierarchiesList;
	}
	@JsonIgnore
	public Collection<OrgRelations> getClientList() {
		return clientList;
	}

	public void setClientList(Collection<OrgRelations> clientList) {
		this.clientList = clientList;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProjectMembersCount() {
		return projectMembersCount;
	}

	public void setProjectMembersCount(String projectMembersCount) {
		this.projectMembersCount = projectMembersCount;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	
	
	
}
