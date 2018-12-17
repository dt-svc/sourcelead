package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "INTERVIEW_FEEDBACK_TEMPLATE")
public class InterviewFeedbackTemplate implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_INTERVIEW_ID")
    private RequirementInterview requirementInterviewId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_INTERVIEWER_ID")
    private RequirementInterviewer requirementInterviewerId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBMISSION_TEMPLATE_ID")
    private SubmissionTemplate submissionTemplateId;
	
	@Column(name = "INTERVIEW_TEMPLATE_JSON_STRING")
	@Type(type="text")
	private String interviewTemplateJsonString;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "COMMENT")
	@Type(type="text")
	private String comment;
	
	@Column(name = "INTERVIEW_STATUS")
	private String interviewStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	@JsonProperty
	private String interviewerName;
	
	@Transient
	@JsonProperty
	private String interviewerRating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementInterview getRequirementInterviewId() {
		return requirementInterviewId;
	}

	public void setRequirementInterviewId(RequirementInterview requirementInterviewId) {
		this.requirementInterviewId = requirementInterviewId;
	}

	public SubmissionTemplate getSubmissionTemplateId() {
		return submissionTemplateId;
	}

	public void setSubmissionTemplateId(SubmissionTemplate submissionTemplateId) {
		this.submissionTemplateId = submissionTemplateId;
	}

	public String getInterviewTemplateJsonString() {
		return interviewTemplateJsonString;
	}

	public void setInterviewTemplateJsonString(String interviewTemplateJsonString) {
		this.interviewTemplateJsonString = interviewTemplateJsonString;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
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

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getInterviewerRating() {
		return interviewerRating;
	}

	public void setInterviewerRating(String interviewerRating) {
		this.interviewerRating = interviewerRating;
	}

	public RequirementInterviewer getRequirementInterviewerId() {
		return requirementInterviewerId;
	}

	public void setRequirementInterviewerId(RequirementInterviewer requirementInterviewerId) {
		this.requirementInterviewerId = requirementInterviewerId;
	}
}
