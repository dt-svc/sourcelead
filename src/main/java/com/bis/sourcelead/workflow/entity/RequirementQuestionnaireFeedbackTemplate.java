package com.bis.sourcelead.workflow.entity;

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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "REQUIREMENT_QUESTIONNAIRE_FEEDBACK_TEMPLATE")
public class RequirementQuestionnaireFeedbackTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_WORKFLOW_ID",  nullable = false)
	private RequirementWorkFlow workflowId;
	
	@Column(name = "JSON_STRING", columnDefinition = "LONGTEXT default NULL")
	private String jsonString ;
	
	@Column(name = "JSON_STRING_VALUES", columnDefinition = "LONGTEXT default NULL")
	private String jsonStringValues ;
	
	@Column(name = "TIME_LIMIT_MINUTES")
	private Integer timeLimitMinutes;
	
	@Column(name = "TIME_LIMIT_SECONDS")
	private Integer timeLimitSeconds;
	
	@Column(name = "NUMBER_OF_SUBMISSIONS")
	private Integer submissionsCount;
	
	@Column(name = "TOTAL_POINTS")
	private Integer totalPoints;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable = false)        
    private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false)        
    private User updatedBy;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public RequirementWorkFlow getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(RequirementWorkFlow workflowId) {
		this.workflowId = workflowId;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public String getJsonStringValues() {
		return jsonStringValues;
	}

	public void setJsonStringValues(String jsonStringValues) {
		this.jsonStringValues = jsonStringValues;
	}

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getTimeLimitSeconds() {
		return timeLimitSeconds;
	}

	public void setTimeLimitSeconds(Integer timeLimitSeconds) {
		this.timeLimitSeconds = timeLimitSeconds;
	}

	public Integer getSubmissionsCount() {
		return submissionsCount;
	}

	public void setSubmissionsCount(Integer submissionsCount) {
		this.submissionsCount = submissionsCount;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	
}
