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
@Table(name = "PERFORMANCE_APPRAISAL_USER_FEEDBACK")
public class PerformanceAppraisalUserFeedback implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="APPRAISAL_ASSIGNED_ID" )        
    private PerformanceAppraisalAssignedAssociates appraisalAssignedId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID" )        
    private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID" )        
    private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_ID" )        
    private Org orgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FEEDBACK_TEMPLATE_ID" )        
    private PerformanceFeedbackTemplate feedbackTemplateId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "FEEDBACK_JSON_STRING", columnDefinition = "LONGTEXT default NULL")
	private String feedbackJsonString;
	
	@Column(name = "FEEDBACK_JSON_STRING_VALUES", columnDefinition = "LONGTEXT default NULL")
	private String feedbackJsonStringValues;
	
	@Column(name = "SCORE ")
	private Integer score;
	
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

	public PerformanceAppraisalAssignedAssociates getAppraisalAssignedId() {
		return appraisalAssignedId;
	}

	public void setAppraisalAssignedId(PerformanceAppraisalAssignedAssociates appraisalAssignedId) {
		this.appraisalAssignedId = appraisalAssignedId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getFeedbackJsonString() {
		return feedbackJsonString;
	}

	public void setFeedbackJsonString(String feedbackJsonString) {
		this.feedbackJsonString = feedbackJsonString;
	}

	public String getFeedbackJsonStringValues() {
		return feedbackJsonStringValues;
	}

	public void setFeedbackJsonStringValues(String feedbackJsonStringValues) {
		this.feedbackJsonStringValues = feedbackJsonStringValues;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public PerformanceFeedbackTemplate getFeedbackTemplateId() {
		return feedbackTemplateId;
	}

	public void setFeedbackTemplateId(PerformanceFeedbackTemplate feedbackTemplateId) {
		this.feedbackTemplateId = feedbackTemplateId;
	}
	
	
}
