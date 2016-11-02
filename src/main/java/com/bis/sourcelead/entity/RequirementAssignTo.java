package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

	@Entity
	@Table(name = "REQUIREMENT_ASSIGN_TO")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="requirementAssignTo")
	public class RequirementAssignTo {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="TEAM_ID", nullable=false )        
    private Team teamId;
	
	
	@ManyToOne
    @JoinColumn(name="ROLE_ID", nullable=false )        
    private Roles roleId;
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )        
    private Requirement requirementId;
	
	@ManyToOne
    @JoinColumn(name="ASSIGN_USER_ID", nullable=false )        
    private User assignUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String assignedUserName;
	
	@Transient
	private Long teamOwnerId;
	
	
	@Transient
	private String assignedUserRole;
	
	@Transient
	private String teamName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Team getTeamId() {
		return teamId;
	}

	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public User getAssignUserId() {
		return assignUserId;
	}

	
	public String getAssignedUserRole() {
		return assignedUserRole;
	}

	public void setAssignedUserRole(String assignedUserRole) {
		this.assignedUserRole = assignedUserRole;
	}

	public void setAssignUserId(User assignUserId) {
		this.assignUserId = assignUserId;
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

	public String getAssignedUserName() {
		return assignedUserName;
	}

	public void setAssignedUserName(String assignedUserName) {
		this.assignedUserName = assignedUserName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getTeamOwnerId() {
		return teamOwnerId;
	}

	public void setTeamOwnerId(Long teamOwnerId) {
		this.teamOwnerId = teamOwnerId;
	}
	
	
}
