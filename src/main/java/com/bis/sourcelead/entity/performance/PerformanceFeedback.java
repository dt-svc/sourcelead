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
@Table(name = "PERFORMANCE_FEEDBACK")
public class PerformanceFeedback implements Serializable{

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
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "FEEDBACK_CATEGORIES_SCORE ")
	private Integer feedbackCategoriesScore;
	
	@Column(name = "GOALS_SCORE ")
	private Integer goalsScore;
	
	@Column(name = "TASKS_SCORE ")
	private Integer tasksScore;
	
	@Column(name = "COMPETENCIES_SCORE ")
	private Integer competenciesScore;
	
	@Column(name = "FINAL_SCORE ")
	private Integer finalScore;
	
	@Column(name = "SUBMISSION_STAGE")
	private String submissionStage;
	
	@Column(name = "COMMENTS", columnDefinition = "LONGTEXT default NULL")
	private String comments;
	
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

	public Integer getFeedbackCategoriesScore() {
		return feedbackCategoriesScore;
	}

	public void setFeedbackCategoriesScore(Integer feedbackCategoriesScore) {
		this.feedbackCategoriesScore = feedbackCategoriesScore;
	}

	public Integer getGoalsScore() {
		return goalsScore;
	}

	public void setGoalsScore(Integer goalsScore) {
		this.goalsScore = goalsScore;
	}

	public Integer getTasksScore() {
		return tasksScore;
	}

	public void setTasksScore(Integer tasksScore) {
		this.tasksScore = tasksScore;
	}

	public Integer getCompetenciesScore() {
		return competenciesScore;
	}

	public void setCompetenciesScore(Integer competenciesScore) {
		this.competenciesScore = competenciesScore;
	}

	public Integer getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
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

	public PerformanceAppraisalAssignedAssociates getAppraisalAssignedId() {
		return appraisalAssignedId;
	}

	public void setAppraisalAssignedId(PerformanceAppraisalAssignedAssociates appraisalAssignedId) {
		this.appraisalAssignedId = appraisalAssignedId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
