package com.bis.sourcelead.entity.performance;

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

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES")
public class PerformanceAppraisalAssignedAssociates implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="APPRAISAL_TEMPLATE_ID" )        
    private PerformanceAppraisalTemplate appraisalTemplateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID" )        
    private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PEER_ID" )        
    private PerformanceAppraisalPeers peerId;
	
	@Column(name = "PEER_USER_ID", columnDefinition = "TEXT default NULL")
	private String peerUserId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID" )        
    private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_ID" )        
    private Org orgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REPORTING_MANAGER_ID" )        
    private User reportingManagerId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="RESOURCE_MANAGER_ID" )        
    private User resourceManagerId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "APPRAISAL_STAGE")
	private String appraisalStage;
	
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY" )        
    private User createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY")        
    private User updatedBy;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerformanceAppraisalTemplate getAppraisalTemplateId() {
		return appraisalTemplateId;
	}

	public void setAppraisalTemplateId(PerformanceAppraisalTemplate appraisalTemplateId) {
		this.appraisalTemplateId = appraisalTemplateId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public PerformanceAppraisalPeers getPeerId() {
		return peerId;
	}
	
	public String getPeerUserId() {
		return peerUserId;
	}

	public void setPeerUserId(String peerUserId) {
		this.peerUserId = peerUserId;
	}

	public void setPeerId(PerformanceAppraisalPeers peerId) {
		this.peerId = peerId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}
	
	public User getReportingManagerId() {
		return reportingManagerId;
	}

	public void setReportingManagerId(User reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

	public User getResourceManagerId() {
		return resourceManagerId;
	}

	public void setResourceManagerId(User resourceManagerId) {
		this.resourceManagerId = resourceManagerId;
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

	public String getAppraisalStage() {
		return appraisalStage;
	}

	public void setAppraisalStage(String appraisalStage) {
		this.appraisalStage = appraisalStage;
	}
	
	
}
