package com.bis.sourcelead.entity;

import java.util.Date;

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

/**
 * @author Venkat Kesav
 *
 */

@Entity
@Table(name = "TEAM_MEMBERS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "teamMembers")

public class TeamMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="TEAM_ID", nullable=false )        
    private Team teamId;
	
	@ManyToOne
    @JoinColumn(name="MEMBER_USER_ID", nullable=false )        
    private User memberUserId;
	
	@Column(name = "STATUS ")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	
	@Transient
    private String ownerName;
	
	@Transient
    private String memberName;
	
	@Transient
    private String teamName;
	
	
	@Transient
    private int teamMemberRequirementAssignedCount;
	
	
	
	@Transient
    private int teamMemberRequirementAssignedOpenCount;
	
	
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

	public User getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(User memberUserId) {
		this.memberUserId = memberUserId;
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

	public int getTeamMemberRequirementAssignedCount() {
		return teamMemberRequirementAssignedCount;
	}

	public void setTeamMemberRequirementAssignedCount(int teamMemberRequirementAssignedCount) {
		this.teamMemberRequirementAssignedCount = teamMemberRequirementAssignedCount;
	}

	public int getTeamMemberRequirementAssignedOpenCount() {
		return teamMemberRequirementAssignedOpenCount;
	}

	public void setTeamMemberRequirementAssignedOpenCount(int teamMemberRequirementAssignedOpenCount) {
		this.teamMemberRequirementAssignedOpenCount = teamMemberRequirementAssignedOpenCount;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
	
}
