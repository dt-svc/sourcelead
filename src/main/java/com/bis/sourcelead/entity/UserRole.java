package com.bis.sourcelead.entity;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.enums.Status;

/**
 * @author bis113
 *
 */
@Entity
@Table(name = "USER_ROLE", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="userRole")
public class UserRole{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REPORTING_MANAGER_ID")
	private User reportingManager;
	
    @ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Roles roles;
    
    @ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "ORG_LOCATIONS_ID", nullable = false)
	private OrgLocations orgLocations;
    
    @Column(name = "IS_PRIMARY")
    private Integer isPrimary;
    
    @Column(name = "USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status userState;
    
    @Column(name = "PREVIOUS_USER_STATE")
	@Enumerated(EnumType.STRING)
	private Status previousUserState;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@Column(name = "TERMINATE_REASON", columnDefinition = "TEXT default NULL")
	private String terminateReason;
    
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
	
	@Transient
	private String userRoleName;
	
	
	@Transient
	private Integer projectsCount;
	
	
	@Transient
	private Integer openTimeSheetsCount;
	
	
	@Transient
	private Integer approvedTimeSheetsCount;
	
	
	@Transient
	private Integer rejectedTimeSheetsCount;
	
	
	@Transient
	private Integer waitingTimeSheetsCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(User reportingManager) {
		this.reportingManager = reportingManager;
	}

	public Status getUserState() {
		return userState;
	}

	public void setUserState(Status userState) {
		this.userState = userState;
	}

	public Status getPreviousUserState() {
		return previousUserState;
	}

	public void setPreviousUserState(Status previousUserState) {
		this.previousUserState = previousUserState;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
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

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public Integer getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(Integer projectsCount) {
		this.projectsCount = projectsCount;
	}

	public Integer getOpenTimeSheetsCount() {
		return openTimeSheetsCount;
	}

	public void setOpenTimeSheetsCount(Integer openTimeSheetsCount) {
		this.openTimeSheetsCount = openTimeSheetsCount;
	}

	public Integer getApprovedTimeSheetsCount() {
		return approvedTimeSheetsCount;
	}

	public void setApprovedTimeSheetsCount(Integer approvedTimeSheetsCount) {
		this.approvedTimeSheetsCount = approvedTimeSheetsCount;
	}

	public Integer getRejectedTimeSheetsCount() {
		return rejectedTimeSheetsCount;
	}

	public void setRejectedTimeSheetsCount(Integer rejectedTimeSheetsCount) {
		this.rejectedTimeSheetsCount = rejectedTimeSheetsCount;
	}

	public Integer getWaitingTimeSheetsCount() {
		return waitingTimeSheetsCount;
	}

	public void setWaitingTimeSheetsCount(Integer waitingTimeSheetsCount) {
		this.waitingTimeSheetsCount = waitingTimeSheetsCount;
	}

	
	

}