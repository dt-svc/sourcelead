package com.bis.sourcelead.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PROJECTS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Projects {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
 	
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
	@JoinColumn(name="PROJECT_MANAGER_USER_ID", nullable=false )	
	private User projectManagerUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CLIENT_ORG_ID", nullable=false )	
	private Org org;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERIOR_USER_ID", nullable = false)
   	private User superiorUser;
    
    @Column(name = "PROJECT_STATUS")
    private String projectStatus;
    
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

	public Long getId() {
		return id;
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
	public User getProjectManagerUser() {
		return projectManagerUser;
	}

	public void setProjectManagerUser(User projectManagerUser) {
		this.projectManagerUser = projectManagerUser;
	}
	
	@JsonIgnore
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	@JsonIgnore
	public User getSuperiorUser() {
		return superiorUser;
	}

	public void setSuperiorUser(User superiorUser) {
		this.superiorUser = superiorUser;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
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

	public Set<ProjectMembers> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Set<ProjectMembers> projectMembers) {
		this.projectMembers = projectMembers;
	}
	
}
